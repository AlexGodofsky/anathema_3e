package net.sf.anathema.lib.workflow.container.view;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.anathema.lib.control.change.ChangeControl;
import net.sf.anathema.lib.control.change.IChangeListener;
import net.sf.anathema.lib.gui.IView;
import net.sf.anathema.lib.gui.list.SmartJList;
import net.sf.anathema.lib.workflow.container.ISelectionContainerView;

public class SelectionContainerListView<V> implements ISelectionContainerView<V>, IView {

  private final SmartJList<V> smartList;
  private final ChangeControl changeControl = new ChangeControl();

  public SelectionContainerListView(Class<V> contentClass) {
    smartList = new SmartJList<V>(contentClass);
    smartList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
          return;
        }
        changeControl.fireChangedEvent();
      }
    });
  }

  @Override
  public void populate(V[] contentValues) {
    smartList.setObjects(contentValues);
  }

  @Override
  public void setSelectedValues(V[] selectedValues) {
    smartList.setSelectedObjects(selectedValues);
  }

  @Override
  public JList getComponent() {
    return smartList;
  }

  @Override
  public void addSelectionChangeListener(IChangeListener listener) {
    changeControl.addChangeListener(listener);
  }

  @Override
  public V[] getSelectedValues() {
    return smartList.getSelectedValues();
  }

  public void setRenderer(ListCellRenderer renderer) {
    smartList.setCellRenderer(renderer);
  }
}