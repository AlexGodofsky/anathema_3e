package net.sf.anathema.hero.charms.model.additional;

import net.sf.anathema.hero.abilities.model.AbilitiesModel;
import net.sf.anathema.hero.abilities.model.AbilitiesModelFetcher;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.learn.CharmLearnAdapter;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.splat.HeroType;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.state.TraitStateChangedListener;
import net.sf.anathema.hero.traits.model.state.TraitStateType;
import net.sf.anathema.library.event.IntegerChangedListener;
import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.reference.CharmName;

import java.util.ArrayList;
import java.util.List;

import static net.sf.anathema.hero.traits.model.state.CasteTraitStateType.Caste;
import static net.sf.anathema.hero.traits.model.state.FavoredTraitStateType.Favored;

//Called via Object Factory in CharmModel
@SuppressWarnings("unused")
public class ExcellencyAdditionalRules implements AdditionalCharmRules {

  private final static String id = "LearnsExcellenciesForFavoredAbilitiesAndWithOtherCharms";

  private final HeroType heroType;
  private final CharmsModel charms;
  private AbilitiesModel abilities;

  public ExcellencyAdditionalRules(CharmsModel charms, Hero hero) {
    this.heroType = hero.getSplat().getTemplateType().getHeroType();
    this.charms = charms;
    this.abilities = AbilitiesModelFetcher.fetch(hero);
  }

  protected String getStringForExcellency(String trait) {
    return heroType.getId() + ".Excellent" + heroType.getId() + trait;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void initialize() {
    charms.addCharmLearnListener(new CharmExcellencyMonitor());
    abilities.getAll().forEach(trait -> {
      TraitExcellencyMonitor monitor = new TraitExcellencyMonitor(trait.getType());
      abilities.getState(trait).addTraitStateChangedListener(monitor);
      trait.addCurrentValueListener(monitor);
    });
  }

  private void updateExcellency(TraitType type) {
    if (shouldKnowExcellency(type)) {
      learnExcellency(type);
    } else {
      forgetExcellency(type);
    }
  }

  private boolean shouldKnowExcellency(TraitType traitType) {
    Trait trait = abilities.getTrait(traitType);
    TraitStateType state = abilities.getState(trait).getType();
    boolean isCasteOrFavored = state.countsAs(Caste) || state.countsAs(Favored);
    if (isCasteOrFavored && trait.getCurrentValue() >= 1) {
      return true;
    }
    List<TraitType> traitTypes = new ArrayList<>();
    traitTypes.add(traitType);
    return charms.hasLearnedThresholdCharmsOfTrait(traitTypes, null, 1, 1);
  }

  private void forgetExcellency(TraitType type) {
    Charm excellency = charms.getCharmById(new CharmName(getStringForExcellency(type.getId())));
    if (charms.isLearned(excellency)) {
      charms.getLearningModel().forgetCharm(excellency, false);
    }
  }

  private void learnExcellency(TraitType type) {
    Charm excellency = charms.getCharmById(new CharmName(getStringForExcellency(type.getId())));
    if (!charms.isLearned(excellency)) {
      charms.getLearningModel().learnCharm(excellency, false);
    }
  }

  private class TraitExcellencyMonitor implements TraitStateChangedListener, IntegerChangedListener {

    private TraitType type;

    public TraitExcellencyMonitor(TraitType type) {
      this.type = type;
    }

    @Override
    public void favorableStateChanged(TraitStateType newState) {
      updateExcellency(type);
    }

    @Override
    public void valueChanged(int newValue) {
      updateExcellency(type);
    }
  }


  private class CharmExcellencyMonitor extends CharmLearnAdapter {

    @Override
    public void charmLearned(Charm charm) {
      updateExcellency(new TraitType(charm.getPrerequisites().getPrimaryTraitType().type));
    }

    @Override
    public void charmForgotten(Charm charm) {
      updateExcellency(new TraitType(charm.getPrerequisites().getPrimaryTraitType().type));
    }
  }
}