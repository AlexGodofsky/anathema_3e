package net.sf.anathema.lib.gui.table.celleditors;

import javax.swing.JComponent;
import javax.swing.JTextField;

public class StringCellEditor extends AbstractDelegatingCellEditor {

  @Override
  protected final EditorDelegate createDelegate(JComponent editorComponent) {
    final JTextField textField = (JTextField) editorComponent;
    return new EditorDelegate(this) {

      @Override
      public void setValue(Object value) {
        textField.setText(value != null ? value.toString() : "");
        textField.selectAll();
      }

      @Override
      public Object getCellEditorValue() {
        return textField.getText();
      }
    };
  }

  @Override
  protected JComponent createEditorComponent() {
    return new JTextField();
  }
}