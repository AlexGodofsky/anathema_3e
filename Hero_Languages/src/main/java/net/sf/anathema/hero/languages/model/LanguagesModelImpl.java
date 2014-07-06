package net.sf.anathema.hero.languages.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.sf.anathema.hero.abilities.model.AbilitiesModelFetcher;
import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.RemovableEntryChangeAdapter;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.event.TraitChangeFlavor;
import net.sf.anathema.hero.traits.model.types.AbilityType;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import net.sf.anathema.library.lang.StringUtilities;
import net.sf.anathema.library.model.AbstractRemovableEntryModel;
import org.jmock.example.announcer.Announcer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LanguagesModelImpl extends AbstractRemovableEntryModel<Identifier> implements LanguagesModel {

  private static final int barbarianLanguagesPerPoint = 4;
  private final List<Identifier> languages = Lists.newArrayList(
          new SimpleIdentifier("HighRealm"), new SimpleIdentifier("LowRealm"), new SimpleIdentifier("OldRealm"),
          new SimpleIdentifier("Riverspeak"), new SimpleIdentifier("Skytongue"), new SimpleIdentifier("Flametongue"),
          new SimpleIdentifier("Seatongue"), new SimpleIdentifier("Foresttongue"), new SimpleIdentifier("GuildCant"),
          new SimpleIdentifier("ClawSpeak"), new SimpleIdentifier("HighHolySpeech"), new SimpleIdentifier("Pelagial"));

  private Identifier selection;
  private int languagePointsAllowed;
  private Hero hero;
  private final Announcer<ChangeListener> pointControl = Announcer.to(ChangeListener.class);

  @Override
  public Identifier getId() {
    return ID;
  }

  @Override
  public void initialize(HeroEnvironment environment, Hero hero) {
    this.hero = hero;
    updateLanguagePointAllowance();
  }

  @Override
  public void initializeListening(final ChangeAnnouncer announcer) {
    addModelChangeListener(new RemovableEntryChangeAdapter<>(announcer));
    announcer.addListener(flavor -> {
      if (TraitChangeFlavor.changes(flavor, AbilityType.Linguistics)) {
        updateLanguagePointAllowance();
      }
    });
  }

  private void updateLanguagePointAllowance() {
    int currentPoints = languagePointsAllowed;
    Trait linguistics = AbilitiesModelFetcher.fetch(hero).getTrait(AbilityType.Linguistics);
    languagePointsAllowed = linguistics.getCurrentValue() + 1;
    if (currentPoints != languagePointsAllowed) {
      pointControl.announce().changeOccurred();
    }
  }

  @Override
  public Collection<Identifier> getPredefinedLanguages() {
    return new ArrayList<>(languages);
  }

  @Override
  protected Identifier createEntry() {
    Identifier selectionCopy = selection;
    this.selection = null;
    fireEntryChanged();
    return selectionCopy;
  }

  @Override
  public boolean isEntryAllowed() {
    return selection != null && !getEntries().contains(selection);
  }

  @Override
  public boolean isPredefinedLanguage(Object object) {
    return languages.contains(object);
  }

  @Override
  public void selectBarbarianLanguage(String customName) {
    if (StringUtilities.isNullOrTrimmedEmpty(customName)) {
      this.selection = null;
      fireEntryChanged();
    }
    selectLanguage(new SimpleIdentifier(customName));
  }

  @Override
  public void selectLanguage(final Identifier language) {
    Preconditions.checkNotNull(language);
    Identifier foundLanguage = Iterables.find(getEntries(), selectedLanguage -> Objects.equal(language, selectedLanguage), null);
    if (foundLanguage != null) {
      fireEntryChanged();
      return;
    }
    this.selection = language;
    fireEntryChanged();
  }

  @Override
  public Identifier getPredefinedLanguageById(final String id) {
    return languages.stream().filter(definedLanuage -> Objects.equal(id, definedLanuage.getId())).findFirst().orElse(null);
  }

  @Override
  public void addCharacterChangedListener(ChangeListener listener) {
    pointControl.addListener(listener);
  }

  @Override
  public int getBarbarianLanguageCount() {
    int count = 0;
    for (Identifier language : getEntries()) {
      if (!isPredefinedLanguage(language)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int getLanguagePointsAllowed() {
    updateLanguagePointAllowance();
    return languagePointsAllowed;
  }

  @Override
  public Identifier getSelectedEntry() {
    return selection;
  }

  @Override
  public int getLanguagePointsSpent() {
    int spent = getPredefinedLanguageCount();
    spent += Math.ceil((double) getBarbarianLanguageCount() / barbarianLanguagesPerPoint);
    return spent;
  }

  @Override
  public int getPredefinedLanguageCount() {
    int count = 0;
    for (Identifier language : getEntries()) {
      if (isPredefinedLanguage(language)) {
        count++;
      }
    }
    return count;
  }
}