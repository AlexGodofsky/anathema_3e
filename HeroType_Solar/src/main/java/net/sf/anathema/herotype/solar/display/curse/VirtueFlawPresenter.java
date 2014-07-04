package net.sf.anathema.herotype.solar.display.curse;

import net.sf.anathema.hero.traits.display.TraitPresenter;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.herotype.solar.model.curse.LimitBreak;
import net.sf.anathema.herotype.solar.model.curse.LimitBreakModel;
import net.sf.anathema.library.presenter.Presenter;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.text.ITextView;
import net.sf.anathema.library.text.TextualPresentation;
import net.sf.anathema.library.view.ConfigurableCharacterView;
import net.sf.anathema.library.view.IntValueView;

public class VirtueFlawPresenter implements Presenter {

  private final Resources resources;
  private final ConfigurableCharacterView view;
  private final LimitBreakModel model;

  public VirtueFlawPresenter(Resources resources, ConfigurableCharacterView view, LimitBreakModel model) {
    this.resources = resources;
    this.view = view;
    this.model = model;
  }

  @Override
  public void initPresentation() {
    initBasicPresentation();
    initAdditionalPresentation();
    initLimitPresentation(model.getLimitBreak());
  }

  protected void initAdditionalPresentation() {
    // Nothing to do
  }

  protected void initBasicPresentation() {
    LimitBreak limitBreak = model.getLimitBreak();
    initNamePresentation(limitBreak);
  }

  protected void initLimitPresentation(LimitBreak limitBreak) {
    Trait trait = limitBreak.getLimitTrait();
    IntValueView traitView =
            view.addDotSelector(getResources().getString(trait.getType().getId()), trait.getMaximalValue());
    new TraitPresenter(trait, traitView).initPresentation();
  }

  protected ITextView initNamePresentation(LimitBreak limitBreak) {
    ITextView titleView = view.addLineView(resources.getString("VirtueFlaw.Name.Name"));
    new TextualPresentation().initView(titleView, limitBreak.getName());
    return titleView;
  }

  protected final Resources getResources() {
    return resources;
  }
}