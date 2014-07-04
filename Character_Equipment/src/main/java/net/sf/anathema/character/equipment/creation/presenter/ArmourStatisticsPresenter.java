package net.sf.anathema.character.equipment.creation.presenter;

import net.sf.anathema.character.equipment.creation.presenter.stats.properties.ArmourStatisticsProperties;
import net.sf.anathema.hero.display.fx.configurableview.IIntegerSpinner;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.library.event.IBooleanValueChangedListener;
import net.sf.anathema.library.event.IntValueChangedListener;
import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.library.interaction.model.ToggleTool;
import net.sf.anathema.library.model.BooleanValueModel;
import net.sf.anathema.library.resources.Resources;

public class ArmourStatisticsPresenter {
  private final EquipmentStatsView view;
  private final ArmourStatisticsProperties properties;
  private final IArmourStatisticsModel armourModel;


  public ArmourStatisticsPresenter(IArmourStatisticsModel armourModel, EquipmentStatsView view, Resources resources) {
    this.view = view;
    this.properties = new ArmourStatisticsProperties(resources);
    this.armourModel = armourModel;
  }

  public void initPresentation() {
    addSpinner(properties.getBashingSoakLabel(), armourModel.getBashingSoakModel());
    addSpinner(properties.getBashingHardnessLabel(), armourModel.getBashingHardnessModel());

    IIntValueModel lethalSoakModel = armourModel.getLethalSoakModel();
    addSpinner(properties.getLethalSoakLabel(), lethalSoakModel);
    addSpinner(properties.getLethalHardnessLabel(), armourModel.getLethalHardnessModel());

    final IIntValueModel aggravatedSoakModel = armourModel.getSoakModel(HealthType.Aggravated);
    IIntegerSpinner aggravatedSoakSpinner = addSpinner(properties.getAggravatedSoakLabel(), aggravatedSoakModel);
    ToggleTool tool = view.addToggleTool();
    tool.setText(properties.getLinkSoakLabel());

    addSpinner(properties.getMobilityPenaltyLabel(), armourModel.getMobilityPenaltyModel());
    addSpinner(properties.getFatigueLabel(), armourModel.getFatigueModel());

    tool.setCommand(new Command() {
      @Override
      public void execute() {
        BooleanValueModel soakLinkModel = armourModel.getSoakLinkModel();
        soakLinkModel.setValue(!soakLinkModel.getValue());
      }
    });
    armourModel.getSoakLinkModel().addChangeListener(new IBooleanValueChangedListener() {
      @Override
      public void valueChanged(boolean isLinkToggled) {
        if (isLinkToggled) {
          aggravatedSoakModel.setValue(lethalSoakModel.getValue());
        }
        ArmourStatisticsPresenter.this.setToolBasedOnLinkState(tool, aggravatedSoakSpinner);
      }
    });
    boolean linked = lethalSoakModel.getValue() == aggravatedSoakModel.getValue();
    armourModel.getSoakLinkModel().setValue(linked);
    setToolBasedOnLinkState(tool, aggravatedSoakSpinner);
    lethalSoakModel.addIntValueChangeListener(new IntValueChangedListener() {
      @Override
      public void valueChanged(int newValue) {
        if (armourModel.getSoakLinkModel().getValue()) {
          aggravatedSoakModel.setValue(newValue);
        }
      }
    });
  }

  private void setToolBasedOnLinkState(ToggleTool tool, IIntegerSpinner aggravatedSoakSpinner) {
    if (armourModel.getSoakLinkModel().getValue()) {
      aggravatedSoakSpinner.setEnabled(false);
      tool.select();
    } else {
      aggravatedSoakSpinner.setEnabled(true);
      tool.deselect();
    }
  }

  private IIntegerSpinner addSpinner(String label, IIntValueModel model) {
    IIntegerSpinner spinner = view.addIntegerSpinner(label);
    new IntValuePresentation().initPresentation(spinner, model);
    return spinner;
  }
}