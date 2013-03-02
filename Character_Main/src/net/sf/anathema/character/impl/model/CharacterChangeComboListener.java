/**
 *
 */
package net.sf.anathema.character.impl.model;

import net.sf.anathema.character.impl.model.context.CharacterListening;
import net.sf.anathema.character.model.charm.ICombo;
import net.sf.anathema.character.model.charm.IComboConfigurationListener;

public final class CharacterChangeComboListener implements IComboConfigurationListener {

  private final CharacterListening listening;

  public CharacterChangeComboListener(CharacterListening listening) {
    this.listening = listening;
  }

  @Override
  public void editEnded() {
    // Nothing to do
  }

  @Override
  public void editBegun(ICombo combo) {
    // Nothing to do
  }

  @Override
  public void comboDeleted(ICombo combo) {
    listening.fireCharacterChanged();
  }

  @Override
  public void comboChanged(ICombo combo) {
    listening.fireCharacterChanged();
  }

  @Override
  public void comboAdded(ICombo combo) {
    listening.fireCharacterChanged();
  }
}