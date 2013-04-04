package net.sf.anathema.character.db.virtueflaw;

import net.sf.anathema.character.generic.template.additional.IAdditionalTemplate;

public class DbVirtueFlawTemplate implements IAdditionalTemplate {

  public static final String TEMPLATE_ID = "Db.VirtueFlaw.Template";

  @Override
  public String getId() {
    return TEMPLATE_ID;
  }
}