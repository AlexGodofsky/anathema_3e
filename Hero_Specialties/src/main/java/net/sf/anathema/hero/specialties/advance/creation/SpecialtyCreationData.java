package net.sf.anathema.hero.specialties.advance.creation;

import net.sf.anathema.hero.specialties.template.SpecialtyPointsTemplate;
import net.sf.anathema.hero.traits.advance.TraitListCreationData;
import net.sf.anathema.hero.traits.model.TraitType;

public class SpecialtyCreationData implements TraitListCreationData {

  private SpecialtyPointsTemplate template;

  public SpecialtyCreationData(SpecialtyPointsTemplate template) {
    this.template = template;
  }

  @Override
  public int getCalculationBase(TraitType type) {
    return template.standard.calculationBase;
  }

  public int getCreationDots() {
    return template.creationPoints;
  }
}
