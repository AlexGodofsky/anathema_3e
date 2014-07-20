package net.sf.anathema.character.equipment.character;

import net.sf.anathema.character.equipment.character.model.IEquipmentItem;
import net.sf.anathema.character.equipment.character.model.IEquipmentStatsOption;
import net.sf.anathema.hero.equipment.sheet.content.stats.weapon.IEquipmentStats;

public interface EquipmentOptionsProvider {
  void enableStatOption(IEquipmentItem item, IEquipmentStats stats, IEquipmentStatsOption option);

  void disableStatOption(IEquipmentItem item, IEquipmentStats stats, IEquipmentStatsOption option);

  boolean isStatOptionEnabled(IEquipmentItem item, IEquipmentStats stats, IEquipmentStatsOption option);

  StatsOptions getEnabledStatOptions(IEquipmentItem item, IEquipmentStats stats);

  StatsOptions getEnabledStatOptions(IEquipmentStats stats);

  // returns true if options have been transferred
  boolean transferOptions(IEquipmentItem fromItem, IEquipmentItem toItem);
}
