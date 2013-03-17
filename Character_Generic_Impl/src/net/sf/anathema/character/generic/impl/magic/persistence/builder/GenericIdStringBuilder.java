package net.sf.anathema.character.generic.impl.magic.persistence.builder;

import net.sf.anathema.character.generic.impl.magic.persistence.IGenericsBuilder;
import net.sf.anathema.character.generic.magic.charms.CharmException;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.lib.util.Identified;
import org.dom4j.Element;

public class GenericIdStringBuilder extends IdStringBuilder implements IIdStringBuilder, IGenericsBuilder {

  private Identified type;

  @Override
  public String build(Element element) throws CharmException {
    if (type == null) {
      throw new IllegalStateException("Type not set."); //$NON-NLS-1$
    }
    return super.build(element) + "." + type.getId(); //$NON-NLS-1$
  }

  @Override
  public void setType(ITraitType type) {
    this.type = type;
  }
}