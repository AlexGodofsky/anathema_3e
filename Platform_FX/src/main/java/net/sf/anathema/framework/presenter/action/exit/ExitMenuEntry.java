package net.sf.anathema.framework.presenter.action.exit;

import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.environment.fx.UiEnvironment;
import net.sf.anathema.framework.module.MenuEntry;
import net.sf.anathema.framework.module.RegisteredMenuEntry;
import net.sf.anathema.framework.view.MenuBar;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.platform.environment.Environment;

@RegisteredMenuEntry
@Weight(weight = 20)
public class ExitMenuEntry implements MenuEntry {

  private final Environment environment;

  @SuppressWarnings("UnusedParameters")
  public ExitMenuEntry(Environment environment, UiEnvironment uiEnvironment, IApplicationModel model) {
    this.environment = environment;
  }

  @Override
  public void addTo(MenuBar menu) {
    Command action = new AnathemaExitAction();
    String name = environment.getString("AnathemaCore.Tools.Exit.Name");
    menu.getMainMenu().addMenuItem(action, name);
  }
}
