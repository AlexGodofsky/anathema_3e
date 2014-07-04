package net.sf.anathema.hero.framework.perspective;

import com.google.common.base.Objects;
import net.sf.anathema.hero.creation.ICharacterItemCreationModel;
import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.hero.framework.HeroTemplateHolder;
import net.sf.anathema.hero.framework.type.CharacterType;
import net.sf.anathema.hero.framework.type.CharacterTypes;
import net.sf.anathema.hero.template.HeroTemplate;
import net.sf.anathema.hero.template.TemplateRegistry;
import net.sf.anathema.library.collection.MultiEntryMap;
import net.sf.anathema.library.event.ChangeListener;
import org.jmock.example.announcer.Announcer;

import java.util.ArrayList;
import java.util.List;

public class CharacterItemCreationModel implements ICharacterItemCreationModel {

  private final Announcer<ChangeListener> control = Announcer.to(ChangeListener.class);
  private final MultiEntryMap<CharacterType, HeroTemplate> templatesByType = new MultiEntryMap<>();
  private final List<CharacterType> availableCharacterTypes = new ArrayList<>();
  private final HeroEnvironment generics;
  private final HeroTemplateHolder templateHolder = new HeroTemplateHolder();
  private CharacterType selectedType;

  public CharacterItemCreationModel(HeroEnvironment generics) {
    this.generics = generics;
    initializeTypesAndTemplates();
    setCharacterType(availableCharacterTypes.get(0));
  }

  private void initializeTypesAndTemplates() {
    CharacterTypes types = generics.getCharacterTypes();
    TemplateRegistry templateRegistry = generics.getTemplateRegistry();
    for (CharacterType type : types) {
      HeroTemplate[] templates = templateRegistry.getAllSupportedTemplates(type);
      if (templates.length > 0) {
        availableCharacterTypes.add(type);
        templatesByType.add(type, templates);
      }
    }
  }

  @Override
  public Iterable<CharacterType> getAvailableCharacterTypes() {
    return availableCharacterTypes;
  }

  @Override
  public void setCharacterType(CharacterType type) {
    if (Objects.equal(selectedType, type)) {
      return;
    }
    this.selectedType = type;
    setTemplateToDefault();
    control.announce().changeOccurred();
  }

  private void setTemplateToDefault() {
    HeroTemplate defaultTemplate = generics.getTemplateRegistry().getAllSupportedTemplates(selectedType)[0];
    templateHolder.setTemplate(defaultTemplate);
  }

  @Override
  public HeroTemplate[] getAvailableTemplates() {
    List<HeroTemplate> list = templatesByType.get(selectedType);
    List<HeroTemplate> copyList = new ArrayList<>(list);
    return copyList.toArray(new HeroTemplate[copyList.size()]);
  }

  @Override
  public void setSelectedTemplate(HeroTemplate newValue) {
    if (templateHolder.isCurrentlySelected(newValue)) {
      return;
    }
    templateHolder.setTemplate(newValue);
    control.announce().changeOccurred();
  }

  @Override
  public HeroTemplate getSelectedTemplate() {
    return templateHolder.getTemplate();
  }

  @Override
  public void addListener(ChangeListener listener) {
    control.addListener(listener);
  }
}