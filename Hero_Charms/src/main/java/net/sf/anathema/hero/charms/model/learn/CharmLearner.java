package net.sf.anathema.hero.charms.model.learn;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.special.CharmSpecialsModel;
import net.sf.anathema.hero.charms.model.special.subeffects.SubEffectCharmSpecials;
import net.sf.anathema.hero.charms.model.special.upgradable.IUpgradableCharmConfiguration;
import net.sf.anathema.magic.data.Magic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CharmLearner implements MagicLearner {
  private CharmsModel charms;

  public CharmLearner(CharmsModel charms) {
    this.charms = charms;
  }

  @Override
  public boolean handlesMagic(Magic magic) {
    return magic instanceof Charm;

  }

  @Override
  public int getAdditionalBonusPoints(Magic magic) {
    CharmSpecialsModel specialCharmConfiguration = charms.getCharmSpecialsModel((Charm) magic);
    if (specialCharmConfiguration instanceof IUpgradableCharmConfiguration) {
      return ((IUpgradableCharmConfiguration) specialCharmConfiguration).getUpgradeBPCost();
    }
    if (!(specialCharmConfiguration instanceof SubEffectCharmSpecials)) {
      return 0;
    }
    SubEffectCharmSpecials configuration = (SubEffectCharmSpecials) specialCharmConfiguration;
    int count = Math.max(0, (configuration.getCreationLearnedSubEffectCount() - 1));
    return (int) Math.ceil(count * configuration.getPointCostPerEffect());
  }

  @Override
  public int getCreationLearnCount(Magic magic) {
    Charm charm = (Charm) magic;
    int learnCount = handleSpecialCharm(charm);
    if (charms.isAlienCharm(charm)) {
      learnCount *= 2;
    }
    return learnCount;
  }

  private int handleSpecialCharm(Charm charm) {
    CharmSpecialsModel specialCharmConfiguration = charms.getCharmSpecialsModel(charm);
    if (specialCharmConfiguration != null) {
      if (specialCharmConfiguration instanceof IUpgradableCharmConfiguration) {
        return 1;
      }
      return specialCharmConfiguration.getCreationLearnCount();
    }
    return 1;
  }

  @Override
  public Collection<? extends Magic> getLearnedMagic(boolean experienced) {
    List<Charm> allLearnedCharms = new ArrayList<>();
    allLearnedCharms.addAll(charms.getLearningModel().getCharmsLearnedOnCreation());
    if (experienced) {
      allLearnedCharms.addAll(charms.getLearningModel().getCharmsLearnedWithExperience());
    }
    return allLearnedCharms;
  }
}
