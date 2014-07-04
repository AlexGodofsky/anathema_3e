package net.sf.anathema.platform.tool;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.library.interaction.model.ToggleTool;

public class FxToggleTool extends FxBaseTool implements ToggleTool {

  public static FxToggleTool create() {
    ImageView mainIcon = new ImageView();
    ImageView miniIcon = new ImageView();
    ToggleButton button = new ToggleButton("", mainIcon);
    return new FxToggleTool(button, miniIcon, new AdjustSize(button), new SetImage(mainIcon));
  }

  private final ToggleButton button;

  public FxToggleTool(final ToggleButton button, ImageView miniIcon, ImageClosure... onLoad) {
    super(button, miniIcon, onLoad);
    this.button = button;
  }


  @Override
  public void setCommand(Command command) {
    Command deselectAndProcess = new DeselectToggleButtonAndProcess(command);
    super.setCommand(deselectAndProcess);
  }

  @Override
  public void select() {
    button.setSelected(true);
  }

  @Override
  public void deselect() {
    button.setSelected(false);
  }

  public void setStyleClass(String styleClass) {
    button.getStyleClass().add(styleClass);
  }

  public void registerWithGroup(ToggleGroup group) {
    group.getToggles().add(button);
  }

  private class DeselectToggleButtonAndProcess implements Command {
    private Command command;

    public DeselectToggleButtonAndProcess(Command command) {
      this.command = command;
    }

    @Override
    public void execute() {
      deselect();
      command.execute();
    }
  }
}