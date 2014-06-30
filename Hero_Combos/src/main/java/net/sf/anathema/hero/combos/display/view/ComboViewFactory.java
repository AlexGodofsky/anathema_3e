package net.sf.anathema.hero.combos.display.view;

import net.sf.anathema.hero.framework.display.SubViewFactory;
import net.sf.anathema.framework.util.Produces;
import net.sf.anathema.hero.combos.display.presenter.ComboConfigurationView;
import net.sf.anathema.platform.fx.Stylesheet;

@Produces(ComboConfigurationView.class)
public class ComboViewFactory implements SubViewFactory {
  @SuppressWarnings("unchecked")
  @Override
  public <T> T create() {
    FxComboConfigurationView fxView = new FxComboConfigurationView();
    new Stylesheet("skin/combos/combos.css").applyToParent(fxView.getNode());
    new Stylesheet("skin/platform/tooltip.css").applyToParent(fxView.getNode());
    return (T) fxView;
  }
}