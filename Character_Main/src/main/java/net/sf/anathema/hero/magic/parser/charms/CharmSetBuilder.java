package net.sf.anathema.hero.magic.parser.charms;

import net.sf.anathema.charm.parser.template.special.SpecialCharmTemplate;
import net.sf.anathema.charm.parser.util.ElementUtilities;
import net.sf.anathema.hero.framework.type.CharacterTypes;
import net.sf.anathema.hero.magic.charm.CharmImpl;
import net.sf.anathema.hero.magic.parser.charms.prerequisite.AttributePrerequisiteBuilder;
import net.sf.anathema.hero.magic.parser.charms.prerequisite.CharmPrerequisiteBuilder;
import net.sf.anathema.hero.magic.parser.charms.prerequisite.TraitPrerequisitesBuilder;
import net.sf.anathema.hero.magic.parser.charms.special.ReflectionSpecialCharmParser;
import net.sf.anathema.lib.exception.PersistenceException;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static net.sf.anathema.charm.parser.ICharmXMLConstants.TAG_CHARM;

public class CharmSetBuilder {

  private final CharmParser builder;

  public CharmSetBuilder(CharacterTypes characterTypes, ReflectionSpecialCharmParser specialCharmParser) {
    this.builder =
            new CharmParserImpl(new IdStringParserImpl(), new TraitPrerequisitesBuilder(), new AttributePrerequisiteBuilder(),
                    new CharmPrerequisiteBuilder(), specialCharmParser);
  }

  public CharmImpl[] buildCharms(Document charmDoc, List<SpecialCharmTemplate> specialCharms) throws PersistenceException {
    Collection<CharmImpl> allCharms = new HashSet<>();
    Element charmListElement = charmDoc.getRootElement();
    buildCharms(allCharms, specialCharms, charmListElement);
    return allCharms.toArray(new CharmImpl[allCharms.size()]);
  }

  private void buildCharms(Collection<CharmImpl> allCharms, List<SpecialCharmTemplate> specialCharms, Element charmListElement) throws
          PersistenceException {
    for (Element charmElementObject : ElementUtilities.elements(charmListElement, TAG_CHARM)) {
      createCharm(allCharms, specialCharms, builder, charmElementObject);
    }
  }

  private void createCharm(Collection<CharmImpl> allCharms, List<SpecialCharmTemplate> specialCharms, CharmParser currentbuilder,
                                   Element charmElement) throws PersistenceException {
    CharmImpl newCharm = currentbuilder.buildCharm(charmElement, specialCharms);
    if (allCharms.contains(newCharm)) {
      allCharms.remove(newCharm);
    }
    allCharms.add(newCharm);
  }
}