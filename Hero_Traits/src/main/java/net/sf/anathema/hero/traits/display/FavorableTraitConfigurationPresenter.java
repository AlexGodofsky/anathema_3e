package net.sf.anathema.hero.traits.display;

import net.sf.anathema.hero.display.fx.dot.ExtensibleDotView;
import net.sf.anathema.hero.display.fx.dot.GroupedFavorableDotConfigurationView;
import net.sf.anathema.hero.environment.herotype.PresentationPropertiesImpl;
import net.sf.anathema.hero.experience.model.ExperienceChange;
import net.sf.anathema.hero.experience.model.ExperienceModelFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.FavorableState;
import net.sf.anathema.hero.traits.model.ITraitFavorization;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.lists.DefaultTraitTypeList;
import net.sf.anathema.hero.traits.model.lists.IdentifiedTraitTypeList;
import net.sf.anathema.library.collection.IdentityMapping;
import net.sf.anathema.library.interaction.model.ToggleTool;
import net.sf.anathema.library.resources.Resources;

import java.util.List;

import static net.sf.anathema.hero.traits.model.FavorableState.Caste;
import static net.sf.anathema.hero.traits.model.FavorableState.Favored;

public class FavorableTraitConfigurationPresenter {

  private final GroupedFavorableDotConfigurationView view;
  private final IdentityMapping<Trait, ToggleTool> traitViewsByTrait = new IdentityMapping<>();
  private final Resources resources;
  private final IdentifiedTraitTypeList[] traitTypeGroups;
  private final TraitMap traitConfiguration;
  private Hero hero;

  public FavorableTraitConfigurationPresenter(IdentifiedTraitTypeList[] traitTypeGroups, Hero hero, GroupedFavorableDotConfigurationView view,
                                              Resources resources) {
    this.hero = hero;
    this.traitTypeGroups = traitTypeGroups;
    this.traitConfiguration = TraitModelFetcher.fetch(hero);
    this.resources = resources;
    this.view = view;
  }

  public void init(String typePrefix) {
    for (IdentifiedTraitTypeList traitTypeGroup : traitTypeGroups) {
      view.startNewTraitGroup(resources.getString(typePrefix + "." + traitTypeGroup.getListId().getId()));
      List<TraitType> allTraitTypes = traitTypeGroup.getAll();
      addTraitViews(traitConfiguration.getTraits(allTraitTypes.toArray(new TraitType[allTraitTypes.size()])));
    }
    hero.getChangeAnnouncer().addListener(flavor -> {
      if (flavor == ExperienceChange.FLAVOR_EXPERIENCE_STATE) {
        updateButtons();
      }
    });
    updateButtons();
  }

  private void updateButtons() {
    for (Trait trait : getAllTraits()) {
      ToggleTool view = traitViewsByTrait.get(trait);
      boolean disabled = ExperienceModelFetcher.fetch(hero).isExperienced();// || trait.getFavorization().isCaste();
      boolean favored = trait.getFavorization().isCasteOrFavored();
      setButtonState(view, favored, !disabled);
    }
  }

  private Trait[] getAllTraits() {
    return traitConfiguration.getTraits(DefaultTraitTypeList.getAllTraitTypes(traitTypeGroups));
  }

  private void addTraitViews(Trait[] traits) {
    for (Trait trait : traits) {
      addTraitView(trait);
    }
  }

  private void addTraitView(Trait favorableTrait) {
    ExtensibleDotView traitView = createTraitView(favorableTrait);
    addCasteAndFavoredToggle(favorableTrait, traitView);
  }

  private ExtensibleDotView createTraitView(Trait favorableTrait) {
    String traitName = resources.getString(favorableTrait.getType().getId());
    ExtensibleDotView traitView = view.addExtensibleTraitView(traitName, favorableTrait.getMaximalValue());
    new TraitPresenter(favorableTrait, traitView.getIntValueView()).initPresentation();
    return traitView;
  }

  private void addCasteAndFavoredToggle(final Trait favorableTrait, ExtensibleDotView traitView) {
    final ToggleTool casteTool = traitView.addToggleInFront();
    casteTool.setCommand(() -> {
      ITraitFavorization favorization = favorableTrait.getFavorization();
      favorization.advanceFavorableState();
    });
    favorableTrait.getFavorization().addFavorableStateChangedListener(state -> updateView(casteTool, state));
    updateView(casteTool, favorableTrait.getFavorization().getFavorableState());
    traitViewsByTrait.put(favorableTrait, casteTool);
  }

  private void updateView(final ToggleTool view, FavorableState state) {
    boolean select = state == Favored || state == Caste;
    boolean enable = true; // state == Favored || state == Default;
    setButtonState(view, select, enable);
    PresentationPropertiesImpl properties = new PresentationPropertiesImpl(hero.getSplat());
    new FavoredIconSelector(view, properties).setIconFor(hero, state);
  }

  private void setButtonState(ToggleTool view, boolean select, boolean enable) {
    select(view, select);
    enable(view, enable);
  }

  private void select(ToggleTool view, boolean select) {
    if (select) {
      view.select();
    } else {
      view.deselect();
    }
  }

  private void enable(ToggleTool view, boolean enable) {
    if (enable) {
      view.enable();
    } else {
      view.disable();
    }
  }
}