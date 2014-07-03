package net.sf.anathema.hero.charms.display.tooltip;

import net.sf.anathema.magic.Magic;
import net.sf.anathema.lib.gui.ConfigurableTooltip;

public interface MagicTooltipContributor {

  void buildStringForMagic(ConfigurableTooltip tooltip, Magic magic, Object specialDetails);
}
