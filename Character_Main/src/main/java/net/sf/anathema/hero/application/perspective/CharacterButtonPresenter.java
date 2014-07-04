package net.sf.anathema.hero.application.perspective;

import net.sf.anathema.hero.application.perspective.model.CharacterIdentifier;
import net.sf.anathema.hero.application.perspective.model.CharacterItemModel;
import net.sf.anathema.library.resources.Resources;

public class CharacterButtonPresenter {

  private Resources resources;
  private Selector<CharacterIdentifier> selector;
  private CharacterItemModel character;
  private CharacterGridView view;

  public CharacterButtonPresenter(Resources resources, Selector<CharacterIdentifier> selector, CharacterItemModel character, CharacterGridView view) {
    this.resources = resources;
    this.selector = selector;
    this.character = character;
    this.view = view;
  }

  public void initPresentation() {
    CharacterButtonDto dto = extractButtonDto();
    view.addButton(dto, selector);
    initDescriptiveFeatureListening();
  }

  private void initDescriptiveFeatureListening() {
    character.whenFeaturesChange(() -> view.updateButton(extractButtonDto()));
  }

  private CharacterButtonDto extractButtonDto() {
    return new ToCharacterButtonDto(resources).apply(character.getDescriptiveFeatures());
  }
}
