package net.sf.anathema.character.lunar.reporting.content.stats.heartsblood;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.reporting.pdf.content.stats.AbstractTextStatsGroup;
import net.sf.anathema.lib.resources.IResources;

public class HeartsBloodStrengthStatsGroup extends AbstractTextStatsGroup<IHeartsBloodStats> {
  private final IResources resources;

  public HeartsBloodStrengthStatsGroup(IResources resources) {
    this.resources = resources;
  }

  @Override
  public void addContent(PdfPTable table, Font font, IHeartsBloodStats stats) {
    String text = stats == null ? null : stats.getStrengthString();
    table.addCell(createTextCell(font, text));
  }

  @Override
  public Float[] getColumnWeights() {
    return new Float[]{1.5f};
  }

  @Override
  public String getTitle() {
    return resources.getString("Sheet.Lunar.HeartsBlood.Strength"); //$NON-NLS-1$
  }
}
