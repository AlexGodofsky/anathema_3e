package net.sf.anathema.character.library.selection;

import net.sf.anathema.character.library.removableentry.presenter.IRemovableEntryModel;
import net.sf.anathema.hero.change.FlavoredChangeListener;

public interface IStringEntryTraitModel<V> extends IRemovableEntryModel<V> {

  void setCurrentName(String newValue);

  void addChangeListener(FlavoredChangeListener listener);
}