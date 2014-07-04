package net.sf.anathema.lib.workflow.textualdescription;

import net.sf.anathema.library.event.ObjectValueListener;

public interface ITextualDescription {

  boolean isDirty();

  boolean isEmpty();

  void setText(String text);

  void addTextChangedListener(ObjectValueListener<String> listener);

  @SuppressWarnings("UnusedDeclaration")
  void removeTextChangeListener(ObjectValueListener<String> listener);

  String getText();

  void clear();
}