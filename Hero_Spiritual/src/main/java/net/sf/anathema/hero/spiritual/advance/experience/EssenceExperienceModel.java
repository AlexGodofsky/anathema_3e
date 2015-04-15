package net.sf.anathema.hero.spiritual.advance.experience;

import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModel;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.points.display.overview.model.AbstractIntegerValueModel;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Essence;

public class EssenceExperienceModel extends AbstractIntegerValueModel {
  private final SpiritualTraitModel spiritualTraits;
  private final SpiritualExperienceCalculator calculator;

  public EssenceExperienceModel(SpiritualTraitModel spiritualTraits, SpiritualExperienceCalculator calculator) {
    super("Experience", "Essence");
    this.spiritualTraits = spiritualTraits;
    this.calculator = calculator;
  }

  @Override
  public Integer getValue() {
    Trait essence = spiritualTraits.getTrait(Essence);
    return calculator.getEssenceCosts(essence);
  }
}