package net.sf.anathema.hero.application.perspective;

import net.sf.anathema.hero.application.perspective.model.CharacterIdentifier;

public class ShowOnSelect implements Selector<CharacterIdentifier> {
  private final CharacterMessaging messaging;
  private final HeroStackPresenter characterStack;

  public ShowOnSelect(CharacterMessaging messaging, HeroStackPresenter characterStack) {
    this.messaging = messaging;
    this.characterStack = characterStack;
  }

  @Override
  public void selected(CharacterIdentifier identifier) {
    messaging.showMessagesFor(identifier);
    characterStack.showCharacter(identifier);
  }
}
