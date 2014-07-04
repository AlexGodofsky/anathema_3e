package net.sf.anathema.character.equipment.item;

import net.sf.anathema.character.equipment.item.model.IEquipmentDatabaseManagement;
import net.sf.anathema.character.equipment.item.view.EquipmentNavigation;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.library.event.ObjectValueListener;
import net.sf.anathema.library.resources.Resources;

public class RemoveEquipmentTemplateAction {
  private final IEquipmentDatabaseManagement model;
  private StatsEditModel editModel;
  private final Resources resources;

  public RemoveEquipmentTemplateAction(Resources resources, IEquipmentDatabaseManagement model, StatsEditModel editModel) {
    this.resources = resources;
    this.model = model;
    this.editModel = editModel;
  }

  public void addToolTo(EquipmentNavigation view) {
    final Tool removeTool = view.addEditTemplateTool();
    removeTool.setIcon(new RelativePath("icons/ButtonCross24.png"));
    removeTool.setTooltip(resources.getString("Equipment.Creation.Item.RemoveActionTooltip"));
    view.getTemplateListView().addObjectSelectionChangedListener(new EnableWhenItemSelected(removeTool));
    updateEnabled(removeTool, view.getTemplateListView().getSelectedObject());
    removeTool.setCommand(new RemoveEquipmentItem(view, model, resources,editModel));
  }

  private void updateEnabled(Tool removeTool, String selectedObject) {
    if (selectedObject != null) {
      removeTool.enable();
    } else {
      removeTool.disable();
    }
  }

  private class EnableWhenItemSelected implements ObjectValueListener<String> {
    private final Tool removeTool;

    public EnableWhenItemSelected(Tool removeTool) {
      this.removeTool = removeTool;
    }

    @Override
    public void valueChanged(String newValue) {
      updateEnabled(removeTool, newValue);
    }
  }
}