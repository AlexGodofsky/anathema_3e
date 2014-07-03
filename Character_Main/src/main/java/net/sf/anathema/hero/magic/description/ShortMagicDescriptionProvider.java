package net.sf.anathema.hero.magic.description;

import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.magic.Magic;
import net.sf.anathema.framework.environment.Resources;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class ShortMagicDescriptionProvider implements MagicDescriptionProvider {

  private Resources resources;

  public ShortMagicDescriptionProvider(Resources resources) {
    this.resources = resources;
  }

  @Override
  public MagicDescription getCharmDescription(final Magic magic) {
    return new MagicDescription() {
      @Override
      public boolean isEmpty() {
        return getParagraphs().length == 0;
      }

      @Override
      public String[] getParagraphs() {
        String descriptionString = getDescriptionString(magic);
        return StringUtils.isEmpty(descriptionString) ? new String[0] : new String[]{descriptionString};
      }

      private String getDescriptionString(Magic magic) {
        String id = magic.getName().text;
        String genericId = id; // todo (sandra) i18n-Key for Charms create and reuse here
        String description = getDescriptionPattern(id, genericId);
        if (magic instanceof Charm) {
          String traitId = ((Charm) magic).getPrerequisites().getPrimaryTraitType().getId();
          description = MessageFormat.format(description, resources.getString(traitId));
        }
        return description;
      }

      private String getDescriptionPattern(String id, String genericId) {
        if (resources.supportsKey(id + ".Description")) {
          return resources.getString(id + ".Description");
        }
        if (resources.supportsKey(genericId + ".Description.Long")) {
          return resources.getString(genericId + ".Description.Long");
        }
        return "";
      }
    };
  }
}