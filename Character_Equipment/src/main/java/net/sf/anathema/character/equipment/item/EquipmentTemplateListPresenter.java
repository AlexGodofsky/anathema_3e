package net.sf.anathema.character.equipment.item;

import net.sf.anathema.character.equipment.item.model.IEquipmentDatabaseManagement;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateEditModel;
import net.sf.anathema.character.equipment.item.view.EquipmentNavigation;
import net.sf.anathema.library.event.ObjectValueListener;
import net.sf.anathema.library.resources.Resources;

import java.util.Arrays;

public class EquipmentTemplateListPresenter {

  private final class EquipmentTemplateLoadListener implements ObjectValueListener<String> {
    @Override
    public void valueChanged(String newValue) {
      if (newValue == null) {
        return;
      }
      IEquipmentTemplateEditModel editModel = model.getTemplateEditModel();
      editModel.setEditTemplate(newValue);
      EquipmentTemplateListPresenter.this.editModel.clearStatsSelection();
    }
  }

  private final Resources resources;
  private final EquipmentNavigation view;
  private final StatsEditModel editModel;
  private final IEquipmentDatabaseManagement model;

  public EquipmentTemplateListPresenter(
          Resources resources,
          IEquipmentDatabaseManagement model,
          EquipmentNavigation view, StatsEditModel editModel) {
    this.resources = resources;
    this.model = model;
    this.view = view;
    this.editModel = editModel;
  }

  public void initPresentation() {
    model.getDatabase().addAvailableTemplateChangeListener(this::updateAvailableTemplates);
    updateAvailableTemplates();
    view.getTemplateListView().addSelectionVetor(new DiscardChangesVetor(model, view, resources));
    view.getTemplateListView().addObjectSelectionChangedListener(new EquipmentTemplateLoadListener());
  }

  private void updateAvailableTemplates() {
    String[] templates = model.getDatabase().getAllAvailableTemplateIds();
    Arrays.sort(templates, new EquipmentTemplateNameComparator());
    view.getTemplateListView().setObjects(templates);
  }
}
