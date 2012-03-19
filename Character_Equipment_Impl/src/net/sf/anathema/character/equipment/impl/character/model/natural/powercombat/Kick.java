package net.sf.anathema.character.equipment.impl.character.model.natural.powercombat;

import net.sf.anathema.character.equipment.impl.character.model.natural.AbstractNaturalWeaponStats;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.AttributeType;
import net.sf.anathema.lib.util.IIdentificate;
import net.sf.anathema.lib.util.Identificate;

public class Kick extends AbstractNaturalWeaponStats {

  public int getAccuracy() {
    return 1;
  }

  public int getDamage() {
    return 3;
  }
  
  public int getMinimumDamage() {
	return 1;
  }

  public Integer getDefence() {
    return -3;
  }

  @Override
  public Integer getRate() {
    return 3;
  }

  public int getSpeed() {
    return -3;
  }

  public IIdentificate[] getTags() {
    return new IIdentificate[0];
  }

  public ITraitType getTraitType() {
    return AbilityType.Brawl;
  }

  public ITraitType getDamageTraitType() {
    return AttributeType.Strength;
  }

  public boolean inflictsNoDamage() {
    return false;
  }

  public IIdentificate getName() {
    return new Identificate("Kick"); //$NON-NLS-1$
  }
}