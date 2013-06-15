package net.sf.anathema.character.generic.framework.util;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;

public class ExperienceUtilities {

  public static void setLabelColor(Component component, boolean enabled) {
    if (component instanceof JLabel) {
      component.setEnabled(true);
      if (enabled) {
        component.setForeground(SystemColor.textText);
      } else {
        component.setForeground(Color.DARK_GRAY);
      }
    }
  }
}