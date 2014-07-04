package net.sf.anathema.hero.display.fx.configurableview;

import javafx.scene.Node;
import javafx.scene.control.Label;
import net.sf.anathema.hero.display.configurableview.MultiComponentLine;
import net.sf.anathema.hero.framework.IIntegerDescription;
import net.sf.anathema.lib.workflow.textualdescription.ITextView;
import net.sf.anathema.library.view.IntegerView;
import net.sf.anathema.platform.fx.FxTextView;
import org.tbee.javafx.scene.layout.MigPane;

public class FxMultiComponentLine implements MultiComponentLine {
  private final MigPane fieldPanel = new MigPane();

  @Override
  public ITextView addFieldsView(String labelText) {
    FxTextView view = FxTextView.SingleLine(labelText);
    addLabeledComponent("", view.getNode());
    return view;
  }

  @Override
  public IntegerView addIntegerView(String labelText, IIntegerDescription description) {
    IntegerSpinner spinner = new IntegerSpinner();
    spinner.setValue(description.getValue());
    addLabeledComponent(labelText, spinner.getNode());
    return spinner;
  }

  private void addLabeledComponent(String text, final Node component) {
    Label label = new Label(text);
    fieldPanel.add(label);
    fieldPanel.add(component);
  }

  public Node getNode() {
    return fieldPanel;
  }
}
