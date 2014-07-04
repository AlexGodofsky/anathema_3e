package net.sf.anathema.character.equipment.creation.model;

import net.sf.anathema.character.equipment.creation.presenter.IArtifactStatisticsModel;
import net.sf.anathema.character.equipment.creation.presenter.IIntValueModel;
import net.sf.anathema.character.equipment.creation.presenter.RangedIntValueModel;
import net.sf.anathema.library.Range;
import net.sf.anathema.library.model.BooleanValueModel;

public class ArtifactStatisticsModel extends EquipmentStatisticsModel implements IArtifactStatisticsModel {
  private final IIntValueModel attuneCostModel = new RangedIntValueModel(new Range(0, Integer.MAX_VALUE), 0);
  private final BooleanValueModel allowForeignModel = new BooleanValueModel(true);
  private final BooleanValueModel requireAttuneModel = new BooleanValueModel(false);

  @Override
  public IIntValueModel getAttuneCostModel() {
    return attuneCostModel;
  }

  @Override
  public BooleanValueModel getForeignAttunementModel() {
    return allowForeignModel;
  }

  @Override
  public BooleanValueModel getRequireAttunementModel() {
    return requireAttuneModel;
  }

}
