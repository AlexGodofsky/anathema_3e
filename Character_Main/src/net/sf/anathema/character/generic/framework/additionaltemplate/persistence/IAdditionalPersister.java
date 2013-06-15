package net.sf.anathema.character.generic.framework.additionaltemplate.persistence;

import net.sf.anathema.character.generic.additionaltemplate.IAdditionalModel;
import net.sf.anathema.lib.exception.PersistenceException;
import org.dom4j.Element;

public interface IAdditionalPersister {

  void save(Element parent, IAdditionalModel model);

  void load(Element parent, IAdditionalModel model) throws PersistenceException;
}