package net.sf.anathema.initialization;

import net.sf.anathema.framework.module.AbstractItemTypeConfiguration;
import net.sf.anathema.framework.module.IItemTypeConfiguration;

import java.util.ArrayList;
import java.util.Collection;

public class ItemTypeConfigurationCollection  {

  private final Collection<AbstractItemTypeConfiguration> itemTypeConfigurations = new ArrayList<AbstractItemTypeConfiguration>();

  public ItemTypeConfigurationCollection(Instantiater instantiater)
      throws InitializationException {
    Collection<AbstractItemTypeConfiguration> configurations = instantiater.instantiateOrdered(
            ItemTypeConfiguration.class);
    itemTypeConfigurations.addAll(configurations);
  }

  public Collection<IItemTypeConfiguration> getItemTypes() {
    return new ArrayList<IItemTypeConfiguration>(itemTypeConfigurations);
  }
}