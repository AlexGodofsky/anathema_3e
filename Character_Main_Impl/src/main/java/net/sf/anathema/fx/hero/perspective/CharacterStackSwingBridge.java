package net.sf.anathema.fx.hero.perspective;

import net.sf.anathema.framework.repository.Item;
import net.sf.anathema.framework.swing.IView;
import net.sf.anathema.hero.framework.perspective.CharacterStackBridge;
import net.sf.anathema.hero.framework.perspective.model.CharacterIdentifier;
import net.sf.anathema.swing.hero.perspective.StackView;

import javax.swing.JComponent;

public class CharacterStackSwingBridge implements CharacterStackBridge {

  private final StackView stackView;
  private final CharacterViewFactory viewFactory;

  public CharacterStackSwingBridge(CharacterViewFactory viewFactory, StackView stackView) {
    this.viewFactory = viewFactory;
    this.stackView = stackView;
  }

  @Override
  public void addViewForCharacter(CharacterIdentifier identifier, Item item) {
    IView itemView = viewFactory.createView(item);
    stackView.addView(identifier, itemView);
  }

  @Override
  public void showCharacterView(CharacterIdentifier identifier) {
    stackView.showView(identifier);
  }

  public JComponent getComponent() {
    return stackView.getComponent();
  }
}