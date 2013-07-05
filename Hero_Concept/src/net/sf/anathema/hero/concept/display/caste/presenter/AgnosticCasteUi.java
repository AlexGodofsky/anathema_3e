package net.sf.anathema.hero.concept.display.caste.presenter;

import net.sf.anathema.character.generic.caste.CasteType;
import net.sf.anathema.character.generic.template.presentation.IPresentationProperties;
import net.sf.anathema.character.presenter.CasteUI;
import net.sf.anathema.charmtree.presenter.SelectIdentifierConfiguration;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.resources.Resources;

public class AgnosticCasteUi extends SelectIdentifierConfiguration<CasteType> {
  private final CasteUI casteUI;

  public AgnosticCasteUi(Resources resources, IPresentationProperties properties) {
    super(resources);
    this.casteUI = new CasteUI(properties);
  }

  @Override
  protected boolean isUnselected(CasteType value) {
    return super.isUnselected(value) || value == CasteType.NULL_CASTE_TYPE;
  }

  @Override
  protected String getKeyForObject(CasteType value) {
    return "Caste." + value.getId();
  }


  @Override
  protected RelativePath getIconForObject(CasteType value) {
    return casteUI.getSmallCasteIconPath(value);
  }
}