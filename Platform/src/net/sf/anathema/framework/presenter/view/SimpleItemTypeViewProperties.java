package net.sf.anathema.framework.presenter.view;

import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.module.NullWizardPageFactory;
import net.sf.anathema.lib.workflow.wizard.selection.IWizardFactory;

import javax.swing.Icon;

public class SimpleItemTypeViewProperties extends AbstractItemTypeViewProperties {

  public SimpleItemTypeViewProperties(IItemType type, Icon icon) {
    super(type, icon, new SimpleItemTypeUi(icon));
  }

  @Override
  public IWizardFactory getNewItemWizardFactory() {
    return new NullWizardPageFactory();
  }
}