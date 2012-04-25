package net.sf.anathema.character.generic.impl.magic;

import net.sf.anathema.character.generic.magic.general.ICost;
import net.sf.anathema.character.generic.magic.general.ICostList;
import net.sf.anathema.character.generic.magic.general.IHealthCost;

public class CostList implements ICostList {

  private final ICost essence;
  private final ICost willpower;
  private final IHealthCost health;
  private final ICost xp;

  public CostList(ICost essence, ICost willpower, IHealthCost health, ICost xp) {
    this.xp = xp;
    this.essence = essence;
    this.willpower = willpower;
    this.health = health;
  }

  @Override
  public ICost getXPCost() {
    return xp != null ? xp : Cost.NULL_COST;
  }

  @Override
  public ICost getEssenceCost() {
    return essence != null ? essence : Cost.NULL_COST;
  }

  @Override
  public IHealthCost getHealthCost() {
    return health != null ? health : HealthCost.NULL_HEALTH_COST;
  }

  @Override
  public ICost getWillpowerCost() {
    return willpower != null ? willpower : Cost.NULL_COST;
  }
}