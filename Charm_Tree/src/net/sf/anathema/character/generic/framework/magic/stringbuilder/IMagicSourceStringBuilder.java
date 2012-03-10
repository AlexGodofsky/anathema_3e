package net.sf.anathema.character.generic.framework.magic.stringbuilder;

import net.sf.anathema.character.generic.magic.IMagic;

public interface IMagicSourceStringBuilder<T extends IMagic> {

  String createSourceString(T magic);

  String createShortSourceString(T charm);
}