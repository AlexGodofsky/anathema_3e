package net.sf.anathema.character.lunar.reporting.content.stats.heartsblood;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.reporting.pdf.content.stats.AbstractTextStatsGroup;
import net.sf.anathema.lib.resources.IResources;

public class HeartsBloodStaminaStatsGroup extends AbstractTextStatsGroup<IHeartsBloodStats> {

  private final IResources resources;

  public HeartsBloodStaminaStatsGroup(IResources resources) {
    this.resources = resources;
  }

  @Override
  public void addContent(PdfPTable table, Font font, IHeartsBloodStats stats) {
    String text = stats == null ? null : stats.getStaminaString();
    table.addCell(createTextCell(font, text));
  }

  @Override
  public Float[] getColumnWeights() {
    return new Float[]{1.5f};
  }

  @Override
  public String getTitle() {
    return resources.getString("Sheet.Lunar.HeartsBlood.Stamina"); //$NON-NLS-1$
  }
}
