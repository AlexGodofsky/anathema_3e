package net.sf.anathema.hero.equipment;

import net.sf.anathema.character.equipment.character.EquipmentHeroEvaluator;
import net.sf.anathema.character.equipment.character.EquipmentOptionsProvider;
import net.sf.anathema.character.equipment.character.model.IEquipmentItemCollection;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateProvider;
import net.sf.anathema.equipment.core.MagicalMaterial;
import net.sf.anathema.equipment.core.MaterialComposition;
import net.sf.anathema.hero.equipment.sheet.content.stats.weapon.IArmourStats;
import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.StatsModifierFactory;
import net.sf.anathema.hero.spiritual.model.pool.IEssencePoolModifier;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

public interface EquipmentModel extends HeroModel, IEquipmentItemCollection, IEquipmentTemplateProvider, IEssencePoolModifier, StatsModifierFactory {

  Identifier ID = new SimpleIdentifier("Equipment");

  MagicalMaterial getDefaultMaterial();

  MaterialComposition getMaterialComposition(String templateId);

  MagicalMaterial getMagicalMaterial(String templateId);

  EquipmentHeroEvaluator getHeroEvaluator();

  void refreshItems();

  EquipmentOptionsProvider getOptionProvider();

  IArmourStats getNaturalArmor();
}