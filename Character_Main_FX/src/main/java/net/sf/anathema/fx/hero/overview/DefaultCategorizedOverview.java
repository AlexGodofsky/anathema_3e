package net.sf.anathema.fx.hero.overview;

import net.sf.anathema.character.framework.library.overview.OverviewCategory;
import net.sf.anathema.hero.points.display.overview.view.CategorizedOverview;
import net.sf.anathema.hero.points.display.overview.view.OverviewDisplay;
import net.sf.anathema.lib.gui.layout.LayoutUtils;
import org.tbee.javafx.scene.layout.MigPane;

public class DefaultCategorizedOverview implements CategorizedOverview {

  private final MigPane panel = new MigPane(LayoutUtils.fillWithoutInsets().wrapAfter(1));

  @Override
  public final OverviewCategory addOverviewCategory(String borderText) {
    return new FxOverviewCategory(panel, borderText);
  }

  @Override
  public void showIn(OverviewDisplay display) {
    if (!(display instanceof FxOverviewDisplay)) {
      return;
    }
    ((FxOverviewDisplay) display).setOverviewPane(panel);
  }
}