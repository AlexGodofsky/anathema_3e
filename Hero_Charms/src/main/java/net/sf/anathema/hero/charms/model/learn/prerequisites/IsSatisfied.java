package net.sf.anathema.hero.charms.model.learn.prerequisites;

import net.sf.anathema.magic.attribute.MagicAttribute;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.magic.charm.CharmLearnArbitrator;
import net.sf.anathema.hero.magic.charm.prerequisite.CharmPrerequisite;
import net.sf.anathema.hero.magic.charm.prerequisite.PrerequisiteProcessor;

public class IsSatisfied implements PrerequisiteProcessor {

  public static boolean isSatisfied(CharmPrerequisite prerequisite, CharmLearnArbitrator arbitrator) {
    IsSatisfied satisfied = new IsSatisfied(arbitrator);
    prerequisite.process(satisfied);
    return satisfied.satisfied;
  }

  private CharmLearnArbitrator arbitrator;
  public boolean satisfied = true;

  public IsSatisfied(CharmLearnArbitrator learnArbitrator) {
    this.arbitrator = learnArbitrator;
  }

  @Override
  public void requiresMagicAttributes(MagicAttribute attribute, int count) {
    this.satisfied = arbitrator.hasLearnedThresholdCharmsWithKeyword(attribute, count);
  }

  @Override
  public void requiresCharm(Charm prerequisite) {
    this.satisfied = arbitrator.isLearned(prerequisite);
  }

  @Override
  public void requiresCharmFromSelection(Charm[] prerequisites, int threshold) {
    int known = 0;
    for (Charm charm : prerequisites) {
      if (arbitrator.isLearned(charm)) {
        known++;
      }
      if (known >= threshold) {
        this.satisfied = true;
      }
    }
    this.satisfied =false;
  }
}
