package net.sf.anathema.hero.charms.sheet.content.stats;

import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.charms.sheet.content.IMagicStats;
import net.sf.anathema.framework.environment.Resources;

public class MultipleEffectCharmStats extends AbstractCharmStats implements IMagicStats {

  private final String effect;

  public MultipleEffectCharmStats(Charm charm, String effect) {
    super(charm);
    this.effect = effect;
  }

  @Override
  public String getNameString(Resources resources) {
    String effectString = resources.getString(getMagic().getMagicName().text + ".Subeffects." + effect);
    return resources.getString(getMagic().getMagicName().text + ".PrintPattern", effectString);
  }
}
