package net.sf.anathema.character.generic.impl.magic.persistence.writer;

import net.sf.anathema.character.generic.magic.ICharmData;

import org.dom4j.Element;

public class SourceWriter {

  private final static String CUSTOM = "Custom"; //$NON-NLS-1$

  public void write(ICharmData charm, Element charmElement) {
    throw new UnsupportedOperationException("Sources m�ssen auf RuleBook umgestellt werden");
  }
}