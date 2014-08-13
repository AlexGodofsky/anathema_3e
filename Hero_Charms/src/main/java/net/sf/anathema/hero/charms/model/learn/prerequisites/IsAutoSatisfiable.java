package net.sf.anathema.hero.charms.model.learn.prerequisites;

import java.util.List;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.charm.data.prerequisite.CharmPrerequisite;
import net.sf.anathema.charm.data.prerequisite.PrerequisiteProcessor;
import net.sf.anathema.charm.data.prerequisite.RequiredTraitType;
import net.sf.anathema.hero.charms.model.learn.CharmLearnableArbitrator;
import net.sf.anathema.magic.data.attribute.MagicAttribute;

public class IsAutoSatisfiable implements PrerequisiteProcessor {

  public static boolean isAutoSatisfiable(CharmPrerequisite prerequisite, CharmLearnableArbitrator arbitrator) {
    IsAutoSatisfiable satisfied = new IsAutoSatisfiable(arbitrator);
    prerequisite.process(satisfied);
    return satisfied.satisfiable;
  }

  private final CharmLearnableArbitrator arbitrator;
  public boolean satisfiable = true;

  public IsAutoSatisfiable(CharmLearnableArbitrator learnArbitrator) {
    this.arbitrator = learnArbitrator;
  }

  @Override
  public void requiresMagicAttributes(MagicAttribute attribute, int count) {
    this.satisfiable = false;
  }

  @Override
  public void requiresCharm(Charm prerequisite) {
    this.satisfiable = arbitrator.isLearnable(prerequisite);
  }

  @Override
  public void requiresCharmFromSelection(Charm[] prerequisites, int threshold) {
    int knowable = 0;
    for (Charm charm : prerequisites) {
      if (arbitrator.isLearnable(charm)) {
        knowable++;
      }
      if (knowable >= threshold) {
        this.satisfiable = true;
      }
    }
    this.satisfiable = false;
  }

	@Override
	public void requiresCharmsOfTraits(List<RequiredTraitType> traits, int count,
			int minimumEssence) {
		this.satisfiable = false;
	}
}
