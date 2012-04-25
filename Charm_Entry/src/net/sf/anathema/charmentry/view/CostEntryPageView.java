package net.sf.anathema.charmentry.view;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.sf.anathema.charmentry.presenter.view.ICostEntryPageView;
import net.sf.anathema.charmentry.presenter.view.ICostEntryView;

public class CostEntryPageView implements ICostEntryPageView {

  private final JPanel content = new JPanel(new GridDialogLayout(5, false));

  @Override
  public JComponent getContent() {
    return content;
  }

  @Override
  public void requestFocus() {
    // Nothing to do
  }

  @Override
  public void dispose() {
    // Nothing to do
  }

  @Override
  public ICostEntryView addCostEntryView(String typeLabel, String costLabel, String textLabel) {
    content.add(new JLabel(typeLabel));
    CostEntryView view = new CostEntryView(costLabel, textLabel);
    view.addTo(content);
    return view;
  }
}