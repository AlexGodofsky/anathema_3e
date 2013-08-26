package net.sf.anathema.fx.hero.traitview;

import net.sf.anathema.character.main.framework.RegisteredCharacterView;
import net.sf.anathema.character.main.library.trait.view.GroupedFavorableTraitConfigurationView;
import net.sf.anathema.character.main.library.util.CssSkinner;
import net.sf.anathema.character.main.view.SubViewFactory;
import net.sf.anathema.platform.fx.Stylesheet;

@RegisteredCharacterView(GroupedFavorableTraitConfigurationView.class)
public class GroupedFavorableTraitViewFactory implements SubViewFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T create() {
    FxGroupedTraitConfigurationView fxView = new FxGroupedTraitConfigurationView();
    return (T) fxView;
  }
}