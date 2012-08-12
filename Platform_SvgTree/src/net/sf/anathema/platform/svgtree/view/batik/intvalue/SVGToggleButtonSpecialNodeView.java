package net.sf.anathema.platform.svgtree.view.batik.intvalue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.lib.workflow.booleanvalue.IBooleanValueView;
import net.sf.anathema.platform.svgtree.presenter.view.ISVGSpecialNodeView;
import net.sf.anathema.platform.svgtree.view.batik.IBoundsCalculator;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGGElement;

public class SVGToggleButtonSpecialNodeView implements ISVGSpecialNodeView {

  private final String id;
  private final double width;
  private final Color color;
  private final List<SVGBooleanValueDisplay> effects = new ArrayList<SVGBooleanValueDisplay>();

  public SVGToggleButtonSpecialNodeView(String id, double width, Color color) {
    this.id = id;
    this.width = width;
    this.color = color;
  }

  @Override
  public SVGGElement initGui(SVGOMDocument document, IBoundsCalculator boundsCalculator) {
    SVGGElement groupElement = (SVGGElement) document.createElementNS(
        SVGDOMImplementation.SVG_NAMESPACE_URI,
        SVGConstants.SVG_G_TAG);
    for (int index = 0; index < effects.size(); index++) {
      Element displayElement = effects.get(index).initGui(document);
      DomUtilities.setAttribute(
          displayElement,
          SVGConstants.SVG_TRANSFORM_ATTRIBUTE,
          "translate(0," + index * SVGIntValueDisplay.getDiameter(width) * 1.1 + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      groupElement.appendChild(displayElement);
    }
    return groupElement;
  }

  @Override
  public void reset() {
    // Nothing to do
  }

  @Override
  public void hide() {
    for (SVGBooleanValueDisplay effect : effects) {
      effect.setVisible(false);
    }
  }

  @Override
  public String getNodeId() {
    return id;
  }

  public IBooleanValueView addSubeffect(String label) {
    SVGBooleanValueDisplay display = new SVGBooleanValueDisplay(label, width, color);
    effects.add(display);
    return display;
  }
}