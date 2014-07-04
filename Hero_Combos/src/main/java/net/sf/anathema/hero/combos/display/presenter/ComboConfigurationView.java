package net.sf.anathema.hero.combos.display.presenter;

import net.sf.anathema.hero.charms.display.magic.MagicLearnProperties;
import net.sf.anathema.hero.charms.display.magic.MagicLearnView;
import net.sf.anathema.library.text.ITextView;

public interface ComboConfigurationView {

  void addComboEditor(ComboViewProperties properties);

  MagicLearnView addMagicLearnView(MagicLearnProperties viewProperties);

  ComboContainer addComboContainer();

  ITextView addComboNameView(String viewTitle);

  ITextView addComboDescriptionView(String viewTitle);
}