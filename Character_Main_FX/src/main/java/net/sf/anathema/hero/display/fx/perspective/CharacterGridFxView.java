package net.sf.anathema.hero.display.fx.perspective;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.sf.anathema.hero.application.creation.CharacterTemplateCreator;
import net.sf.anathema.hero.application.perspective.CharacterButtonDto;
import net.sf.anathema.hero.application.perspective.CharacterGridView;
import net.sf.anathema.hero.application.perspective.Selector;
import net.sf.anathema.hero.application.perspective.model.CharacterIdentifier;
import net.sf.anathema.hero.display.fx.creation.FxHeroSplatCreator;
import net.sf.anathema.library.fx.Stylesheet;
import net.sf.anathema.platform.fx.environment.DialogFactory;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER;

public class CharacterGridFxView implements CharacterGridView {
  private final ScrollPane scrollPane = new ScrollPane();
  private final ToggleGroup toggleGroup = new ToggleGroup();
  private final MigPane gridPane = new MigPane(new LC().insets("0").gridGap("0", "2"),
          new AC().grow().fill());
  private final Map<CharacterIdentifier, CharacterGridButton> buttonsByIdentifier = new HashMap<>();
  private final DialogFactory dialogFactory;

  public CharacterGridFxView(DialogFactory dialogFactory) {
    this.dialogFactory = dialogFactory;
    scrollPane.setContent(gridPane);
    scrollPane.setHbarPolicy(AS_NEEDED);
    scrollPane.setVbarPolicy(NEVER);
    new Stylesheet("skin/character/characternavigation.css").applyToParent(scrollPane);
  }

  @Override
  public void addButton(CharacterButtonDto dto, Selector<CharacterIdentifier> characterSelector) {
    createGridButton(dto, characterSelector);
  }

  @Override
  public void selectButton(CharacterIdentifier identifier) {
    buttonsByIdentifier.get(identifier).setSelected(true);
  }

  @Override
  public void updateButton(CharacterButtonDto dto) {
    buttonsByIdentifier.get(dto.identifier).setContent(dto);
  }

  @Override
  public CharacterTemplateCreator createNewCharacter() {
    return new FxHeroSplatCreator(dialogFactory);
  }

  private CharacterGridButton createGridButton(CharacterButtonDto dto,
                                               Selector<CharacterIdentifier> characterSelector) {
    CharacterGridButton characterGridButton = new CharacterGridButton();
    characterGridButton.initContent(dto, characterSelector);
    characterGridButton.setToggleGroup(toggleGroup);
    buttonsByIdentifier.put(dto.identifier, characterGridButton);
    gridPane.getChildren().add(0, characterGridButton.getNode());
    scrollPane.setVvalue(0);
    return characterGridButton;
  }


  public Node getNode() {
    return scrollPane;
  }
}