package net.sf.anathema.lib.gui.list.actionview;

import javax.swing.JComponent;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionListener;

import net.sf.anathema.lib.gui.list.SmartJList;

public class ActionAddableListView<T> extends AbstractActionAddableListView<T> implements
    IMultiSelectionActionAddableListView<T> {

  private final SmartJList<T> list;

  public ActionAddableListView(String title, Class<T> contentClass) {
    super(title);
    list = new SmartJList<T>(contentClass);
  }

  @Override
  public void setObjects(T[] items) {
    list.setObjects(items);
  }

  @Override
  public void addListSelectionListener(ListSelectionListener listener) {
    list.addListSelectionListener(listener);
  }

  @Override
  public T[] getSelectedItems() {
    return list.getSelectedValues();
  }

  @Override
  public int[] getSelectedIndices() {
    return list.getSelectedIndices();
  }

  @Override
  protected JComponent getDisplayComponent() {
    return list;
  }

  @Override
  protected boolean isScrollable() {
    return true;
  }

  protected SmartJList<T> getList() {
    return list;
  }

  public void setListCellRenderer(ListCellRenderer renderer) {
    list.setCellRenderer(renderer);
  }
}