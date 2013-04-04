package net.sf.anathema.character.equipment.item.model;

import net.sf.anathema.character.equipment.MaterialComposition;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.lib.resources.Resources;

import java.awt.Component;

public interface IEquipmentStatsCreationFactory {

  IEquipmentStats createNewStats(Component parentComponent, Resources resources, String[] definedNames,
                                 MaterialComposition materialComposition);

  IEquipmentStats editStats(Component parentComponent, Resources resources, String[] nameArray,
                            IEquipmentStats selectedStats, MaterialComposition materialComposition);
}
