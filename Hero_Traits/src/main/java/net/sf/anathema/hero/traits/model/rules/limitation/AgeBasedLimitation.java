package net.sf.anathema.hero.traits.model.rules.limitation;

import net.sf.anathema.hero.concept.HeroConcept;
import net.sf.anathema.hero.concept.HeroConceptFetcher;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.traits.model.TraitLimitation;

public class AgeBasedLimitation implements TraitLimitation {
  private final int absoluteLimit;
  private final int[] ageArray = {100, 250, 500, 1000};

  public AgeBasedLimitation(int maxValue) {
    this.absoluteLimit = maxValue;
  }

  @Override
  public int getAbsoluteLimit(Hero hero) {
    return absoluteLimit;
  }

  @Override
  public int getCurrentMaximum(Hero hero, boolean modified) {
    HeroConcept concept = HeroConceptFetcher.fetch(hero);
    int age = concept.getAge().getValue();
    for (int categories = 0; categories != ageArray.length; categories++) {
      if (age < ageArray[categories]) {
        return 5 + categories;
      }
    }
    return absoluteLimit;
  }

  @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
  @Override
  public AgeBasedLimitation clone() {
    try {
      return (AgeBasedLimitation) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}