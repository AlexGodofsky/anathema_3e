package net.sf.anathema.hero.charms.display.special;

import net.sf.anathema.library.view.BooleanValueView;
import net.sf.anathema.platform.tree.display.SpecialNodeView;

public interface ToggleButtonSpecialNodeView extends SpecialNodeView {
  BooleanValueView addSubeffect(String label);
}
