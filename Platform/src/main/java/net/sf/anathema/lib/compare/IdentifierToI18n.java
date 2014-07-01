package net.sf.anathema.lib.compare;

import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.lib.util.Identifier;

import java.util.function.Function;

public class IdentifierToI18n<Id extends Identifier> implements Function<Id, String> {

  private Resources resources;

  public IdentifierToI18n(Resources resources) {
    this.resources = resources;
  }

  @Override
  public String apply(Id id) {
    return resources.getString(id.getId());
  }
}
