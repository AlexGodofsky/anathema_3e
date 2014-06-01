package net.sf.anathema.hero.equipment.display.presenter;

import net.sf.anathema.character.equipment.character.IEquipmentStringBuilder;
import net.sf.anathema.character.equipment.character.model.IEquipmentItem;
import net.sf.anathema.character.equipment.creation.presenter.stats.properties.EquipmentUI;
import net.sf.anathema.character.equipment.item.EquipmentTemplateNameComparator;
import net.sf.anathema.equipment.core.MagicalMaterial;
import net.sf.anathema.equipment.core.MaterialComposition;
import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.framework.presenter.resources.BasicUi;
import net.sf.anathema.hero.equipment.EquipmentModel;
import net.sf.anathema.hero.equipment.model.EquipmentPersonalizationModel;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.control.ICollectionListener;
import net.sf.anathema.lib.gui.AgnosticUIConfiguration;
import net.sf.anathema.lib.gui.selection.VetoableObjectSelectionView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static net.sf.anathema.equipment.core.MaterialComposition.Fixed;
import static net.sf.anathema.equipment.core.MaterialComposition.None;
import static net.sf.anathema.equipment.core.MaterialComposition.Variable;
import static net.sf.anathema.lib.lang.StringUtilities.isNullOrTrimmedEmpty;

public class EquipmentPresenter {

  private final Resources resources;
  private final EquipmentModel model;
  private final EquipmentView view;
  private final Map<IEquipmentItem, EquipmentObjectView> viewsByItem = new HashMap<>();

  public EquipmentPresenter(Resources resources, final EquipmentModel model, EquipmentView view) {
    this.resources = resources;
    this.model = model;
    this.view = view;

    model.getHeroEvaluator().addCharacterSpecialtyListChangeListener(() -> {
      for (IEquipmentItem item : model.getNaturalWeapons()) {
        initEquipmentObjectPresentation(item);
      }
      for (IEquipmentItem item : model.getEquipmentItems()) {
        initEquipmentObjectPresentation(item);
      }
    });
  }

  public void initPresentation() {
    for (IEquipmentItem item : model.getNaturalWeapons()) {
      initEquipmentObjectPresentation(item);
    }
    for (IEquipmentItem item : model.getEquipmentItems()) {
      initEquipmentObjectPresentation(item);
    }
    VetoableObjectSelectionView<String> equipmentTemplatePickList = view.getEquipmentTemplatePickList();
    model.addEquipmentObjectListener(new UpdateOwnedItems());
    equipmentTemplatePickList.setCellRenderer(new EquipmentItemUIConfiguration(model, resources));
    setObjects(equipmentTemplatePickList);
    MagicalMaterialView magicalMaterialView = initMaterialView(equipmentTemplatePickList);
    addAddButton(equipmentTemplatePickList, magicalMaterialView);
    addRefreshTool(equipmentTemplatePickList);
  }

  private void addAddButton(VetoableObjectSelectionView<String> equipmentTemplatePickList,
                            MagicalMaterialView magicalMaterialView) {
    Tool addTool = view.addToolButton();
    createTemplateAddAction(equipmentTemplatePickList, magicalMaterialView, addTool);
  }

  private void addRefreshTool(VetoableObjectSelectionView<String> equipmentTemplatePickList) {
    Tool refreshTool = view.addToolButton();
    createRefreshAction(equipmentTemplatePickList, refreshTool);
  }

  private void updateMagicalMaterialSelector(MagicalMaterialView magicalMaterialView, String templateId) {
    MaterialComposition composition = templateId == null ? None : model.getMaterialComposition(templateId);
    MagicalMaterial magicMaterial = null;
    if (composition == Variable) {
      magicMaterial = model.getDefaultMaterial();
    } else if (composition == Fixed) {
      magicMaterial = model.getMagicalMaterial(templateId);
    }
    magicalMaterialView.setSelectedMaterial(magicMaterial, composition == Variable);
  }

  private MagicalMaterialView initMaterialView(VetoableObjectSelectionView<String> equipmentTemplatePickList) {
    String label = resources.getString("MagicMaterial.Label") + ":";
    AgnosticUIConfiguration<MagicalMaterial> renderer = new MagicMaterialUIConfiguration(resources);
    MagicalMaterialView magicMaterialView = view.addMagicMaterialView(label, renderer);
    magicMaterialView.setMaterials(MagicalMaterial.values());
    equipmentTemplatePickList.addObjectSelectionChangedListener(templateId -> updateMagicalMaterialSelector(magicMaterialView, templateId));
    return magicMaterialView;
  }

  private void createRefreshAction(VetoableObjectSelectionView<String> equipmentTemplatePickList, Tool refreshTool) {
    refreshTool.setTooltip(resources.getString("AdditionalTemplateView.RefreshDatabase.Action.Tooltip"));
    refreshTool.setIcon(new EquipmentUI().getRefreshIconPath());
    refreshTool.setCommand(() -> {
      setObjects(equipmentTemplatePickList);
      model.refreshItems();
    });
  }

  private void setObjects(VetoableObjectSelectionView<String> equipmentTemplatePickList) {
    String[] templates = model.getAvailableTemplateIds();
    Arrays.sort(templates, new EquipmentTemplateNameComparator());
    equipmentTemplatePickList.setObjects(templates);
  }

  private void createTemplateAddAction(VetoableObjectSelectionView<String> equipmentTemplatePickList,
                                       MagicalMaterialView materialView, Tool selectTool) {
    selectTool.setIcon(new BasicUi().getRightArrowIconPath());
    selectTool.setTooltip(resources.getString("AdditionalTemplateView.AddTemplate.Action.Tooltip"));
    selectTool.setCommand(() -> model.addEquipmentObjectFor(equipmentTemplatePickList.getSelectedObject(),
            materialView.getSelectedMaterial()));
    equipmentTemplatePickList.addObjectSelectionChangedListener(newValue -> setEnabled(newValue, selectTool));
    setEnabled(equipmentTemplatePickList.getSelectedObject(), selectTool);
  }

  private void setEnabled(String newValue, Tool selectTool) {
    if (isNullOrTrimmedEmpty(newValue)) {
      selectTool.disable();
    } else {
      selectTool.enable();
    }
  }

  private void removeItemView(IEquipmentItem item) {
    EquipmentObjectView objectView = viewsByItem.remove(item);
    view.removeEquipmentObjectView(objectView);
  }

  private void initEquipmentObjectPresentation(IEquipmentItem selectedObject) {
    EquipmentObjectView objectView = viewsByItem.get(selectedObject);
    objectView = objectView == null ? view.addEquipmentObjectView() : objectView;
    IEquipmentStringBuilder resourceBuilder = new EquipmentStringBuilder(resources);
    viewsByItem.put(selectedObject, objectView);
    EquipmentObjectPresenter objectPresenter = new EquipmentObjectPresenter(selectedObject, objectView, resourceBuilder,
            model.getHeroEvaluator(), model.getOptionProvider(), resources);
    objectPresenter.initPresentation();
    enablePersonalization(selectedObject, objectPresenter);
  }

  private void enablePersonalization(IEquipmentItem selectedObject, EquipmentObjectPresenter objectPresenter) {
    if (model.canBeRemoved(selectedObject)) {
      createPersonalizeTool(selectedObject, objectPresenter);
      createRemoveItemTool(selectedObject, objectPresenter);
    }
  }

  private void createPersonalizeTool(IEquipmentItem selectedObject, EquipmentObjectPresenter objectPresenter) {
    Tool personalize = objectPresenter.addContextTool();
    personalize.setIcon(new BasicUi().getEditIconPath());
    personalize.setText(resources.getString("AdditionalTemplateView.Personalize.Action.Name"));
    personalize.setCommand(() -> personalizeItem(selectedObject));
  }

  private void createRemoveItemTool(IEquipmentItem selectedObject, EquipmentObjectPresenter objectPresenter) {
    Tool remove = objectPresenter.addContextTool();
    remove.setIcon(new BasicUi().getRemoveIconPath());
    remove.setText(resources.getString("AdditionalTemplateView.RemoveTemplate.Action.Name"));
    remove.setCommand(() -> model.removeItem(selectedObject));
  }

  private void personalizeItem(IEquipmentItem selectedObject) {
    PersonalizationEditView personalizationView = createView();
    EquipmentPersonalizationModel personalizationModel = new EquipmentPersonalizationModel(selectedObject);
    personalizationView.setTitle(personalizationModel.getTitle());
    personalizationView.setDescription(personalizationModel.getDescription());
    personalizationView.whenTitleChanges(personalizationModel::setTitle);
    personalizationView.whenDescriptionChanges(personalizationModel::setDescription);
    personalizationView.whenChangeIsConfirmed(() -> {
      selectedObject.setPersonalization(personalizationModel.getTitle(), personalizationModel.getDescription());
      initEquipmentObjectPresentation(selectedObject);
      model.updateItem(selectedObject);
    });
    personalizationView.show();
  }

  private PersonalizationEditView createView() {
    EquipmentPersonalizationProperties properties = new EquipmentPersonalizationProperties(resources);
    return view.startEditingPersonalization(properties);
  }

  private class UpdateOwnedItems implements ICollectionListener<IEquipmentItem> {
    @Override
    public void itemAdded(IEquipmentItem item) {
      initEquipmentObjectPresentation(item);
    }

    @Override
    public void itemRemoved(IEquipmentItem item) {
      removeItemView(item);
    }
  }
}