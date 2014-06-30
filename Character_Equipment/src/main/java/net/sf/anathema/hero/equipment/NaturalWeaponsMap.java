package net.sf.anathema.hero.equipment;

import net.sf.anathema.hero.framework.type.CharacterType;
import net.sf.anathema.equipment.core.IEquipmentTemplate;

public interface NaturalWeaponsMap {
  String ID = "Equipment";

  IEquipmentTemplate getNaturalWeaponTemplate(CharacterType characterType);
}