package net.sf.anathema.character.lunar.reporting.layout;

import com.lowagie.text.pdf.BaseFont;
import net.sf.anathema.character.lunar.reporting.rendering.anima.LunarAnimaEncoderFactory;
import net.sf.anathema.character.lunar.reporting.rendering.greatcurse.SecondEditionLunarGreatCurseEncoder;
import net.sf.anathema.character.reporting.pdf.layout.extended.AbstractSecondEditionExaltPdfPartEncoder;
import net.sf.anathema.character.reporting.pdf.layout.extended.ExtendedEncodingRegistry;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.EncoderRegistry;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.PageEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.PdfPageConfiguration;
import net.sf.anathema.lib.resources.IResources;

public class Extended2ndEditionLunarPartEncoder extends AbstractSecondEditionExaltPdfPartEncoder {

  private final BaseFont baseFont;
  private EncoderRegistry encoderRegistry;

  public Extended2ndEditionLunarPartEncoder(EncoderRegistry encoderRegistry, IResources resources, ExtendedEncodingRegistry registry,
    int essenceMax) {
    super(resources, registry, essenceMax);
    this.encoderRegistry = encoderRegistry;
    this.baseFont = registry.getBaseFont();
  }

  public ContentEncoder getGreatCurseEncoder() {
    return new SecondEditionLunarGreatCurseEncoder();
  }

  @Override
  public ContentEncoder getAnimaEncoder() {
    return new LunarAnimaEncoderFactory(getResources()).createAnimaEncoder();
  }

  @Override
  public PageEncoder[] getAdditionalPages(PdfPageConfiguration configuration) {
    return new PageEncoder[] { new Lunar2ndEditionAdditionalPageEncoder(encoderRegistry, getResources(), configuration) };
  }
}
