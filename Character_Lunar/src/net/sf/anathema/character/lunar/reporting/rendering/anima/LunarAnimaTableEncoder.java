package net.sf.anathema.character.lunar.reporting.rendering.anima;

import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import net.sf.anathema.character.reporting.pdf.content.ReportContent;
import net.sf.anathema.character.reporting.pdf.content.stats.anima.ColumnDescriptor;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.anima.AnimaTableEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.lib.resources.IResources;

public class LunarAnimaTableEncoder extends AnimaTableEncoder {

  public LunarAnimaTableEncoder(IResources resources, BaseFont baseFont, float fontSize) {
    super(resources, baseFont, fontSize);
  }

  @Override
  protected ColumnDescriptor[] getColumns() {
    return new ColumnDescriptor[] { new ColumnDescriptor(0.135f, "Sheet.AnimaTable.Header.Motes"), //$NON-NLS-1$
        new ColumnDescriptor(0.495f, "Sheet.AnimaTable.Header.BannerFlare"), //$NON-NLS-1$
        new ColumnDescriptor(0.145f, "Sheet.AnimaTable.Header.FormsLocked"), //$NON-NLS-1$
        new ColumnDescriptor(0.225f, "Sheet.AnimaTable.Header.Stealth") }; //$NON-NLS-1$
  }

  @Override
  protected void addAnimaRow(SheetGraphics graphics, PdfPTable table, int level, ReportContent content, String descriptionPrefix) {
    table.addCell(createRangeCell(level, content.getCharacter()));
    table.addCell(createDescriptionCell(level, descriptionPrefix));
    table.addCell(createFormCell(graphics, level));
    table.addCell(createStealthCell(level));
  }

  protected final PdfPCell createFormCell(SheetGraphics graphics, int level) {
    if (level < 2) {
      return createContentCell(""); //$NON-NLS-1$
    }
    PdfPCell cell = new PdfPCell(new Phrase(graphics.createSymbolChunk()));
    configureCell(cell);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return cell;
  }
}
