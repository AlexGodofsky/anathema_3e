package net.sf.anathema.points.display.overview.presenter;

import net.sf.anathema.character.framework.display.labelledvalue.IValueView;
import net.sf.anathema.hero.points.model.overview.IValueModel;

public class ValueSubPresenter implements IOverviewSubPresenter {

  private final IValueModel<Integer> model;
  private final IValueView<Integer> view;

  public ValueSubPresenter(IValueModel<Integer> model, IValueView<Integer> view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void update() {
    view.setValue(model.getValue());
  }
}