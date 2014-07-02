package net.sf.anathema.hero.magic.charm.martial;

import net.sf.anathema.charm.data.reference.CategoryReference;
import net.sf.anathema.hero.magic.basic.Magic;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.traits.model.types.AbilityType;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

import java.text.MessageFormat;

import static net.sf.anathema.charm.old.attribute.CharmAttributeList.FORM_ATTRIBUTE;

public class MartialArtsUtilities {

  public static final SimpleIdentifier MARTIAL_ARTS = new SimpleIdentifier(AbilityType.MartialArts.name());

  public static boolean isMartialArts(Magic charm) {
    return charm.hasAttribute(MARTIAL_ARTS);
  }

  public static boolean isFormMagic(Magic charm) {
    return charm.hasAttribute(FORM_ATTRIBUTE);
  }

  public static MartialArtsLevel getLevel(Magic charm) {
    if (!isMartialArts(charm)) {
      return null;
    }
    for (MartialArtsLevel level : MartialArtsLevel.values()) {
      if (charm.hasAttribute(new SimpleIdentifier(level.name()))) {
        return level;
      }
    }
    String pattern = "Martial Arts Charm without level: {0}. Please ensure it has a Martial Arts level as a 'charmAttribute'.";
    String message = MessageFormat.format(pattern, charm.getName().text);
    throw new IllegalStateException(message);
  }

  public static boolean hasLevel(MartialArtsLevel level, Charm charm) {
    return charm.hasAttribute(new SimpleIdentifier(level.name()));
  }

  public static CategoryReference getCategory(Identifier identifier) {
    return new CategoryReference(identifier.getId());
  }
}