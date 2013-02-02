package net.sf.anathema.framework.model;

import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.presenter.IItemManagementModelListener;
import net.sf.anathema.framework.presenter.IItemViewFactory;
import net.sf.anathema.framework.presenter.IModelViewMapping;
import net.sf.anathema.framework.repository.IItem;
import net.sf.anathema.framework.view.IItemView;
import net.sf.anathema.framework.view.IItemViewManagement;
import net.sf.anathema.lib.exception.AnathemaException;
import net.sf.anathema.lib.registry.IRegistry;

public class ItemManagmentModelListener implements IItemManagementModelListener {

  private final IModelViewMapping mapping;
  private final IRegistry<IItemType, IItemViewFactory> viewFactoryRegistry;
  private final IItemViewManagement itemViewManagement;
  private final IItemActionFactory actionFactory;

  public ItemManagmentModelListener(IRegistry<IItemType, IItemViewFactory> viewFactoryRegistry, IItemViewManagement itemViewManagement,
                                    IModelViewMapping mappping, IItemActionFactory actionFactory) {
    this.itemViewManagement = itemViewManagement;
    this.mapping = mappping;
    this.viewFactoryRegistry = viewFactoryRegistry;
    this.actionFactory = actionFactory;

  }

  @Override
  public void itemAdded(IItem item) throws AnathemaException {
    IItemViewFactory viewFactory = viewFactoryRegistry.get(item.getItemType());
    IItemView itemView = viewFactory.createView(item);
    mapping.addModelAndView(item, itemView);
    itemViewManagement.addItemView(itemView, actionFactory.createAction(item));
  }

  @Override
  public void itemSelected(IItem item) {
    if (item == null) {
      return;
    }
    itemViewManagement.setSelectedItemView(mapping.getViewByModel(item));
  }

  @Override
  public void itemRemoved(IItem item) {
    IItemView view = mapping.getViewByModel(item);
    mapping.removeModelAndView(item, view);
    itemViewManagement.removeItemView(view);
  }
}