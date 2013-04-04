package net.sf.anathema.character.lunar.reporting.rendering;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.impl.traits.EssenceTemplate;
import net.sf.anathema.character.generic.template.abilities.GroupedTraitType;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.generic.traits.types.AttributeGroupType;
import net.sf.anathema.character.generic.traits.types.AttributeType;
import net.sf.anathema.character.lunar.beastform.BeastformTemplate;
import net.sf.anathema.character.lunar.beastform.model.SecondEditionBeastformModel;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.traits.PdfTraitEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.lib.resources.Resources;

import static net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants.LINE_HEIGHT;

public class SecondEditionLunarSpiritFormEncoder implements ContentEncoder {

  private final Resources resources;
  private final PdfTraitEncoder smallTraitEncoder = PdfTraitEncoder.createSmallTraitEncoder();

  public SecondEditionLunarSpiritFormEncoder(Resources resources) {
    this.resources = resources;
  }

  @Override
  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) {
    GroupedTraitType[] attributeGroups = reportSession.getCharacter().getTemplate().getAttributeGroups();
    SecondEditionBeastformModel additionalModel = (SecondEditionBeastformModel) reportSession.getCharacter()
            .getAdditionalModel(BeastformTemplate.TEMPLATE_ID);
    IGenericTraitCollection traitCollection = additionalModel.getSpiritTraitCollection();
    encodeAttributes(graphics, bounds, attributeGroups, traitCollection);
    encodeForm(graphics, bounds, additionalModel.getSpiritForm());
  }

  private void encodeForm(SheetGraphics graphics, Bounds bounds, String form) {
    Font font = graphics.createTableFont();
    Bounds newBounds = new Bounds(bounds.x, bounds.y, bounds.width, bounds.height - 50);
    String text = resources.getString("Sheet.Header.Lunar.SpiritForm") + ": " + form;
    graphics.createSimpleColumn(newBounds).withLeading(LINE_HEIGHT - 2).andTextPart(new Phrase(text, font)).encode();
  }

  private void encodeAttributes(SheetGraphics graphics, Bounds contentBounds, GroupedTraitType[] attributeGroups,
          IGenericTraitCollection traitCollection) {
    float groupSpacing = smallTraitEncoder.getTraitHeight() / 2;
    float y = contentBounds.getMaxY() - 2 * groupSpacing;
    int maximum = EssenceTemplate.SYSTEM_ESSENCE_MAX;
    float width = contentBounds.getWidth();
    for (GroupedTraitType groupedTraitType : attributeGroups) {
      if (!groupedTraitType.getGroupId().equals(AttributeGroupType.Physical.name()) &&
              !groupedTraitType.getTraitType().getId().equals(AttributeType.Appearance.name())) {
        continue;
      }

      ITraitType traitType = groupedTraitType.getTraitType();
      String traitLabel = resources.getString("AttributeType.Name." + traitType.getId()); //$NON-NLS-1$
      int value = traitCollection.getTrait(traitType).getCurrentValue();
      Position position = new Position(contentBounds.x, y);
      y -= smallTraitEncoder.encodeWithText(graphics, traitLabel, position, width, value, maximum);
    }
  }

  @Override
  public String getHeader(ReportSession session) {
    return resources.getString("Sheet.Header.Lunar.SpiritForm");
  }

  @Override
  public boolean hasContent(ReportSession session) {
    return true;
  }
}
