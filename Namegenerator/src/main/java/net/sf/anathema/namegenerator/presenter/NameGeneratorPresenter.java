package net.sf.anathema.namegenerator.presenter;

import com.google.common.base.Joiner;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.namegenerator.presenter.model.INameGeneratorModel;
import net.sf.anathema.namegenerator.presenter.view.NameGeneratorView;

public class NameGeneratorPresenter {

  private final NameGeneratorView view;
  private final INameGeneratorModel model;
  private final Resources resources;

  public NameGeneratorPresenter(Resources resources, NameGeneratorView view, INameGeneratorModel model) {
    this.view = view;
    this.model = model;
    this.resources = resources;
  }

  public void initPresentation() {
    for (Identifier generatorType : model.getGeneratorTypes()) {
      String formattedLabel = resources.getString(generatorType.getId());
      view.addNameGeneratorType(formattedLabel, generatorType);
    }
    initSelectedGeneratorTypePresentation();
    initGenerationPresentation();
  }

  private void initGenerationPresentation() {
    String label = resources.getString("Namegenerator.GenerateButton.Label");
    view.addGenerationAction(label,
            new Command() {
              @Override
              public void execute() {
                String[] generatedNames = model.generateNames(50);
                view.setResult(Joiner.on("\n").join(generatedNames));
              }
            });
  }

  private void initSelectedGeneratorTypePresentation() {
    view.addGeneratorTypeChangeListener(new ChangeListener() {
      @Override
      public void changeOccurred() {
        model.setGeneratorType((Identifier) view.getSelectedGeneratorType());
      }
    });
    model.addGeneratorTypeChangeListener(new ChangeListener() {
      @Override
      public void changeOccurred() {
        view.setSelectedGeneratorType(model.getSelectedGeneratorType());
      }
    });
    view.setSelectedGeneratorType(model.getSelectedGeneratorType());
  }
}