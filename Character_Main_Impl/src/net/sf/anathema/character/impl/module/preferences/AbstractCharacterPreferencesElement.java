package net.sf.anathema.character.impl.module.preferences;

import net.sf.anathema.lib.util.IIdentificate;

public abstract class AbstractCharacterPreferencesElement implements ICharacterPreferencesElement {

  @Override
  public IIdentificate getCategory() {
    return CHARACTER_CATEGORY;
  }
}