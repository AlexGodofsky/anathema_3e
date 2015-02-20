package net.sf.anathema.hero.equipment.sheet.content.stats.weapons;

import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.hero.equipment.EquipmentHeroEvaluator;
import net.sf.anathema.hero.equipment.EquipmentOptionsProvider;
import net.sf.anathema.hero.equipment.IEquipmentStatsOption;
import net.sf.anathema.hero.equipment.sheet.content.stats.AbstractValueEquipmentStatsGroup;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.types.AttributeType;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;

public class AccuracyWeaponStatsGroup extends AbstractValueEquipmentStatsGroup<IWeaponStats> {

  private final TraitMap collection;
  private final EquipmentHeroEvaluator provider;
  private EquipmentOptionsProvider optionProvider;

  public AccuracyWeaponStatsGroup(Resources resources, TraitMap collection, EquipmentHeroEvaluator provider,
                                  EquipmentOptionsProvider optionProvider) {
    super(resources, "Accuracy");
    this.collection = collection;
    this.optionProvider = optionProvider;
    this.provider = provider;
  }

  @Override
  public int getColumnCount() {
    return 2;
  }

  @Override
  public void addContent(PdfPTable table, Font font, IWeaponStats weapon) {
    if (weapon == null) {
      table.addCell(createEmptyValueCell(font));
      table.addCell(createFinalValueCell(font));
    } else {
      int weaponValue = weapon.getAccuracy();
      table.addCell(createEquipmentValueCell(font, weaponValue));
      int calculateFinalValue = getFinalValue(weapon, weaponValue);
      table.addCell(createFinalValueCell(font, calculateFinalValue));
    }
  }

  private int getOptionModifiers(IWeaponStats stats) {
    if (provider == null) {
      return 0;
    }
    int mod = 0;
    for (IEquipmentStatsOption option : optionProvider.getEnabledStatOptions(stats)) {
      mod += option.getAccuracyModifier();
    }
    return mod;
  }

  protected int getFinalValue(IWeaponStats weapon, int weaponValue) {
    Trait trait = collection.getTrait(AttributeType.Dexterity);
    return calculateFinalValue(weaponValue + getOptionModifiers(weapon), trait, collection.getTrait(weapon.getTraitType()));
  }
}
