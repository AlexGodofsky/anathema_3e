package net.sf.anathema.hero.charms.display.presenter;

import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.charms.model.ICharmGroup;

public interface CharmGroupArbitrator {

  Charm[] getCharms(ICharmGroup charmGroup);
}