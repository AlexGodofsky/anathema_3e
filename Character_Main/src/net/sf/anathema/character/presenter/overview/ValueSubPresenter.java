package net.sf.anathema.character.presenter.overview;

import net.sf.anathema.lib.workflow.labelledvalue.IValueView;

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