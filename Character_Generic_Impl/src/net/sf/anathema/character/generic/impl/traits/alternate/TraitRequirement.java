package net.sf.anathema.character.generic.impl.traits.alternate;

import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.traits.ITraitType;

public class TraitRequirement implements ITraitRequirement {

  private final int freeMinimum;
  private final int strictMinimum;
  private final ITraitType traitType;

  public TraitRequirement(int freeMinimum, int strictMinimum, ITraitType traitType) {
    this.freeMinimum = freeMinimum;
    this.strictMinimum = strictMinimum;
    this.traitType = traitType;
  }

  @Override
  public int getFreeMinimum() {
    return freeMinimum;
  }

  @Override
  public int getStrictMinimum() {
    return strictMinimum;
  }

  @Override
  public boolean isCurrentlyStrict(IGenericTraitCollection collection) {
    int currentValue = collection.getTrait(traitType).getCurrentValue();
    return currentValue >= getStrictMinimum();
  }

  @Override
  public String toString() {
    return "Restriction " + traitType; //$NON-NLS-1$
  }
}