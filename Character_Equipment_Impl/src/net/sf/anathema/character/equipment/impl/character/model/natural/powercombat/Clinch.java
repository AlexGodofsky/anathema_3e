package net.sf.anathema.character.equipment.impl.character.model.natural.powercombat;

import net.sf.anathema.character.equipment.impl.character.model.natural.AbstractNaturalWeaponStats;
import net.sf.anathema.character.equipment.impl.creation.model.WeaponTag;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.AttributeType;
import net.sf.anathema.lib.util.IIdentificate;
import net.sf.anathema.lib.util.Identificate;

public class Clinch extends AbstractNaturalWeaponStats {

  public int getAccuracy() {
    return 0;
  }

  public int getDamage() {
    return 0;
  }

  public Integer getDefence() {
    return 0;
  }
  
  public int getMinimumDamage() {
	return 1;
  }

  @Override
  public Integer getRate() {
    return 1;
  }

  public int getSpeed() {
    return -6;
  }

  public IIdentificate[] getTags() {
    return new IIdentificate[] { WeaponTag.Piercing };
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
    return new Identificate("Clinch"); //$NON-NLS-1$
  }
}