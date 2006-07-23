package net.sf.anathema.character.equipment.creation;

import java.awt.Component;

import net.sf.anathema.character.equipment.creation.model.IEquipmentStatisticsCreationModel;
import net.sf.anathema.character.equipment.creation.model.IShieldStatisticsModel;
import net.sf.anathema.character.equipment.creation.properties.ShieldStatisticsProperties;
import net.sf.anathema.lib.resources.IResources;

public class ShieldStatisticsPresenterPage extends
    AbstractEquipmentStatisticsPresenterPage<IShieldStatisticsModel, ShieldStatisticsProperties> {

  public ShieldStatisticsPresenterPage(
      IResources resources,
      IEquipmentStatisticsCreationModel model,
      IEquipmentStatisticsCreationViewFactory viewFactory) {
    super(resources, new ShieldStatisticsProperties(resources), model, model.getShieldStatisticsModel(), viewFactory);
  }

  @Override
  protected void addAdditionalContent() {
    addLabelledComponentRow(new String[] {
        getProperties().getCloseCombatDvBonusLabel(),
        getProperties().getRangedCombatDvBonusLabel() }, new Component[] {
        initIntegerSpinner(getPageModel().getCloseCombatDvBonusModel(), 1).getComponent(),
        initIntegerSpinner(getPageModel().getRangedCombatDvBonusModel(), 1).getComponent() });
  }

  @Override
  protected boolean isTagsSupported() {
    return false;
  }
}