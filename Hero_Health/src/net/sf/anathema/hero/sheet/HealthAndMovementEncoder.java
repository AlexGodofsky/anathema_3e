package net.sf.anathema.hero.sheet;

import net.sf.anathema.hero.sheet.pdf.encoder.general.table.ITableEncoder;
import net.sf.anathema.lib.resources.Resources;

public class HealthAndMovementEncoder extends AbstractHealthAndMovementEncoder {

  public HealthAndMovementEncoder(Resources resources) {
    super(resources);
  }

  @Override
  protected ITableEncoder createTableEncoder() {
    return new HealthAndMovementTableEncoder(getResources());
  }
}