package net.sf.anathema.character.equipment.impl.creation.model;

import net.sf.anathema.character.equipment.creation.model.stats.IRangedCombatStatisticsModel;
import net.sf.anathema.lib.data.Range;
import net.sf.anathema.lib.workflow.intvalue.IIntValueModel;
import net.sf.anathema.lib.workflow.intvalue.RangedIntValueModel;

public class RangedWeaponStatisticsModel extends OffensiveStatisticsModel implements IRangedCombatStatisticsModel {

  public RangedWeaponStatisticsModel(IIntValueModel speedModel) {
    super(speedModel);
  }

  private final IIntValueModel rangeModel = new RangedIntValueModel(new Range(1, Integer.MAX_VALUE), 1);

  @Override
  public IIntValueModel getRangeModel() {
    return rangeModel;
  }

  @Override
  public boolean supportsRate() {
    return true;
  }
}