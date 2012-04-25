package net.sf.anathema.framework.environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.logging.LogManager;

import javax.swing.ToolTipManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.sf.anathema.framework.configuration.IInitializationPreferences;

public class AnathemaEnvironment {

  public static void initLogging() {
    System.setProperty("org.apache.commons.logging.simplelog.defaultlog", "error"); //$NON-NLS-1$ //$NON-NLS-2$
    String propertyString = "level=ERROR"; //$NON-NLS-1$
    InputStream logPropertyInputStream = new ByteArrayInputStream(propertyString.getBytes());
    try {
      LogManager.getLogManager().readConfiguration(logPropertyInputStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void initTooltipManager(IInitializationPreferences initializationPreferences) {
    ToolTipManager.sharedInstance().setInitialDelay(0);
    ToolTipManager.sharedInstance().setReshowDelay(0);
    int toolTipTime = initializationPreferences.getTooltipTimePreference();
    ToolTipManager.sharedInstance().setDismissDelay(toolTipTime * 1000);
  }

  public static void initLookAndFeel(IInitializationPreferences initializationPreferences)
          throws ClassNotFoundException,
          InstantiationException,
          IllegalAccessException,
          UnsupportedLookAndFeelException {
    new LookAndFeelInitializer(initializationPreferences).initialize();
  }

  public static void initLocale(IInitializationPreferences preferences) {
    Locale.setDefault(preferences.getPreferredLocale());
  }
}