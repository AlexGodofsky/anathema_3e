package net.sf.anathema.hero.display.fx.traitview;

import net.miginfocom.layout.CC;
import net.sf.anathema.hero.display.ExtensibleTraitView;
import net.sf.anathema.library.interaction.model.ToggleTool;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.view.IntValueView;
import net.sf.anathema.platform.fx.FxComponent;
import net.sf.anathema.platform.tool.FxButtonTool;
import net.sf.anathema.platform.tool.FxToggleTool;
import org.tbee.javafx.scene.layout.MigPane;

import static net.sf.anathema.library.fx.layout.LayoutUtils.fillWithoutInsets;

public class FxExtensibleTraitView implements ExtensibleTraitView {
  private final MigPane front = new MigPane(fillWithoutInsets());
  private final MigPane center = new MigPane(fillWithoutInsets().wrapAfter(2));
  private final MigPane rear = new MigPane(fillWithoutInsets());
  private FxTraitView view;
  private TraitViewPanel parent;

  public FxExtensibleTraitView(FxTraitView view) {
    this.view = view;
    view.addTo(center);
  }

  @Override
  public IntValueView getIntValueView() {
    return view;
  }

  @Override
  public ToggleTool addToggleInFront() {
    FxToggleTool toggleTool = FxToggleTool.create();
    toggleTool.setStyleClass("castebutton");
    addToPanel(front, toggleTool);
    return toggleTool;
  }

  @Override
  public ToggleTool addToggleBehind() {
    FxToggleTool toggleTool = FxToggleTool.create();
    addToPanel(rear, toggleTool);
    return toggleTool;
  }

  @Override
  public Tool addToolBehind() {
    FxButtonTool buttonTool = FxButtonTool.ForToolbar();
    addToPanel(rear, buttonTool);
    return buttonTool;
  }

  @Override
  public void remove() {
    removePart(front);
    removePart(center);
    removePart(rear);
  }

  @SuppressWarnings("UnusedParameters")
  private void removePart(MigPane panel) {
    parent.remove(panel);
  }

  public void addTo(TraitViewPanel panel) {
    this.parent = panel;
    panel.add(front, new CC().alignY("center"));
    panel.add(center, new CC().growX().pushX().alignY("center"));
    panel.add(rear, new CC().alignY("center"));
  }

  private void addToPanel(MigPane pane, FxComponent toggleTool) {
    pane.add(toggleTool.getNode(), new CC().alignY("center"));
  }
}