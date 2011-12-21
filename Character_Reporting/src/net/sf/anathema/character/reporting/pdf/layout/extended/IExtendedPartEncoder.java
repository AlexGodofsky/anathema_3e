package net.sf.anathema.character.reporting.pdf.layout.extended;

import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IBoxContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IVariableBoxContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.IPdfPageEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.PdfPageConfiguration;

public interface IExtendedPartEncoder {

  public IBoxContentEncoder getAnimaEncoder();

  public IBoxContentEncoder getEssenceEncoder();

  public IBoxContentEncoder getDotsEncoder(OtherTraitType trait, int traitMax, String traitHeaderKey);

  public IBoxContentEncoder getGreatCurseEncoder();

  public IBoxContentEncoder getOverdriveEncoder();

  public IBoxContentEncoder getCombatStatsEncoder();

  public IBoxContentEncoder getSocialCombatEncoder();

  public IBoxContentEncoder getIntimaciesEncoder(ExtendedEncodingRegistry registry);

  public IBoxContentEncoder getHealthAndMovementEncoder();

  public IBoxContentEncoder getHealthEncoder();

  public IBoxContentEncoder getMovementEncoder();

  public boolean hasMagicPage();

  public float getWeaponryHeight();

  public IVariableBoxContentEncoder[] getAdditionalFirstPageEncoders();

  public IPdfPageEncoder[] getAdditionalPages(PdfPageConfiguration configuration);

  public boolean isEncodeAttributeAsFavorable();
}
