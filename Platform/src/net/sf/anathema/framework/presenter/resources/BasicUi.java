package net.sf.anathema.framework.presenter.resources;

import net.sf.anathema.lib.resources.AbstractUI;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.Icon;

public class BasicUi extends AbstractUI {

  public BasicUi(IResources resources) {
    super(resources);
  }

  public Icon getRemoveIcon() {
    return getIcon("ButtonMinus16.png"); //$NON-NLS-1$
  }

  public Icon getAddIcon() {
    return getIcon("ButtonPlus16.png"); //$NON-NLS-1$
  }

  public Icon getClearIcon() {
    return getIcon("ButtonCross16.png"); //$NON-NLS-1$
  }

  public Icon getLeftArrowIcon() {
    return getIcon("ButtonArrowLeft16.png"); //$NON-NLS-1$
  }

  public Icon getRightArrowIcon() {
    return getIcon("ButtonArrowRight16.png"); //$NON-NLS-1$
  }

  public Icon getUpArrowIcon() {
    return getIcon("ButtonArrowUp16.png"); //$NON-NLS-1$
  }

  public Icon getDownArrowIcon() {
    return getIcon("ButtonArrowDown16.png"); //$NON-NLS-1$
  }

  public Icon getEditIcon() {
    return getIcon("ButtonEdit16.png"); //$NON-NLS-1$
  }
}