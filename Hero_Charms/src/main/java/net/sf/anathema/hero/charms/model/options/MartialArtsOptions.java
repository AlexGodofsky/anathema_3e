package net.sf.anathema.hero.charms.model.options;

import net.sf.anathema.character.main.magic.charmtree.cache.CharmProvider;
import net.sf.anathema.character.main.magic.charm.Charm;
import net.sf.anathema.character.main.magic.charm.CharmIdMap;
import net.sf.anathema.character.main.magic.charm.ICharmGroup;
import net.sf.anathema.character.main.magic.charmtree.ICharmLearnableArbitrator;
import net.sf.anathema.character.main.magic.charmtree.MartialArtsCharmTree;
import net.sf.anathema.hero.charms.model.rules.CharmsRules;
import net.sf.anathema.hero.charmtree.martial.MartialArtsLevel;

import static net.sf.anathema.charms.MartialArtsUtilities.isMartialArts;

public class MartialArtsOptions implements CharmIdMap, ICharmLearnableArbitrator {

  private final MartialArtsCharmTree martialArtsCharmTree;

  public MartialArtsOptions(CharmProvider charmProvider, CharmsRules charmsRules) {
    MartialArtsLevel standardLevel = charmsRules.getMartialArtsRules().getStandardLevel();
    this.martialArtsCharmTree = new MartialArtsCharmTree(charmProvider, standardLevel);
  }

  @Override
  public Charm getCharmById(String charmId) {
    return martialArtsCharmTree.getCharmById(charmId);
  }

  public ICharmGroup[] getAllCharmGroups() {
    return martialArtsCharmTree.getAllCharmGroups();
  }

  @Override
  public boolean isLearnable(Charm charm) {
    return !isMartialArts(charm) || martialArtsCharmTree.isLearnable(charm);
  }
}
