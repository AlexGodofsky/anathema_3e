package net.sf.anathema.hero.magic.parser.charms;

import net.sf.anathema.hero.magic.charm.prerequisite.CharmPrerequisite;
import net.sf.anathema.hero.traits.model.ValuedTraitType;

import com.google.common.base.Preconditions;

public class CharmPrerequisiteList {

  private final ValuedTraitType[] traitPrerequisites;
  private final ValuedTraitType essencePrerequisite;
  private final CharmPrerequisite[] charmPrerequisites;

  public CharmPrerequisiteList(ValuedTraitType[] traitPrerequisites, ValuedTraitType essencePrerequisite, CharmPrerequisite[] charmPrerequisites) {
    Preconditions.checkNotNull(traitPrerequisites);
    Preconditions.checkNotNull(essencePrerequisite);
    Preconditions.checkNotNull(charmPrerequisites);
    this.traitPrerequisites = traitPrerequisites;
    this.essencePrerequisite = essencePrerequisite;
    this.charmPrerequisites = charmPrerequisites;
  }

  public ValuedTraitType getEssencePrerequisite() {
    return essencePrerequisite;
  }

  public ValuedTraitType[] getTraitPrerequisites() {
    return traitPrerequisites;
  }

  public CharmPrerequisite[] getCharmPrerequisites() {
    return charmPrerequisites;
  }
}