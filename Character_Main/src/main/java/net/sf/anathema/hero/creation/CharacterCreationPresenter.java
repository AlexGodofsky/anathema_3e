package net.sf.anathema.hero.creation;

import net.sf.anathema.character.main.template.HeroTemplate;
import net.sf.anathema.character.main.type.CharacterType;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.interaction.ToggleTool;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.selection.VetoableObjectSelectionView;
import net.sf.anathema.lib.workflow.wizard.selection.IItemOperator;

import java.util.Arrays;
import java.util.Comparator;

public class CharacterCreationPresenter {

  private final CharacterCreationView view;
  private final CharacterCreationPageProperties properties;
  private final ICharacterItemCreationModel model;
  private IItemOperator operator;

  public CharacterCreationPresenter(CharacterCreationView view, CharacterCreationPageProperties properties, ICharacterItemCreationModel model, IItemOperator operator) {
    this.view = view;
    this.properties = properties;
    this.model = model;
    this.operator = operator;
  }
  
  public void initPresentation(){
    ToggleButtonPanel panel = view.addToggleButtonPanel();
    for (final CharacterType type : model.getAvailableCharacterTypes()) {
      ToggleTool button = panel.addButton(properties.getTypeString(type));
      button.setIcon(properties.getTypeIcon(type));
      button.setCommand(new Command() {
        @Override
        public void execute() {
          model.setCharacterType(type);
        }
      });
      if (type.equals(model.getSelectedTemplate().getTemplateType().getCharacterType())) {
        button.select();
      } else {
        button.deselect();
      }
    }
    final VetoableObjectSelectionView<HeroTemplate> list = view.addObjectSelectionList();
    list.setCellRenderer(properties.getTemplateUI());
    list.addObjectSelectionChangedListener(new ObjectValueListener<HeroTemplate>() {
      @Override
      public void valueChanged(HeroTemplate newValue) {
        if (newValue == null) {
          return;
        }
        model.setSelectedTemplate(newValue);
      }
    });
    view.whenCanceled(new Command() {
      @Override
      public void execute() {
        view.close();
      }
    });
    view.whenConfirmed(new Command() {
      @Override
      public void execute() {
        view.close();
        operator.operate(model.getSelectedTemplate());
      }
    });
    model.addListener(new ChangeListener() {
      @Override
      public void changeOccurred() {
        refreshList(list);
      }
    });
    refreshList(list);
    view.show();
  }

  protected void refreshList(VetoableObjectSelectionView<HeroTemplate> list) {
    HeroTemplate[] availableTemplates = model.getAvailableTemplates();
    Arrays.sort(availableTemplates, new Comparator<HeroTemplate>() {
      @Override
      public int compare(HeroTemplate o1, HeroTemplate o2) {
        return getTemplateResource(o1).compareTo(getTemplateResource(o2));
      }
    });
    list.setObjects(availableTemplates);
    list.setSelectedObject(model.getSelectedTemplate());
  }


  private String getTemplateResource(HeroTemplate o1) {
    return properties.getTemplateUI().getLabel(o1);
  }
}