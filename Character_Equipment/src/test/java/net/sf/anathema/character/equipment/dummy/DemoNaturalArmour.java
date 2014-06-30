package net.sf.anathema.character.equipment.dummy;

import net.sf.anathema.character.equipment.character.model.stats.AbstractCombatStats;
import net.sf.anathema.hero.equipment.sheet.content.stats.weapon.IArmourStats;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.lib.util.Identifier;

public class DemoNaturalArmour extends AbstractCombatStats implements IArmourStats {

  private final int bashingSoak;
  private final int lethalSoak;
  private final Identifier name;

  public DemoNaturalArmour(Identifier identificate, int bashingSoak, int lethalSoak) {
    this.name = identificate;
    this.bashingSoak = bashingSoak;
    this.lethalSoak = lethalSoak;
  }

  @Override
  public Integer getFatigue() {
    return null;
  }

  @Override
  public Integer getHardness(HealthType healthType) {
    return null;
  }

  @Override
  public Integer getMobilityPenalty() {
    return null;
  }

  @Override
  public Integer getSoak(HealthType type) {
    if (type == HealthType.Aggravated) {
      return null;
    }
    if (type == HealthType.Lethal) {
      return lethalSoak;
    }
    return bashingSoak;
  }

  @Override
  public Identifier getName() {
    return name;
  }

  @Override
  public String getId() {
    return getName().getId();
  }
}