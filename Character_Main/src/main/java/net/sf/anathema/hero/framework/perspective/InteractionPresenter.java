package net.sf.anathema.hero.framework.perspective;

import net.sf.anathema.hero.framework.perspective.model.CharacterIdentifier;
import net.sf.anathema.hero.framework.perspective.model.ItemSelectionModel;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.interaction.view.InteractionView;
import net.sf.anathema.library.io.SingleFileChooser;
import net.sf.anathema.platform.environment.Environment;

public class InteractionPresenter {

  private ItemSelectionModel model;
  private InteractionView view;
  private final Environment environment;
  private CharacterGridView gridView;
  private Selector<CharacterIdentifier> selector;
  private final SingleFileChooser fileChooser;

  public InteractionPresenter(ItemSelectionModel model, InteractionView view, Environment environment, CharacterGridView gridView,
                              Selector<CharacterIdentifier> selector, SingleFileChooser fileChooser) {
    this.model = model;
    this.view = view;
    this.environment = environment;
    this.gridView = gridView;
    this.selector = selector;
    this.fileChooser = fileChooser;
  }

  public void initPresentation() {
    initNewInteraction();
    initSaveInteraction();
    initControlledPrintInteraction();
    initExperiencedInteraction();
  }

  private void initSaveInteraction() {
    Tool tool = view.addTool();
    new SaveInteractionPresenter(model, tool, environment).initPresentation();
  }

  private void initNewInteraction() {
    new NewInteractionPresenter(model, view.addTool(), environment, gridView, selector).initPresentation();
  }

  private void initControlledPrintInteraction() {
    new PrintInteractionPresenter(model, view.addMenuTool(), environment, fileChooser).initPresentation();
  }

  private void initExperiencedInteraction() {
    new ExperiencedInteractionPresenter(model, view.addToggleTool(), environment).initPresentation();
  }
}