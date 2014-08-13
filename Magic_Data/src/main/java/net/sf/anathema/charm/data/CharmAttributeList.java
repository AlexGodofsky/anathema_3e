package net.sf.anathema.charm.data;

import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

public interface CharmAttributeList {

  Identifier COMBO_BASIC_ATTRIBUTE = new SimpleIdentifier("Combo-Basic");
  Identifier COMBO_OK_ATTRIBUTE = new SimpleIdentifier("Combo-OK");
  Identifier FORM_ATTRIBUTE = new SimpleIdentifier("Form");
  Identifier NO_STYLE_ATTRIBUTE = new SimpleIdentifier("NoStyle");
  Identifier NATIVE = new SimpleIdentifier("Native");
}