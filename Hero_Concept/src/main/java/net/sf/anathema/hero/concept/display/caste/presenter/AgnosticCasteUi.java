package net.sf.anathema.hero.concept.display.caste.presenter;

import net.sf.anathema.hero.template.presentation.IPresentationProperties;
import net.sf.anathema.points.display.overview.presenter.SelectIdentifierConfiguration;
import net.sf.anathema.hero.concept.CasteType;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.framework.environment.Resources;

public class AgnosticCasteUi extends SelectIdentifierConfiguration<CasteType> {
  private final CasteUI casteUI;

  public AgnosticCasteUi(Resources resources, IPresentationProperties properties) {
    super(resources);
    this.casteUI = new CasteUI(properties);
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