package net.sf.anathema.cascades.module;

import net.sf.anathema.lib.resources.AbstractUI;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.Icon;

public class CascadesUI extends AbstractUI {

  public CascadesUI(IResources resources) {
    super(resources);
  }

  public Icon getCascadesTabIcon() {
    return getIcon("TabCharms16.png"); //$NON-NLS-1$
  }

  public Icon getCascadesToolBarIcon() {
    return getIcon("toolbar/TaskBarCharms24.png"); //$NON-NLS-1$
  }
}