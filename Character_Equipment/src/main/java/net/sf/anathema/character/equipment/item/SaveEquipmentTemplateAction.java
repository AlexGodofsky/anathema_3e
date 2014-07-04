package net.sf.anathema.character.equipment.item;

import net.sf.anathema.character.equipment.item.model.IEquipmentDatabaseManagement;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateEditModel;
import net.sf.anathema.character.equipment.item.view.EquipmentNavigation;
import net.sf.anathema.equipment.core.IEquipmentTemplate;
import net.sf.anathema.framework.repository.tree.VetorFactory;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.interaction.Hotkey;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.gui.list.veto.Vetor;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.event.ObjectValueListener;
import net.sf.anathema.library.resources.Resources;

public class SaveEquipmentTemplateAction {
  private final Resources resources;
  private final IEquipmentDatabaseManagement model;

  public SaveEquipmentTemplateAction(Resources resources, IEquipmentDatabaseManagement model) {
    this.resources = resources;
    this.model = model;
  }

  public void addToolTo(EquipmentNavigation view) {
    Tool saveTool = view.addEditTemplateTool();
    saveTool.setIcon(new RelativePath("icons/TaskBarSave24.png"));
    saveTool.setTooltip(resources.getString("Equipment.Creation.Item.SaveActionTooltip"));
    initListening(saveTool);
    saveTool.setCommand(new SaveChangedEquipment(view));
    saveTool.setHotkey(new Hotkey('S'));
  }

  private void initListening(Tool saveTool) {
    UpdateOnChange changeListener = new UpdateOnChange(saveTool);
    model.getTemplateEditModel().getDescription().getName().addTextChangedListener(changeListener);
    model.getTemplateEditModel().getDescription().getContent().addTextChangedListener(changeListener);
    model.getTemplateEditModel().addStatsChangeListener(changeListener);
    model.getTemplateEditModel().addCompositionChangeListener(changeListener);
    model.getTemplateEditModel().addCostChangeListener(changeListener);
    model.getTemplateEditModel().addMagicalMaterialChangeListener(changeListener);
    updateEnabled(saveTool);
  }

  private void updateEnabled(Tool tool) {
    if (!model.getTemplateEditModel().getDescription().getName().isEmpty()
            && model.getTemplateEditModel().isDirty()) {
      tool.enable();
    } else {
      tool.disable();
    }
  }


  private class UpdateOnChange implements ObjectValueListener<String>, ChangeListener {
    private Tool saveTool;

    public UpdateOnChange(Tool saveTool) {
      this.saveTool = saveTool;
    }

    @Override
    public void valueChanged(String newValue) {
      updateEnabled(saveTool);
    }

    @Override
    public void changeOccurred() {
      updateEnabled(saveTool);
    }
  }

  private class SaveChangedEquipment implements Command {
    private VetorFactory factory;

    public SaveChangedEquipment(VetorFactory factory) {
      this.factory = factory;
    }

    @Override
    public void execute() {
      IEquipmentTemplateEditModel editModel = model.getTemplateEditModel();
      IEquipmentTemplate saveTemplate = editModel.createTemplate();
      String editTemplateId = editModel.getEditTemplateId();
      if (!saveTemplate.getName().equals(editTemplateId)) {
        IEquipmentTemplate existingTemplate = model.getDatabase().loadTemplate(saveTemplate.getName());
        if (existingTemplate != null) {
          String message = resources.getString("Equipment.Creation.OverwriteMessage.Text");
          String title = resources.getString("AnathemaCore.DialogTitle.ConfirmationDialog");
          Vetor vetor = factory.createVetor(title, message);
          vetor.requestPermissionFor(() -> model.getDatabase().deleteTemplate(existingTemplate.getName()));
        }
      }
      if (editTemplateId != null) {
        model.getDatabase().updateTemplate(editTemplateId, saveTemplate);
      } else {
        model.getDatabase().saveTemplate(saveTemplate);
      }
      editModel.setEditTemplate(saveTemplate.getName());
    }
  }
}