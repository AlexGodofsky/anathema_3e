package net.sf.anathema.character.equipment.creation.presenter.stats.properties;

import net.sf.anathema.lib.resources.Resources;

public abstract class OffensiveStatisticsProperties extends AbstractEquipmentStatisticsProperties {

  public OffensiveStatisticsProperties(Resources resources) {
    super(resources);
  }

  public String getAccuracyLabel() {
    return getLabelString("Equipment.Stats.Long.Accuracy"); //$NON-NLS-1$    
  }

  public String getSpeedLabel() {
    return getLabelString("Equipment.Stats.Long.Speed"); //$NON-NLS-1$
  }

  public String getRateLabel() {
    return getLabelString("Equipment.Stats.Long.Rate"); //$NON-NLS-1$
  }
}