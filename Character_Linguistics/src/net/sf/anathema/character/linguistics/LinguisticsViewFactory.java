package net.sf.anathema.character.linguistics;

import net.sf.anathema.character.generic.additionaltemplate.IAdditionalModel;
import net.sf.anathema.character.generic.framework.additionaltemplate.IAdditionalViewFactory;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.linguistics.presenter.ILinguisticsModel;
import net.sf.anathema.character.linguistics.presenter.LinguisticsPresenter;
import net.sf.anathema.character.linguistics.view.LinguisticsView;
import net.sf.anathema.lib.gui.IView;
import net.sf.anathema.lib.resources.IResources;

public class LinguisticsViewFactory implements IAdditionalViewFactory {

  @Override
  public IView createView(IAdditionalModel model, IResources resources, ICharacterType type) {
    ILinguisticsModel linguisticsModel = ((ILinguisticsAdditionalModel) model).getLinguisticsModel();
    LinguisticsView view = new LinguisticsView();
    new LinguisticsPresenter(linguisticsModel, view, resources).initPresentation();
    return view;
  }
}