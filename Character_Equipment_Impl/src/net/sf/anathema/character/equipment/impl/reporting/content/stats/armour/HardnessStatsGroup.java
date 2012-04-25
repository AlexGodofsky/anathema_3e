package net.sf.anathema.character.equipment.impl.reporting.content.stats.armour;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.equipment.impl.reporting.content.stats.AbstractValueEquipmentStatsGroup;
import net.sf.anathema.character.generic.equipment.weapon.IArmourStats;
import net.sf.anathema.character.generic.health.HealthType;
import net.sf.anathema.lib.resources.IResources;

public class HardnessStatsGroup extends AbstractValueEquipmentStatsGroup<IArmourStats> implements IArmourStatsGroup {

  public HardnessStatsGroup(IResources resources) {
    super(resources, "Hardness"); //$NON-NLS-1$
  }

  @Override
  public int getColumnCount() {
    return 2;
  }

  @Override
  public void addContent(PdfPTable table, Font font, IArmourStats armour) {
    if (armour == null) {
      table.addCell(createEmptyValueCell(font));
      table.addCell(createEmptyValueCell(font));
    } else {
      table.addCell(createEquipmentValueCell(font, armour.getHardness(HealthType.Bashing)));
      table.addCell(createEquipmentValueCell(font, armour.getHardness(HealthType.Lethal)));
    }
  }

  @Override
  public void addTotal(PdfPTable table, Font font, IArmourStats armour) {
    table.addCell(createFinalValueCell(font, armour.getHardness(HealthType.Bashing)));
    table.addCell(createFinalValueCell(font, armour.getHardness(HealthType.Lethal)));
  }

  @Override
  protected String getZeroPrefix() {
    return ""; //$NON-NLS-1$
  }

  @Override
  protected String getPositivePrefix() {
    return ""; //$NON-NLS-1$
  }
}
