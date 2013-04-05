package net.sf.anathema.lib.workflow.textualdescription.model;

import net.sf.anathema.lib.workflow.textualdescription.ITextualDescription;

public abstract class AbstractTextualDescription implements ITextualDescription {

  private boolean dirty = true;

  @Override
  public boolean isDirty() {
    return dirty;
  }

  @Override
  public void setDirty(boolean isDirty) {
    this.dirty = isDirty;
    fireChangedEvent();
  }

  protected abstract void fireChangedEvent();
}