package net.sf.anathema.points.display.overview.view;

import javafx.scene.Node;
import net.miginfocom.layout.CC;
import net.sf.anathema.character.framework.display.labelledvalue.IValueView;
import net.sf.anathema.character.framework.display.labelledvalue.LabelledAllotmentView;
import net.sf.anathema.character.framework.library.overview.OverviewCategory;
import net.sf.anathema.platform.fx.StyledTitledPane;
import org.tbee.javafx.scene.layout.MigPane;

import static net.sf.anathema.lib.gui.layout.LayoutUtils.withoutInsets;

public class FxOverviewCategory implements OverviewCategory {
  private final MigPane panel = new MigPane(withoutInsets().wrapAfter(4));
  private Node overViewCategory;

  public FxOverviewCategory(final MigPane parent, final String label) {
    overViewCategory = StyledTitledPane.Create(label, panel);
    parent.add(overViewCategory, new CC().grow());
  }

  @Override
  public LabelledAllotmentView addAlotmentView(String labelText, int maxValueLength) {
    FxAllotmentOverview view = new FxAllotmentOverview(labelText);
    view.addTo(panel);
    return view;
  }

  @Override
  public IValueView<Integer> addIntegerValueView(String labelText, int maxValueLength) {
    FxIntegerOverview view = new FxIntegerOverview(labelText);
    view.addTo(panel);
    return view;
  }

  @Override
  public IValueView<String> addStringValueView(String labelText) {
    FxStringOverview view = new FxStringOverview(labelText);
    view.addTo(panel);
    return view;
  }

  public Node getNode() {
    return overViewCategory;
  }
}