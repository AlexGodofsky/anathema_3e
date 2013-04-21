package net.sf.anathema.character.library.trait.experience;

import net.sf.anathema.character.generic.framework.additionaltemplate.model.IBasicTrait;
import net.sf.anathema.character.generic.template.experience.CurrentRatingCosts;

public class TraitRatingCostCalculator {

  public static int getTraitRatingCosts(IBasicTrait trait, CurrentRatingCosts ratingCosts) {
    return getTraitRaitingCosts(trait.getCreationValue(), trait.getExperiencedValue(), ratingCosts);
  }

  public static int getTraitRaitingCosts(int creationValue, int experiencedValue, CurrentRatingCosts ratingCosts) {
    int traitCosts = 0;
    int currentRating = creationValue;
    while (currentRating < experiencedValue) {
      traitCosts += ratingCosts.getRatingCosts(currentRating);
      currentRating++;
    }
    return traitCosts;
  }
}