package net.sf.anathema.character.equipment;

import net.sf.anathema.character.equipment.template.IEquipmentTemplate;
import net.sf.anathema.character.generic.type.ICharacterType;

public interface IEquipmentAdditionalModelTemplate {
  String ID = "Equipment";

  IEquipmentTemplate getNaturalWeaponTemplate(ICharacterType characterType);
}