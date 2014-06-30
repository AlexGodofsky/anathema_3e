package net.sf.anathema.hero.magic.charm.combos;

import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.types.AbilityType;

public interface IComboRestrictions {

  boolean combosAllAbilities();
  
  boolean combosSelectAbility(AbilityType type);

  boolean isRestrictedCharm(Charm charm);

  TraitType[] getRestrictedTraitTypes();
}