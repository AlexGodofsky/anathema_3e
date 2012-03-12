package net.sf.anathema.framework;

import net.sf.anathema.framework.extension.IAnathemaExtension;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.item.IItemTypeRegistry;
import net.sf.anathema.framework.messaging.IAnathemaMessageContainer;
import net.sf.anathema.framework.messaging.IAnathemaMessaging;
import net.sf.anathema.framework.persistence.IRepositoryItemPersister;
import net.sf.anathema.framework.presenter.IItemManagementModel;
import net.sf.anathema.framework.presenter.IItemViewFactory;
import net.sf.anathema.framework.reporting.IReportRegistry;
import net.sf.anathema.framework.repository.IRepository;
import net.sf.anathema.lib.registry.IRegistry;

public interface IAnathemaModel {

  public IRepository getRepository();

  public IReportRegistry getReportRegistry();

  public IItemTypeRegistry getItemTypeRegistry();

  public IRegistry<String, IAnathemaExtension> getExtensionPointRegistry();

  public IRegistry<IItemType, IRepositoryItemPersister> getPersisterRegistry();

  public IRegistry<IItemType, IItemViewFactory> getViewFactoryRegistry();

  public IItemManagementModel getItemManagement();

  public IAnathemaMessaging getMessaging();

  public IAnathemaMessageContainer getMessageContainer();
}
