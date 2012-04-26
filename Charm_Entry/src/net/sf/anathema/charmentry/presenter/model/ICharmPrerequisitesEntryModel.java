package net.sf.anathema.charmentry.presenter.model;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.lib.control.IChangeListener;
import net.sf.anathema.lib.exception.PersistenceException;

public interface ICharmPrerequisitesEntryModel {

  public void addModelListener(IChangeListener inputListener);

  public void setPrerequisiteCharms(ICharm[] charms);

  public ICharm[] getAvailableCharms() throws PersistenceException;

  public void setRequiresExcellency(boolean required);

}
