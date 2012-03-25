package net.sf.anathema.character.equipment.impl.reporting.rendering.panoply;

import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.equipment.impl.reporting.content.ShieldContent;
import net.sf.anathema.character.equipment.impl.reporting.rendering.EquipmentTableEncoder;
import net.sf.anathema.character.generic.equipment.weapon.IShieldStats;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.stats.IStatsGroup;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;

public class ShieldTableEncoder extends EquipmentTableEncoder<IShieldStats, ShieldContent> {

  public ShieldTableEncoder() {
    super(ShieldContent.class);
  }

  @Override
  protected PdfPTable createTable(SheetGraphics graphics, ReportSession session, Bounds bounds) {
    IStatsGroup<IShieldStats>[] groups = createStatsGroups(session);
    float[] columnWidths = calculateColumnWidths(groups);
    PdfPTable shieldTable = new PdfPTable(columnWidths);
    shieldTable.setTotalWidth(bounds.width);
    encodeContent(graphics, shieldTable, session, bounds);
    return shieldTable;
  }
}
