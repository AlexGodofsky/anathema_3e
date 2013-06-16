package net.sf.anathema.framework.value;

import net.sf.anathema.framework.swing.IView;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.event.ActionListener;

public class IconToggleButton implements IView {

  private final JButton button = new NoFocusButton();
  private boolean selected;
  private Icon selectedIcon;
  private Icon unselectedIcon;

  public IconToggleButton(Icon activeIcon) {
    this(activeIcon, null);
  }

  public IconToggleButton(Icon selectedIcon, Icon unselectedIcon) {
    setIconSet(selectedIcon, unselectedIcon);
    button.setPreferredSize(getPreferredSize(selectedIcon));
  }

  public void setIconSet(Icon selectedIcon, Icon unselectedIcon) {
    this.selectedIcon = selectedIcon;
    this.unselectedIcon = unselectedIcon;
    button.setIcon(isSelected() ? selectedIcon : unselectedIcon);
    button.setRolloverIcon(unselectedIcon);
    button.setSelectedIcon(selectedIcon);
    button.setRolloverSelectedIcon(selectedIcon);
    button.setDisabledSelectedIcon(selectedIcon);
  }

  public static Dimension getPreferredSize(Icon icon) {
    return new Dimension(icon.getIconWidth() + 4, icon.getIconHeight() + 4);
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    button.setSelected(selected);
    button.setIcon(isSelected() ? selectedIcon : unselectedIcon);
  }

  public void setEnabled(boolean enabled) {
    button.setEnabled(enabled);
  }

  @Override
  public JComponent getComponent() {
    return button;
  }

  public void addActionListener(ActionListener listener) {
    button.addActionListener(listener);
  }

  public boolean isSelected() {
    return selected;
  }

  public void setToolTipText(String text) {
    button.setToolTipText(text);
  }
}