package net.sf.anathema.hero.charms.display.tooltip;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.hero.charms.model.special.ISpecialCharm;
import net.sf.anathema.lib.gui.ConfigurableTooltip;

public interface CharmTooltipBuilder {
  void configureTooltipWithoutSpecials(Charm charm, ConfigurableTooltip tooltip);

  void configureTooltipWithSpecials(Charm charm, ISpecialCharm special, ConfigurableTooltip tooltip);
}