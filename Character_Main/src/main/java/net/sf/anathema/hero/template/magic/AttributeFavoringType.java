package net.sf.anathema.hero.template.magic;

import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.types.AttributeType;

public class AttributeFavoringType implements FavoringTraitType {
  @Override
  public AttributeType[] getTraitTypesForGenericCharms() {
    return AttributeType.values();
  }

  @Override
  public TraitType getSpellFavoringType() {
    return AttributeType.Intelligence;
  }

  @Override
  public String getId() {
    return "AttributeType";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof AttributeFavoringType;
  }

  @Override
  public int hashCode() {
    return 2;
  }
}