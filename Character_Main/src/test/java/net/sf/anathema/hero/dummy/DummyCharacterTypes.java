package net.sf.anathema.hero.dummy;

import net.sf.anathema.hero.environment.CharacterTypes;
import net.sf.anathema.hero.individual.splat.CharacterType;

import java.util.Collections;
import java.util.Iterator;

public class DummyCharacterTypes implements CharacterTypes {
  private CharacterType type;

  @Override
  public CharacterType findById(String id) {
    if (type.getId().equals(id)) {
      return type;
    }
    throw new IllegalArgumentException("No type defined for id:" + id);
  }

  public void add(CharacterType type) {
    this.type = type;
  }

  @Override
  public Iterator<CharacterType> iterator() {
    return Collections.singletonList(type).iterator();
  }
}
