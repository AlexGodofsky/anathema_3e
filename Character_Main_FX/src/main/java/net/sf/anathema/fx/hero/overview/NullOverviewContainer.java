package net.sf.anathema.fx.hero.overview;

import net.sf.anathema.character.framework.library.overview.OverviewCategory;
import net.sf.anathema.hero.points.display.overview.view.CategorizedOverview;
import net.sf.anathema.hero.points.display.overview.view.OverviewDisplay;

public class NullOverviewContainer implements CategorizedOverview {
  @Override
  public OverviewCategory addOverviewCategory(String borderLabel) {
    return new FxOverviewCategory(null, null);
  }

  @Override
  public void showIn(OverviewDisplay characterPane) {
    //nothing to do
  }
}
