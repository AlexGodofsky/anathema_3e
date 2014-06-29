package net.sf.anathema.herotype.solar.display.curse;

import net.sf.anathema.hero.display.configurableview.ConfigurableCharacterView;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.herotype.solar.model.curse.DescriptiveLimitBreak;
import net.sf.anathema.herotype.solar.model.curse.DescriptiveLimitBreakModel;
import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.lib.workflow.textualdescription.ITextView;
import net.sf.anathema.lib.workflow.textualdescription.TextualPresentation;

public class SolarVirtueFlawPresenter extends VirtueFlawPresenter {

  private final ConfigurableCharacterView view;
  private final DescriptiveLimitBreakModel model;

  public SolarVirtueFlawPresenter(Hero hero, Resources resources, ConfigurableCharacterView view, DescriptiveLimitBreakModel model) {
    super(hero, resources, view, model);
    this.view = view;
    this.model = model;
  }

  @Override
  protected void initAdditionalPresentation() {
    DescriptiveLimitBreak virtueFlaw = model.getVirtueFlaw();
    TextualPresentation presentation = new TextualPresentation();
    initConditionPresentation(virtueFlaw, presentation);
    initDescriptionPresentation(virtueFlaw, presentation);
  }

  private void initDescriptionPresentation(DescriptiveLimitBreak virtueFlaw, TextualPresentation textualPresentation) {
    ITextView descriptionView = view.addAreaView(getResources().getString("VirtueFlaw.Description.Name"));
    textualPresentation.initView(descriptionView, virtueFlaw.getDescription());
  }

  private void initConditionPresentation(DescriptiveLimitBreak virtueFlaw, TextualPresentation textualPresentation) {
    ITextView conditionView = view.addAreaView(getResources().getString("VirtueFlaw.LimitBreakCondition.Name"));
    textualPresentation.initView(conditionView, virtueFlaw.getLimitBreak());
  }
}