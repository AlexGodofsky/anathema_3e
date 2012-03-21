package net.sf.anathema.character.reporting.pdf.layout.extended;

import net.sf.anathema.lib.resources.IResources;

public abstract class AbstractSecondEditionExaltPdfPartEncoder extends AbstractSecondEditionPartEncoder {

  public AbstractSecondEditionExaltPdfPartEncoder(IResources resources) {
    super(resources);
  }

  @Override
  public boolean hasMagicPage() {
    return true;
  }
}
