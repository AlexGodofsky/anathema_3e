package net.sf.anathema.character.equipment.impl.reporting.rendering.possessions;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.GlobalEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.Resources;

@RegisteredEncoderFactory
public class PossessionsEncoderFactory extends GlobalEncoderFactory {

  public PossessionsEncoderFactory() {
    super(EncoderIds.POSSESSIONS);
  }

  @Override
  public ContentEncoder create(Resources resources, BasicContent content) {
    return new PossessionsEncoder();
  }
}
