package net.sf.anathema.hero.application.perspective;

import net.sf.anathema.hero.application.item.HeroItemData;
import net.sf.anathema.hero.application.item.Item;
import net.sf.anathema.hero.application.perspective.model.CharacterIdentifier;
import net.sf.anathema.hero.application.perspective.model.ItemSystemModel;

import java.util.ArrayList;
import java.util.List;

public class HeroStackPresenter {
  private final List<CharacterIdentifier> knownCharacters = new ArrayList<>();
  private final ItemSystemModel model;
  private final HeroStackBridge bridge;

  public HeroStackPresenter(HeroStackBridge bridge, ItemSystemModel model) {
    this.bridge = bridge;
    this.model = model;
  }

  public void showCharacter(CharacterIdentifier identifier) {
    if (!knownCharacters.contains(identifier)) {
      addViewForCharacter(identifier);
      knownCharacters.add(identifier);
    }
    bridge.showCharacterView(identifier);
    model.setCurrentCharacter(identifier);
  }

  private void addViewForCharacter(CharacterIdentifier identifier) {
    Item item = model.loadItem(identifier);
    bridge.addViewForCharacter(identifier, (HeroItemData) item.getItemData());
  }
}
