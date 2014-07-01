package net.sf.anathema.hero.charms.compiler;

import net.sf.anathema.charm.data.reference.CategoryReference;
import net.sf.anathema.hero.charms.model.CharmIdMap;
import net.sf.anathema.hero.charms.model.options.CharmOptionCheck;
import net.sf.anathema.hero.charms.model.special.ISpecialCharm;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.lib.util.Identifier;

public interface CharmProvider {

  Charm[] getCharms(CategoryReference category);

  ISpecialCharm[] getSpecialCharms(Identifier type);

  ISpecialCharm[] getSpecialCharms(CharmOptionCheck optionCheck, CharmIdMap map, Identifier preferredType);
}