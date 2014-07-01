package net.sf.anathema.hero.magic.parser.charms;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.magic.charm.CharmImpl;
import net.sf.anathema.charm.parser.util.ElementUtilities;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.sf.anathema.charm.parser.ICharmXMLConstants.ATTRIB_ID;
import static net.sf.anathema.charm.parser.ICharmXMLConstants.TAG_ALTERNATIVE;
import static net.sf.anathema.charm.parser.ICharmXMLConstants.TAG_ALTERNATIVES;
import static net.sf.anathema.charm.parser.ICharmXMLConstants.TAG_CHARM_REFERENCE;
import static net.sf.anathema.lib.lang.ArrayUtilities.getFirst;

public class CharmAlternativeBuilder {

  public void buildAlternatives(Document charmDoc, Charm[] charms) {
    Element charmListElement = charmDoc.getRootElement();
    readAlternatives(charmListElement, charms);
  }

  private void readAlternatives(Element charmListElement, Charm[] charms) {
    Element alternativesElement = charmListElement.element(TAG_ALTERNATIVES);
    if (alternativesElement == null) {
      return;
    }
    for (Element alternativeElement : ElementUtilities.elements(alternativesElement, TAG_ALTERNATIVE)) {
      readAlternative(alternativeElement, charms);
    }
  }

  private void readAlternative(Element alternativeElement, Charm[] existingCharms) {
    List<Element> charmReferences = ElementUtilities.elements(alternativeElement, TAG_CHARM_REFERENCE);
    Set<Charm> charms = new HashSet<>(charmReferences.size());
    for (Element charmReference : charmReferences) {
      final String charmId = charmReference.attributeValue(ATTRIB_ID);
      Charm charm = getFirst(existingCharms, candidate -> candidate.getMagicName().text.equals(charmId));
      Preconditions.checkNotNull(charm, "Charm not found " + charmId);
      charms.add(charm);
    }
    for (Charm charm : charms) {
      ((CharmImpl) charm).addAlternative(charms);
    }
  }
}