package net.sf.anathema.hero.specialties.model;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.types.AbilityType;

import java.util.Collection;

public class HighestSpecialty {

  private int value = 0;
  private String name;

  public HighestSpecialty(Hero hero, AbilityType type) {
    for (Specialty t : getSpecialties(hero, type)) {
      if (value < t.getCurrentValue()) {
        value = t.getCurrentValue();
        name = t.getName();
      }
    }
  }

  public int getValue() {
    return value;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

  public Collection<Specialty> getSpecialties(Hero hero, TraitType traitType) {
    SpecialtiesModel specialtyConfiguration = SpecialtiesModelFetcher.fetch(hero);
    ISubTraitContainer specialtiesContainer = specialtyConfiguration.getSpecialtiesContainer(traitType);
    return specialtiesContainer.getSubTraits();
  }
}