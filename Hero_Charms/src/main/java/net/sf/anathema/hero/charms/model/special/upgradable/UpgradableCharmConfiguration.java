package net.sf.anathema.hero.charms.model.special.upgradable;

import net.sf.anathema.character.main.magic.charm.Charm;
import net.sf.anathema.character.main.magic.charm.CharmSpecialist;
import net.sf.anathema.character.main.magic.charm.special.IUpgradableCharm;
import net.sf.anathema.character.main.magic.charm.special.IUpgradableCharmConfiguration;
import net.sf.anathema.character.main.magic.charm.special.MultipleEffectCharmConfiguration;
import net.sf.anathema.character.main.magic.charm.special.UpgradableSubEffects;
import net.sf.anathema.character.main.magic.charmtree.ICharmLearnableArbitrator;

public class UpgradableCharmConfiguration extends MultipleEffectCharmConfiguration implements IUpgradableCharmConfiguration {

  public UpgradableCharmConfiguration(CharmSpecialist specialist, Charm charm, IUpgradableCharm upgradableCharm,
                                      ICharmLearnableArbitrator arbitrator) {
    super(specialist, charm, upgradableCharm, arbitrator);
  }

  @Override
  protected UpgradableSubEffects getSubeffects() {
    return (UpgradableSubEffects) super.getSubeffects();
  }

  @Override
  public void forget() {
    //nothing to do
  }

  @Override
  public void learn(boolean experienced) {
    //nothing to do
  }

  @Override
  public int getUpgradeBPCost() {
    return getSubeffects().getUpgradeBPCost();
  }

  @Override
  public int getUpgradeXPCost() {
    return getSubeffects().getUpgradeXPCost();
  }
}