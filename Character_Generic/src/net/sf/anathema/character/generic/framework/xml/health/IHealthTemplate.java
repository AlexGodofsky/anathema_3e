package net.sf.anathema.character.generic.framework.xml.health;

import net.sf.anathema.character.generic.traits.ITraitType;

public interface IHealthTemplate {

  ITraitType[] getToughnessControllingTraits();

  String[] getBaseHealthProviders();

}
