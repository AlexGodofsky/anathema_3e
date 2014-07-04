package net.sf.anathema.framework.repository.preferences;

import net.sf.anathema.framework.preferences.elements.PreferenceModel;
import net.sf.anathema.framework.preferences.elements.PreferencePresenter;
import net.sf.anathema.framework.preferences.elements.PreferenceView;
import net.sf.anathema.framework.preferences.elements.RegisteredPreferencePresenter;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.workflow.textualdescription.ITextView;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.event.ObjectValueListener;
import net.sf.anathema.platform.environment.Environment;

import java.nio.file.Path;

@RegisteredPreferencePresenter
public class RepositoryPreferencePresenter implements PreferencePresenter {
  private RepositoryPreferenceModel model;
  private RepositoryPreferenceView view;
  private Environment environment;

  @Override
  public void initialize() {
    initRepoDisplay();
    initBrowseForFolder();
    initResetToDefault();
    initShowInExplorer();
  }

  private void initResetToDefault() {
    Tool tool = view.addTool();
    tool.setText(environment.getString("Preferences.Repository.Default.Button"));
    tool.setTooltip(environment.getString("Preferences.Repository.Default.Tooltip"));
    tool.setCommand(new Command() {
      @Override
      public void execute() {
        model.resetToDefault();
      }
    });
  }

  private void initBrowseForFolder() {
    Tool tool = view.addTool();
    tool.setText(environment.getString("Preferences.Repository.Choose.Button"));
    tool.setTooltip(environment.getString("Preferences.Repository.Choose.Tooltip"));
    tool.setCommand(new Command() {
      @Override
      public void execute() {
        view.selectNewRepository("Preferences.Repository.Choose.Prompt");
      }
    });
    view.whenRepositoryChangeIsRequested(new ObjectValueListener<Path>() {
      @Override
      public void valueChanged(Path path) {
        model.requestChangeOfRepositoryPath(path);
      }
    });
  }

  private void initShowInExplorer() {
    if (!view.canOpenInExplorer()) {
      return;
    }
    Tool tool = view.addTool();
    tool.setText(environment.getString("Preferences.Repository.Browse.Button"));
    tool.setTooltip(environment.getString("Preferences.Repository.Browse.Tooltip"));
    tool.setCommand(new Command() {
      @Override
      public void execute() {
        view.showInExplorer(model.getRepositoryPath());
      }
    });
  }

  private void initRepoDisplay() {
    final ITextView textView = view.addRepositoryDisplay(environment.getString("Preferences.Repository.Label"));
    textView.setEnabled(false);
    model.whenLocationChanges(new ChangeListener() {
      @Override
      public void changeOccurred() {
        showRepoInView(textView);
      }
    });
    showRepoInView(textView);
  }

  private void showRepoInView(ITextView textView) {
    textView.setText(model.getRepositoryPath().toAbsolutePath().toString());
  }

  @Override
  public Class getViewClass() {
    return RepositoryPreferenceView.class;
  }

  @Override
  public Class getModelClass() {
    return RepositoryPreferenceModel.class;
  }

  @Override
  public String getTitle() {
    return environment.getString("Preferences.Repository");
  }

  @Override
  public void useModel(PreferenceModel preferenceModel) {
    this.model = (RepositoryPreferenceModel) preferenceModel;
  }

  @Override
  public void useView(PreferenceView view) {
    this.view = (RepositoryPreferenceView) view;
  }

  @Override
  public void useEnvironment(Environment environment) {
    this.environment = environment;
  }
}