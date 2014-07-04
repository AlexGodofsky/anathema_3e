package net.sf.anathema.hero.framework.perspective;

import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.repository.IRepositoryFileResolver;
import net.sf.anathema.hero.application.item.HeroReferenceScanner;
import net.sf.anathema.hero.application.item.Item;
import net.sf.anathema.hero.creation.CharacterTemplateCreator;
import net.sf.anathema.hero.experience.ExperienceModelFetcher;
import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.hero.framework.HeroEnvironmentExtractor;
import net.sf.anathema.hero.framework.display.ItemReceiver;
import net.sf.anathema.hero.framework.persistence.HeroItemPersister;
import net.sf.anathema.hero.framework.persistence.RepositoryItemPersister;
import net.sf.anathema.hero.framework.perspective.model.CharacterIdentifier;
import net.sf.anathema.hero.framework.perspective.model.CharacterItemModel;
import net.sf.anathema.hero.framework.perspective.model.CharacterPersistenceModel;
import net.sf.anathema.hero.framework.perspective.model.CharacterReference;
import net.sf.anathema.hero.framework.perspective.model.ItemSystemModel;
import net.sf.anathema.hero.framework.perspective.model.NewCharacterListener;
import net.sf.anathema.hero.framework.perspective.model.ReportRegister;
import net.sf.anathema.hero.framework.perspective.sheet.ControlledPrintWithSelectedReport;
import net.sf.anathema.hero.framework.perspective.sheet.QuickPrintCommand;
import net.sf.anathema.hero.framework.reporting.Report;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.platform.JsonHeroReferenceScanner;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.gui.file.SingleFileChooser;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.platform.environment.Environment;
import org.jmock.example.announcer.Announcer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterSystemModel implements ItemSystemModel {

  private final Map<CharacterIdentifier, CharacterItemModel> modelsByIdentifier = new HashMap<>();
  private Announcer<ChangeListener> getsSelectionListener = Announcer.to(ChangeListener.class);
  private Announcer<ChangeListener> becomesExperiencedListener = Announcer.to(ChangeListener.class);
  private Announcer<ChangeListener> becomesInexperiencedListener = Announcer.to(ChangeListener.class);
  private Announcer<NewCharacterListener> characterAddedListener = Announcer.to(NewCharacterListener.class);
  private CharacterIdentifier currentCharacter;
  private Announcer<ChangeListener> becomesDirtyAnnouncer = Announcer.to(ChangeListener.class);
  private Announcer<ChangeListener> becomesCleanAnnouncer = Announcer.to(ChangeListener.class);
  private ChangeListener dirtyListener = this::notifyDirtyListeners;
  private final CharacterPersistenceModel persistenceModel;
  private IApplicationModel model;
  private int newCharacterCount = 0;

  public CharacterSystemModel(IApplicationModel model) {
    this(new CharacterPersistenceModel(model, HeroEnvironmentExtractor.getGenerics(model)), model);
  }

  public CharacterSystemModel(CharacterPersistenceModel persistenceModel, IApplicationModel model) {
    this.persistenceModel = persistenceModel;
    this.model = model;
  }

  @Override
  public Collection<CharacterItemModel> collectAllExistingCharacters() {
    Collection<CharacterReference> references = persistenceModel.collectCharacters();
    List<CharacterItemModel> characters = new ArrayList<>();
    for (CharacterReference reference : references) {
      PreloadedDescriptiveFeatures features = new PreloadedDescriptiveFeatures(createFileScanner(), reference);
      CharacterItemModel character = new CharacterItemModel(features);
      modelsByIdentifier.put(features.getIdentifier(), character);
      characters.add(character);
    }
    return characters;
  }

  private HeroReferenceScanner createFileScanner() {
    HeroEnvironment generics = getHeroEnvironment();
    IRepositoryFileResolver repositoryFileResolver = model.getRepository().getRepositoryFileResolver();
    return new JsonHeroReferenceScanner(generics.getCharacterTypes(), repositoryFileResolver);
  }

  private HeroEnvironment getHeroEnvironment() {
    return HeroEnvironmentExtractor.getGenerics(model);
  }

  @Override
  public Item loadItem(CharacterIdentifier identifier) {
    CharacterItemModel character = modelsByIdentifier.get(identifier);
    if (character.isLoaded()) {
      return character.getItem();
    }
    Item item = persistenceModel.loadItem(identifier);
    character.setItem(item);
    initCharacterListening(item);
    return item;
  }

  private void initCharacterListening(Item item) {
    item.getItemData().getChangeManagement().addDirtyListener(dirtyListener);
  }

  @Override
  public void whenCurrentSelectionBecomesDirty(ChangeListener listener) {
    becomesDirtyAnnouncer.addListener(listener);
  }

  @Override
  public void whenCurrentSelectionBecomesClean(ChangeListener listener) {
    becomesCleanAnnouncer.addListener(listener);
  }

  @Override
  public void whenGetsSelection(ChangeListener listener) {
    getsSelectionListener.addListener(listener);
  }

  @Override
  public void whenCurrentSelectionBecomesExperienced(ChangeListener listener) {
    becomesExperiencedListener.addListener(listener);
  }

  @Override
  public void whenCurrentSelectionBecomesInexperienced(ChangeListener listener) {
    becomesInexperiencedListener.addListener(listener);
  }

  @Override
  public void convertCurrentToExperienced() {
    ExperienceModelFetcher.fetch(getCurrentCharacter()).setExperienced(true);
  }

  @Override
  public void printCurrentItemQuickly(Environment environment) {
    CharacterReportFinder reportFinder = createReportFinder(environment);
    new QuickPrintCommand(environment, reportFinder, getCurrentCharacter()).execute();
  }

  @Override
  public void printCurrentItemInto(Report report, Environment environment, SingleFileChooser fileChooser) {
    new ControlledPrintWithSelectedReport(environment, report, getCurrentCharacter(), fileChooser).execute();
  }

  @Override
  public void createNew(CharacterTemplateCreator factory, Environment environment) {
    ItemReceiver receiver = item -> {
      CharacterIdentifier identifier = new CharacterIdentifier("InternalNewCharacter" + newCharacterCount++);
      initCharacterListening(item);
      CharacterItemModel character = new CharacterItemModel(identifier, item);
      modelsByIdentifier.put(identifier, character);
      characterAddedListener.announce().added(character);
    };
    HeroEnvironment heroEnvironment = getHeroEnvironment();
    RepositoryItemPersister persister = new HeroItemPersister(heroEnvironment, model.getMessaging());
    ItemCreator itemCreator = new ItemCreator(new NewItemCreator(persister), receiver);
    CharacterItemCreationModel creationModel = new CharacterItemCreationModel(heroEnvironment);
    try {
      factory.createTemplate(itemCreator, creationModel);
    } catch (PersistenceException e) {
      environment.handle(e, environment.getString("CharacterSystem.NewCharacter.Error"));
    }
  }

  @Override
  public void whenNewCharacterIsAdded(NewCharacterListener listener) {
    characterAddedListener.addListener(listener);
  }

  @Override
  public void registerAllReportsOn(ReportRegister register, Environment environment) {
    CharacterReportFinder reportFinder = createReportFinder(environment);
    for (Report report : reportFinder.getAllReports(getCurrentCharacter())) {
      register.register(report);
    }
  }

  @Override
  public void setCurrentCharacter(CharacterIdentifier identifier) {
    this.currentCharacter = identifier;
    notifyDirtyListeners();
    notifyGetSelectionListeners();
    notifyExperiencedListeners();
  }

  private void notifyExperiencedListeners() {
    Hero hero = getCurrentCharacter();
    if (ExperienceModelFetcher.fetch(hero).isExperienced()) {
      becomesExperiencedListener.announce().changeOccurred();
    }
    if (!ExperienceModelFetcher.fetch(hero).isExperienced()) {
      becomesInexperiencedListener.announce().changeOccurred();
    }
  }

  private void notifyGetSelectionListeners() {
    getsSelectionListener.announce().changeOccurred();
  }

  private void notifyDirtyListeners() {
    if (currentCharacter == null) {
      return;
    }
    notifyDirtyListeners(getCurrentItem());
  }

  private void notifyDirtyListeners(Item item) {
    if (item == null) {
      return;
    }
    boolean dirty = item.getItemData().getChangeManagement().isDirty();
    if (dirty) {
      becomesDirtyAnnouncer.announce().changeOccurred();
    } else {
      becomesCleanAnnouncer.announce().changeOccurred();
    }
  }

  private Item getCurrentItem() {
    return modelsByIdentifier.get(currentCharacter).getItem();
  }

  private Hero getCurrentCharacter() {
    return (Hero) getCurrentItem().getItemData();
  }

  @Override
  public void saveCurrent() throws IOException {
    save(getCurrentItem());
  }

  private void save(Item item) throws IOException {
    persistenceModel.save(item);
    item.getItemData().getChangeManagement().setClean();
  }

  private CharacterReportFinder createReportFinder(Environment environment) {
    return new CharacterReportFinder(model, environment);
  }
}