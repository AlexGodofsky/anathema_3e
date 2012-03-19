package net.sf.anathema.character.presenter.magic;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.special.IMultiLearnableCharm;
import net.sf.anathema.character.generic.magic.charms.special.IMultipleEffectCharm;
import net.sf.anathema.character.generic.magic.charms.special.IOxBodyTechniqueCharm;
import net.sf.anathema.character.generic.magic.charms.special.IPainToleranceCharm;
import net.sf.anathema.character.generic.magic.charms.special.IPrerequisiteModifyingCharm;
import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharm;
import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharmVisitor;
import net.sf.anathema.character.generic.magic.charms.special.ISubeffectCharm;
import net.sf.anathema.character.generic.magic.charms.special.ITraitCapModifyingCharm;
import net.sf.anathema.character.generic.magic.charms.special.IUpgradableCharm;
import net.sf.anathema.character.model.charm.ICharmConfiguration;
import net.sf.anathema.character.model.charm.special.IMultiLearnableCharmConfiguration;
import net.sf.anathema.character.model.charm.special.IMultipleEffectCharmConfiguration;
import net.sf.anathema.character.model.charm.special.IOxBodyTechniqueConfiguration;
import net.sf.anathema.charmtree.presenter.view.ISpecialCharmViewContainer;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.platform.svgtree.document.visualizer.ITreePresentationProperties;
import net.sf.anathema.platform.svgtree.presenter.view.ISVGSpecialNodeView;
import net.sf.anathema.platform.svgtree.view.batik.intvalue.SVGCategorizedSpecialNodeView;
import net.sf.anathema.platform.svgtree.view.batik.intvalue.SVGToggleButtonSpecialNodeView;

import java.awt.Color;

public class SpecialCharmViewBuilder implements ISpecialCharmVisitor {
  private final IResources resources;
  private final ISpecialCharmViewContainer factory;
  private CharacterCharmModel charmModel;
  private ITreePresentationProperties presentationProperties;
  private ISVGSpecialNodeView createdView;

  public SpecialCharmViewBuilder(IResources resources,
                                 ISpecialCharmViewContainer factory, CharacterCharmModel charmModel,
                                 ITreePresentationProperties presentationProperties) {
    this.resources = resources;
    this.factory = factory;
    this.charmModel = charmModel;
    this.presentationProperties = presentationProperties;
  }

  @Override
  public void visitMultiLearnableCharm(IMultiLearnableCharm visitedCharm) {
    SVGCategorizedSpecialNodeView specialView = createViewForCharm(visitedCharm);
    IMultiLearnableCharmConfiguration model = getModelFormCharm(visitedCharm);
    new MultiLearnableCharmPresenter(resources, specialView, model).initPresentation();
    addViewDirectly(specialView);
  }

  @Override
  public void visitOxBodyTechnique(IOxBodyTechniqueCharm visitedCharm) {
    SVGCategorizedSpecialNodeView specialView = createViewForCharm(visitedCharm);
    IOxBodyTechniqueConfiguration model = getModelFormCharm(visitedCharm);
    new OxBodyTechniquePresenter(resources, specialView, model).initPresentation();
    if (requiresMoreThanOneLine(visitedCharm, model)) {
      addButtonForCharmConfiguration("CharmTreeView.Ox-Body.HealthLevels", specialView);
    } else {
      addViewDirectly(specialView);
    }
  }

  @Override
  public void visitPrerequisiteModifyingCharm(IPrerequisiteModifyingCharm visited) {
    // Nothing to do
  }

  @Override
  public void visitTraitCapModifyingCharm(ITraitCapModifyingCharm visited) {
    // Nothing to do
  }

  @Override
  public void visitPainToleranceCharm(IPainToleranceCharm visited) {
    // Nothing to do
  }

  @Override
  public void visitSubeffectCharm(ISubeffectCharm visited) {
    createMultipleEffectCharmView(visited, "CharmTreeView.SubeffectCharm.ButtonLabel"); //$NON-NLS-1$
  }

  @Override
  public void visitMultipleEffectCharm(IMultipleEffectCharm visited) {
    createMultipleEffectCharmView(visited, visited.getCharmId() + ".ControlButton"); //$NON-NLS-1$
  }

  @Override
  public void visitUpgradableCharm(IUpgradableCharm visited) {
    createMultipleEffectCharmView(visited, visited.getCharmId() + ".ControlButton"); //$NON-NLS-1$
  }

  private void createMultipleEffectCharmView(IMultipleEffectCharm visitedCharm, final String labelKey) {
    SVGToggleButtonSpecialNodeView subeffectView = factory.createSubeffectCharmView(
            visitedCharm,
            getCharmWidth(),
            getCharacterColor());
    IMultipleEffectCharmConfiguration model = getModelFormCharm(visitedCharm);
    new MultipleEffectCharmPresenter(resources, subeffectView, model).initPresentation();
    if (requiredMoreThanOneLine(visitedCharm, model)) {
      addButtonForCharmConfiguration(labelKey, subeffectView);
    } else {
      addViewDirectly(subeffectView);
    }
  }

  private double getCharmWidth() {
    return presentationProperties.getNodeDimension().getWidth();
  }

  private ICharmConfiguration getCharmConfiguration() {
    return charmModel.getCharmConfiguration();
  }

  private Color getCharacterColor() {
    return presentationProperties.getColor();
  }

  private void addButtonForCharmConfiguration(String labelKey, ISVGSpecialNodeView subeffectView) {
    ISVGSpecialNodeView viewControlButton = factory.createViewControlButton(
            subeffectView,
            getCharmWidth(),
            resources.getString(labelKey));
    addViewDirectly(viewControlButton);
  }

  private void addViewDirectly(ISVGSpecialNodeView view) {
    this.createdView = view;
  }

  private boolean requiresMoreThanOneLine(ISpecialCharm visitedCharm, IOxBodyTechniqueConfiguration model) {
    ICharm originalCharm = findOriginalCharm(visitedCharm);
    return otherCharmsAreRenderedBeneath(originalCharm) && hasMoreThanOneCategory(model);
  }

  private boolean requiredMoreThanOneLine(ISpecialCharm visitedCharm, IMultipleEffectCharmConfiguration model) {
    ICharm originalCharm = findOriginalCharm(visitedCharm);
    return otherCharmsAreRenderedBeneath(originalCharm) && hasMoreThanOneEffect(model);
  }

  private ICharm findOriginalCharm(ISpecialCharm visitedCharm) {
    return getCharmConfiguration().getCharmById(visitedCharm.getCharmId());
  }

  private boolean hasMoreThanOneEffect(IMultipleEffectCharmConfiguration model) {
    return model.getEffects().length > 1;
  }

  private boolean hasMoreThanOneCategory(IOxBodyTechniqueConfiguration model) {
    return model.getCategories().length > 1;
  }

  private boolean otherCharmsAreRenderedBeneath(ICharm originalCharm) {
    return (originalCharm.hasChildren() || originalCharm.isTreeRoot());
  }

  private SVGCategorizedSpecialNodeView createViewForCharm(ISpecialCharm visitedCharm) {
    return factory.createMultiLearnableCharmView(
            visitedCharm,
            getCharmWidth(),
            getCharacterColor());
  }

  @SuppressWarnings("unchecked")
  private <T> T getModelFormCharm(ISpecialCharm visitedCharm) {
    return (T) getCharmConfiguration().getSpecialCharmConfiguration(visitedCharm.getCharmId());
  }

  public ISVGSpecialNodeView getResult() {
    return createdView;
  }
}
