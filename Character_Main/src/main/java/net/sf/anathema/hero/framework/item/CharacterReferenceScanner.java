package net.sf.anathema.hero.framework.item;

import net.sf.anathema.hero.framework.perspective.model.CharacterReference;
import net.sf.anathema.hero.framework.type.CharacterType;
import net.sf.anathema.hero.template.TemplateType;
import net.sf.anathema.lib.util.Identifier;

public interface CharacterReferenceScanner {
  CharacterType getCharacterType(CharacterReference reference);

  Identifier getCasteType(CharacterReference reference);

  TemplateType getTemplateType(CharacterReference reference);
}