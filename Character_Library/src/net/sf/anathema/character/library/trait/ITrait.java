package net.sf.anathema.character.library.trait;

import net.sf.anathema.character.generic.traits.IGenericTrait;
import net.sf.anathema.character.library.trait.visitor.ITraitVisitor;
import net.sf.anathema.lib.control.IIntValueChangedListener;

public interface ITrait extends IGenericTrait {

  public int getInitialValue();

  public int getMaximalValue();

  public void addCreationPointListener(IIntValueChangedListener listener);

  public void removeCreationPointListener(IIntValueChangedListener listener);

  public void addCurrentValueListener(IIntValueChangedListener listener);

  public void removeCurrentValueListener(IIntValueChangedListener listener);

  public void accept(ITraitVisitor visitor);
}