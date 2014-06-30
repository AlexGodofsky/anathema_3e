package net.sf.anathema.hero.abilities.advance.experience;

import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.abilities.model.AbilitiesModel;
import net.sf.anathema.hero.points.display.overview.model.AbstractIntegerValueModel;
import net.sf.anathema.hero.traits.model.TraitMap;

public class AbilityExperienceModel extends AbstractIntegerValueModel {

  private final TraitMap abilities;
  private final AbilityExperienceCalculator calculator;

  public AbilityExperienceModel(AbilitiesModel abilities, AbilityExperienceCalculator calculator) {
    super("Experience", "Abilities");
    this.abilities = abilities;
    this.calculator = calculator;
  }

  @Override
  public Integer getValue() {
    return getAbilityCosts();
  }

  private int getAbilityCosts() {
    int experienceCosts = 0;
    for (Trait ability : abilities.getAll()) {
      experienceCosts += calculator.getAbilityCosts(ability, ability.getFavorization().isCaste() || ability.getFavorization().isFavored());
    }
    return experienceCosts;
  }
}