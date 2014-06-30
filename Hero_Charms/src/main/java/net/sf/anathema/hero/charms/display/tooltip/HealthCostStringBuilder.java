package net.sf.anathema.hero.charms.display.tooltip;

import net.sf.anathema.charm.data.cost.HealthCost;
import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.lib.gui.ConfigurableTooltip;

public class HealthCostStringBuilder extends AbstractCostStringBuilder<HealthCost> {

  public HealthCostStringBuilder(Resources resources, String key) {
    super(resources, key, key);
  }

  public HealthCostStringBuilder(Resources resources, String singularKey, String pluralKey) {
    super(resources, singularKey, pluralKey);
  }

  @Override
  protected String getQualifiedValueString(HealthCost cost) {
    int intValue = Integer.parseInt(cost.getCost());
    return intValue + ConfigurableTooltip.Space + getResources().getString(cost.getType().getId()) + ConfigurableTooltip.Space +
           getResources().getString(intValue == 1 ? getSingularKey() : getPluralKey());
  }
}