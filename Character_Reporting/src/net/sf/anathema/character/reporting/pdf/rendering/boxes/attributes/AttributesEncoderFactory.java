package net.sf.anathema.character.reporting.pdf.rendering.boxes.attributes;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.AbstractEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.IResources;

import static net.sf.anathema.character.generic.template.magic.FavoringTraitType.AttributeType;

@RegisteredEncoderFactory
public class AttributesEncoderFactory extends AbstractEncoderFactory {

  public AttributesEncoderFactory() {
    super(EncoderIds.ATTRIBUTES);
  }

  @Override
  public ContentEncoder create(IResources resources, BasicContent content) {
    return new AttributesEncoder(resources, content.favorsTraitType(AttributeType));
  }

  @Override
  public boolean supports(BasicContent content) {
    return true;
  }
}
