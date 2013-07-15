package net.sf.anathema.character.main.traits;

import net.sf.anathema.character.main.template.ITraitLimitation;
import net.sf.anathema.character.main.traits.limitation.EssenceBasedLimitation;
import net.sf.anathema.character.main.traits.limitation.StaticTraitLimitation;
import net.sf.anathema.character.main.traits.limitation.VirtueBasedLimitation;
import net.sf.anathema.character.main.traits.types.VirtueType;
import net.sf.anathema.hero.model.Hero;

public class SimpleTraitTemplate extends AbstractTraitTemplate {

  public static ITraitTemplate createEssenceLimitedTemplate(int minimumValue) {
    return createEssenceLimitedTemplate(minimumValue, minimumValue, LowerableState.Default);
  }

  public static ITraitTemplate createEssenceLimitedTemplate(int minimumValue, int startValue, LowerableState state) {
    return new SimpleTraitTemplate(minimumValue, startValue, new EssenceBasedLimitation(), state);
  }

  public static ITraitTemplate createVirtueLimitedTemplate(int minimumValue, int startValue, LowerableState state, VirtueType type) {
    return new SimpleTraitTemplate(minimumValue, startValue, new VirtueBasedLimitation(type), state);
  }

  public static ITraitTemplate createStaticLimitedTemplate(int minimumValue, int staticLimit) {
    return createStaticLimitedTemplate(minimumValue, staticLimit, LowerableState.Default);
  }

  public static ITraitTemplate createStaticLimitedTemplate(int minimumValue, int staticLimit, LowerableState state) {
    return new SimpleTraitTemplate(minimumValue, minimumValue, new StaticTraitLimitation(staticLimit), state);
  }

  private final int minimumValue;
  private final ITraitLimitation limitation;

  private SimpleTraitTemplate(int minimumValue, int startValue, ITraitLimitation limitation, LowerableState lowerable) {
    super(startValue, lowerable, startValue);
    this.minimumValue = minimumValue;
    this.limitation = limitation;
  }

  @Override
  public int getMinimumValue(Hero hero) {
    return minimumValue;
  }

  @Override
  public ITraitLimitation getLimitation() {
    return limitation;
  }
}