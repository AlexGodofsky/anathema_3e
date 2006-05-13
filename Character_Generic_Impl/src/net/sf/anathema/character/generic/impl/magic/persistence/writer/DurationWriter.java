package net.sf.anathema.character.generic.impl.magic.persistence.writer;

import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.ATTRIB_DURATION;
import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.TAG_DURATION;
import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.VALUE_INSTANT;
import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.VALUE_PERMANENT;

import net.sf.anathema.character.generic.magic.ICharmData;
import net.sf.anathema.character.generic.magic.charms.DurationType;

import org.dom4j.Element;

public class DurationWriter {

  public void write(ICharmData charm, Element charmElement) {
    Element durationElement = charmElement.addElement(TAG_DURATION);
    if (charm.getDuration().getType() == DurationType.Instant) {
      durationElement.addAttribute(ATTRIB_DURATION, VALUE_INSTANT);
      return;
    }
    if (charm.getDuration().getType() == DurationType.Permanent) {
      durationElement.addAttribute(ATTRIB_DURATION, VALUE_PERMANENT);
      return;
    }
    durationElement.addAttribute(ATTRIB_DURATION, charm.getDuration().getText(null));
  }
}