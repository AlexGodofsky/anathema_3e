package net.sf.anathema.character.generic.framework.xml.presentation;

import net.sf.anathema.lib.lang.clone.ReflectionCloneableObject;
import net.sf.anathema.platform.svgtree.document.visualizer.ITreePresentationProperties;

import java.awt.Dimension;

public class GenericCharmPresentationProperties extends ReflectionCloneableObject<GenericCharmPresentationProperties> implements
        ITreePresentationProperties {

  private String polygonString;
  private Dimension charmDimension;
  private Dimension gapDimension;

  public String getNodeFramePolygonString() {
    return polygonString;
  }

  public Dimension getNodeDimension() {
    return charmDimension;
  }

  public Dimension getGapDimension() {
    return gapDimension;
  }

  public final Dimension getVerticalLineDimension() {
    return new Dimension(getGapDimension().width, getNodeDimension().height);
  }

  public void setPolygonString(String polygonString) {
    this.polygonString = polygonString;
  }

  public void setCharmDimension(Dimension dimension) {
    this.charmDimension = dimension;
  }

  public void setGapDimension(Dimension dimension) {
    this.gapDimension = dimension;
  }
}