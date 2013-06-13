package net.sf.anathema.character.equipment.impl.creation.model;

import net.sf.anathema.character.equipment.creation.model.stats.IOffensiveStatisticsModel;
import net.sf.anathema.character.equipment.creation.model.stats.IWeaponDamageModel;
import net.sf.anathema.lib.data.Range;
import net.sf.anathema.lib.workflow.intvalue.IIntValueModel;
import net.sf.anathema.lib.workflow.intvalue.RangedIntValueModel;

public abstract class OffensiveStatisticsModel extends EquipmentStatisticsModel implements IOffensiveStatisticsModel {

  private final IIntValueModel speedModel;
  private final IIntValueModel accuracyModel = new RangedIntValueModel(0);
  private final IIntValueModel rateModel = new RangedIntValueModel(new Range(1, Integer.MAX_VALUE), 1);
  private final IWeaponDamageModel weaponDamageModel = new WeaponDamageModel();

  public OffensiveStatisticsModel(IIntValueModel speedModel) {
    this.speedModel = speedModel;
  }

  @Override
  public IIntValueModel getSpeedModel() {
    return speedModel;
  }

  @Override
  public IIntValueModel getAccuracyModel() {
    return accuracyModel;
  }

  @Override
  public IIntValueModel getRateModel() {
    return rateModel;
  }

  @Override
  public IWeaponDamageModel getWeaponDamageModel() {
    return weaponDamageModel;
  }
}