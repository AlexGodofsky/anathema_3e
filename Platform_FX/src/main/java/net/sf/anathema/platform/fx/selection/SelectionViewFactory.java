package net.sf.anathema.platform.fx.selection;

import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.platform.fx.FxObjectSelectionView;

public interface SelectionViewFactory {
  <T> FxObjectSelectionView<T> create(String label, AgnosticUIConfiguration<T> ui);
}