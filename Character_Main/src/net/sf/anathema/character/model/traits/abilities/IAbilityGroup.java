package net.sf.anathema.character.model.traits.abilities;

import net.sf.anathema.character.library.trait.IFavorableDefaultTrait;
import net.sf.anathema.lib.util.IIdentificate;

public interface IAbilityGroup extends IIdentificate {

  IFavorableDefaultTrait[] getAbilities();
}