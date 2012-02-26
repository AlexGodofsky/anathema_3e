package net.sf.anathema.character.abyssal.template;

import net.sf.anathema.character.generic.framework.xml.presentation.GenericCharmPresentationProperties;

import java.awt.Dimension;

public class AbyssalCharmPresentationProperties extends GenericCharmPresentationProperties {

  private static final String POLYGON = "99.0,4.0 88.0,11.0 37.0,18.0 45.0,26.0 15.0,25.0 10.0,37.0 3.0,45.0 10.0,53.0 15.0,65.0 45.0,64.0 37.0,72.0 88.0,79.0 99.0,86.0 110.0,79.0 161.0,72.0 153.0,64.0 183.0,65.0 188.0,53.0 195.0,45.0 188.0,37.0 183.0,25.0 153.0,26.0 161.0,18.0 110.0,11.0"; //$NON-NLS-1$

  public AbyssalCharmPresentationProperties() {
    setPolygonString(POLYGON);
    setCharmDimension(new Dimension(198, 85));
  }
}