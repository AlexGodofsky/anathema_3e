package net.sf.anathema.character.equipment.character.model.stats.modification.material;

import net.sf.anathema.character.equipment.character.model.stats.modification.BaseMaterial;
import net.sf.anathema.character.equipment.character.model.stats.modification.StatModifier;
import net.sf.anathema.hero.health.model.HealthType;

public class MaterialSoakModifier implements StatModifier {
  private final BaseMaterial material;
  private final HealthType healthType;

  public MaterialSoakModifier(BaseMaterial material, HealthType healthType) {
    this.material = material;
    this.healthType = healthType;
  }

  @Override
  public int calculate() {
    if (material.isOrichalcumBased() || material.isSoulsteelBased()) {
      return 2;
    }
    if (material.isAdamantBased() && healthType != HealthType.Bashing) {
      return 3;
    }
    return 0;
  }
}