package net.sf.anathema.character.equipment.item.view;

import javax.swing.JComponent;

import net.sf.anathema.character.equipment.template.IEquipmentTemplate;
import net.sf.anathema.lib.gui.IView;
import net.sf.anathema.lib.gui.selection.IListObjectSelectionView;

public interface IEquipmentDatabaseView extends IView {

  public void fillDescriptionPanel(JComponent content);

  public IListObjectSelectionView<IEquipmentTemplate> getTemplateView();

  public void setTemplateListHeader(String headerText);

  public void setEditTemplateHeader(String headerText);
}