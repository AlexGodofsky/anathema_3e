package net.sf.anathema.character.equipment.impl.character.model.stats;

import net.sf.anathema.character.generic.equipment.weapon.IShieldStats;

public class ShieldStats extends AbstractCombatStats implements IShieldStats {

  private int rangedCombatDv;
  private int closeCombatDv;
  private Integer fatigue;
  private Integer mobilityPenalty;

  @Override
  public int getCloseCombatBonus() {
    return closeCombatDv;
  }

  @Override
  public int getRangedCombatBonus() {
    return rangedCombatDv;
  }

  public void setCloseCombatDv(int closeCombatDv) {
    this.closeCombatDv = closeCombatDv;
  }

  public void setRangedCombatDv(int rangedCombatDv) {
    this.rangedCombatDv = rangedCombatDv;
  }

  @Override
  public Integer getFatigue() {
    return fatigue;
  }

  @Override
  public Integer getMobilityPenalty() {
    return mobilityPenalty;
  }

  public void setFatigue(Integer fatigue) {
    this.fatigue = fatigue;
  }

  public void setMobilityPenalty(Integer mobilityPenalty) {
    this.mobilityPenalty = mobilityPenalty;
  }

  @Override
  public String getId() {
    return getName().getId();
  }
}