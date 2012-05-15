package net.sf.anathema.campaign.persistence;

import net.sf.anathema.framework.itemdata.persistence.IBasicItemPersistenceConstants;
import net.sf.anathema.framework.persistence.IAnathemaXmlConstants;

public interface ISeriesPersistenceConstants extends IBasicItemPersistenceConstants {

  String TAG_PLOT = "Plot"; //$NON-NLS-1$
  String TAG_SERIES_ROOT = "Series"; //$NON-NLS-1$
  String TAG_NOTE_ROOT = "Note"; //$NON-NLS-1$

  String ATTRIB_REPOSITORY_ID = IAnathemaXmlConstants.ATTRIB_REPOSITORY_ID;
  String ATTRIB_REPOSITORY_PRINT_NAME = "repositoryPrintName"; //$NON-NLS-1$
  String ATTRIB_ITEM_TYPE = "itemType"; //$NON-NLS-1$
}