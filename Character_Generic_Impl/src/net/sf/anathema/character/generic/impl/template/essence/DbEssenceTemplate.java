package net.sf.anathema.character.generic.impl.template.essence;

import net.sf.anathema.character.generic.template.essence.FactorizedTrait;
import net.sf.anathema.character.generic.traits.IGenericTrait;

import java.util.List;

public class DbEssenceTemplate extends AbstractEssenceTemplate {

  @Override
  public FactorizedTrait[] getPersonalTraits(IGenericTrait willpower, IGenericTrait[] virtues, IGenericTrait essence) {
    return new FactorizedTrait[] { new FactorizedTrait(essence, 1), new FactorizedTrait(willpower, 1) };
  }

  @Override
  public FactorizedTrait[] getPeripheralTraits(IGenericTrait willpower, IGenericTrait[] virtues, IGenericTrait essence) {
    List<IGenericTrait> sortedVirtues = sortVirtuesDescending(virtues);
    return new FactorizedTrait[] {
        new FactorizedTrait(essence, 4),
        new FactorizedTrait(willpower, 1),
        new FactorizedTrait(sortedVirtues.get(0), 1),
        new FactorizedTrait(sortedVirtues.get(1), 1) };
  }

  @Override
  public boolean isEssenceUser() {
    return true;
  }
}