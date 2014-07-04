package net.sf.anathema.hero.sheet.preferences;

import net.sf.anathema.framework.preferences.elements.PreferenceModel;
import net.sf.anathema.framework.preferences.elements.PreferencePresenter;
import net.sf.anathema.framework.preferences.elements.PreferenceView;
import net.sf.anathema.framework.preferences.elements.RegisteredPreferencePresenter;
import net.sf.anathema.framework.reporting.pdf.PageSize;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.event.ObjectValueListener;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.view.ObjectSelectionView;
import net.sf.anathema.platform.environment.Environment;

@RegisteredPreferencePresenter
public class SheetPreferencePresenter implements PreferencePresenter {
  private SheetPreferenceModel model;
  private SheetPreferenceView view;
  private Resources resources;

  @Override
  public void initialize() {
    final ObjectSelectionView<PageSize> pageSizeView = view.addObjectSelectionView(
            resources.getString("Preferences.Sheet.PageSize"), new PageSizeUi(resources));
    pageSizeView.setObjects(model.getAvailableChoices());
    pageSizeView.addObjectSelectionChangedListener(new ObjectValueListener<PageSize>() {
      @Override
      public void valueChanged(PageSize newValue) {
        model.requestChangeTo(newValue);
      }
    });
    model.onChange(new ChangeListener() {
      @Override
      public void changeOccurred() {
        showCurrentChoiceInView(pageSizeView);
      }
    });
    showCurrentChoiceInView(pageSizeView);
  }

  @Override
  public void useEnvironment(Environment environment) {
    this.resources = environment;
  }

  @Override
  public Class getViewClass() {
    return SheetPreferenceView.class;
  }

  @Override
  public Class getModelClass() {
    return SheetPreferenceModel.class;
  }

  @Override
  public String getTitle() {
    return resources.getString("Preferences.Sheet");
  }

  @Override
  public void useModel(PreferenceModel preferenceModel) {
    this.model = (SheetPreferenceModel) preferenceModel;
  }

  @Override
  public void useView(PreferenceView view) {
    this.view = (SheetPreferenceView) view;
  }

  private void showCurrentChoiceInView(ObjectSelectionView<PageSize> pageSizeView) {
    pageSizeView.setSelectedObject(model.getSelectedPageSize());
  }
}
