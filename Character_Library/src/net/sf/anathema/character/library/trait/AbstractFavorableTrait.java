package net.sf.anathema.character.library.trait;

import net.disy.commons.core.util.Ensure;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ITraitContext;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ITraitValueStrategy;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.library.trait.favorable.IFavorableTrait;
import net.sf.anathema.character.library.trait.rules.ITraitRules;
import net.sf.anathema.lib.control.intvalue.IIntValueChangedListener;
import net.sf.anathema.lib.control.intvalue.IntValueControl;

public abstract class AbstractFavorableTrait implements IFavorableTrait {

  private final ITraitRules traitRules;
  private final IntValueControl creationPointControl = new IntValueControl();
  private final IntValueControl currentValueControl = new IntValueControl();
  private final ITraitContext traitContext;

  public AbstractFavorableTrait(ITraitRules traitRules, ITraitContext traitContext) {
    Ensure.ensureArgumentNotNull(traitRules);
    this.traitRules = traitRules;
    this.traitContext = traitContext;
  }

  @Override
  public boolean isCasteOrFavored() {
    return getFavorization().isCasteOrFavored();
  }

  @Override
  public final ITraitType getType() {
    return getTraitRules().getType();
  }

  @Override
  public final int getMaximalValue() {
    return getTraitRules().getAbsoluteMaximumValue();
  }

  public final boolean isLowerable() {
    return getTraitRules().isLowerable();
  }

  @Override
  public int hashCode() {
    return getType().getId().hashCode();
  }

  public int getAbsoluteMinValue() {
    return getTraitRules().getAbsoluteMinimumValue();
  }

  public final int getZeroCalculationValue() {
    return getTraitRules().getZeroCalculationCost();
  }

  @Override
  public int getInitialValue() {
    return getTraitRules().getStartValue();
  }

  @Override
  public final void addCreationPointListener(IIntValueChangedListener listener) {
    getCreationPointControl().addIntValueChangeListener(listener);
  }

  @Override
  public final void removeCreationPointListener(IIntValueChangedListener listener) {
    getCreationPointControl().removeIntValueChangeListener(listener);
  }

  @Override
  public final void addCurrentValueListener(IIntValueChangedListener listener) {
    getCurrentValueControl().addIntValueChangeListener(listener);
  }

  @Override
  public final void removeCurrentValueListener(IIntValueChangedListener listener) {
    getCurrentValueControl().removeIntValueChangeListener(listener);
  }

  protected ITraitRules getTraitRules() {
    return traitRules;
  }

  protected IntValueControl getCreationPointControl() {
    return creationPointControl;
  }

  protected IntValueControl getCurrentValueControl() {
    return currentValueControl;
  }

  protected ITraitValueStrategy getTraitValueStrategy() {
    return traitContext.getTraitValueStrategy();
  }
}