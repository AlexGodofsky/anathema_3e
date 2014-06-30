package net.sf.anathema.hero.spiritual.model.traits;

import net.sf.anathema.hero.traits.model.ValuedTraitType;
import net.sf.anathema.hero.traits.model.rules.limitation.TraitLimitation;
import net.sf.anathema.hero.traits.model.types.OtherTraitType;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.exception.UnreachableCodeReachedException;

public class EssenceBasedLimitation implements TraitLimitation {

  @Override
  public int getAbsoluteLimit(Hero hero) {
    SpiritualTraitModel spiritualTraitModel = getOtherTraitModel(hero);
    TraitLimitation essenceLimitation = spiritualTraitModel.getEssenceLimitation();
    int essenceMaximum = essenceLimitation.getAbsoluteLimit(hero);
    return Math.max(essenceMaximum, 5);
  }

  @Override
  public int getCurrentMaximum(Hero hero, boolean modified) {
    SpiritualTraitModel spiritualTraitModel = getOtherTraitModel(hero);
    ValuedTraitType essence = spiritualTraitModel.getTrait(OtherTraitType.Essence);
    int currentEssence = Math.min(essence.getCurrentValue(), spiritualTraitModel.getEssenceCap(modified));
    int currentEssenceValue = Math.max(currentEssence, 5);
    return Math.min(getAbsoluteLimit(hero), currentEssenceValue);
  }

  private SpiritualTraitModel getOtherTraitModel(Hero hero) {
    return SpiritualTraitModelFetcher.fetch(hero);
  }

  @Override
  public EssenceBasedLimitation clone() {
    try {
      return (EssenceBasedLimitation) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new UnreachableCodeReachedException(e);
    }
  }
}