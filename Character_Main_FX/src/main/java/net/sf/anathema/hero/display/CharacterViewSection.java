package net.sf.anathema.hero.display;

import net.sf.anathema.hero.display.fx.ContentProperties;
import net.sf.anathema.hero.framework.display.SubViewRegistry;
import net.sf.anathema.hero.individual.view.SectionView;
import net.sf.anathema.library.fx.NodeHolder;

public class CharacterViewSection implements SectionView {

  private final MultipleContentView view;
  private final SubViewRegistry subViewFactory;

  public CharacterViewSection(CharacterPane characterPane, String title, SubViewRegistry subViewFactory) {
    this.view = characterPane.addMultipleContentView(title);
    this.subViewFactory = subViewFactory;
  }

  @Override
  public <T> T addView(String title, Class<T> viewClass) {
    T newView = subViewFactory.get(viewClass);
    NodeHolder viewToAdd = (NodeHolder) newView;
    view.addView(viewToAdd, new ContentProperties(title));
    return newView;
  }

  @Override
  public void finishInitialization() {
    view.finishInitialization();
  }
}