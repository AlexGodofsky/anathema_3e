package net.sf.anathema.character.lunar.beastform.model.gift.weapons;

import net.sf.anathema.lib.util.IIdentificate;
import net.sf.anathema.lib.util.Identificate;

public class CoreRulesBeastClawsBite extends AbstractLunarGiftWeapon {

  public int getAccuracy() {
    return 2;
  }

  public int getDamage() {
    return 5;
  }

  public Integer getDefence() {
    return 0;
  }

  public int getSpeed() {
    return 0;
  }
  
  public int getMinimumDamage() {
	return 1;
  }

  public IIdentificate getName() {
    return new Identificate("DeadlyBeastmanTransformation.Weapon.Bite"); //$NON-NLS-1$
  }
}