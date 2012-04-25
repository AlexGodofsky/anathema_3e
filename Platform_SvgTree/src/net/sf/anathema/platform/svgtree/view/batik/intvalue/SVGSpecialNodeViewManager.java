package net.sf.anathema.platform.svgtree.view.batik.intvalue;

import java.awt.Rectangle;

import net.sf.anathema.platform.svgtree.document.components.ISVGCascadeXMLConstants;
import net.sf.anathema.platform.svgtree.presenter.view.IAnathemaCanvas;
import net.sf.anathema.platform.svgtree.presenter.view.ISVGSpecialNodeView;
import net.sf.anathema.platform.svgtree.presenter.view.ISpecialNodeViewManager;
import net.sf.anathema.platform.svgtree.view.batik.AnathemaCanvas;
import net.sf.anathema.platform.svgtree.view.batik.IBoundsCalculator;

import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGGElement;
import org.w3c.dom.svg.SVGSVGElement;

public class SVGSpecialNodeViewManager implements ISpecialNodeViewManager {

  private final IAnathemaCanvas canvas;
  private final IBoundsCalculator boundsCalculator;

  public SVGSpecialNodeViewManager(AnathemaCanvas canvas, IBoundsCalculator calculator) {
    this.canvas = canvas;
    this.boundsCalculator = calculator;
  }

  @Override
  public void setVisible(final ISVGSpecialNodeView specialView, boolean visible) {
    if (!visible) {
      specialView.setVisible(false);
      return;
    }
    SVGOMDocument document = (SVGOMDocument) canvas.getSVGDocument();
    Element viewElement = specialView.initGui(document, boundsCalculator);
    DomUtilities.setAttribute(viewElement, ISVGCascadeXMLConstants.ATTRIB_IS_CONTROL, SVGConstants.SVG_TRUE_VALUE);
    Rectangle bounds = getGroupBounds(specialView.getNodeId());
    if (bounds == null) {
      specialView.setVisible(false);
      return;
    }
    SVGSVGElement rootElement = document.getRootElement();
    float xPosition = bounds.x / rootElement.getScreenCTM().getA();
    float yPosition = (bounds.y + bounds.height + 5) / rootElement.getScreenCTM().getD();
    DomUtilities.setAttribute(
        viewElement,
        SVGConstants.SVG_TRANSFORM_ATTRIBUTE,
        "translate(" + xPosition + "," + yPosition + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    rootElement.appendChild(viewElement);
  }

  private Rectangle getGroupBounds(final String nodeId) {
    SVGGElement svgElement = (SVGGElement) canvas.getElementById(nodeId);
    if (svgElement == null) {
      return null;
    }
    return boundsCalculator.getBounds(svgElement);
  }
}