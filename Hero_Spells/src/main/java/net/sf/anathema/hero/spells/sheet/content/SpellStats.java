package net.sf.anathema.hero.spells.sheet.content;

import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.hero.charms.display.tooltip.IMagicSourceStringBuilder;
import net.sf.anathema.hero.charms.display.tooltip.source.MagicSourceContributor;
import net.sf.anathema.hero.charms.sheet.content.IMagicStats;
import net.sf.anathema.hero.charms.sheet.content.stats.AbstractCharmStats;
import net.sf.anathema.hero.charms.sheet.content.stats.AbstractMagicStats;
import net.sf.anathema.hero.spells.data.Spell;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static net.sf.anathema.lib.lang.ArrayUtilities.transform;

public class SpellStats extends AbstractMagicStats<Spell> {

  public SpellStats(Spell spell) {
    super(spell);
  }

  @Override
  public String getGroupName(Resources resources) {
    return resources.getString("Sheet.Magic.Group.Sorcery");
  }

  @Override
  public String getType(Resources resources) {
    return resources.getString(getMagic().getCircleType().getId());
  }

  @Override
  public String getDurationString(Resources resources) {
    return "-";
  }

  @Override
  public String getSourceString(Resources resources) {
    IMagicSourceStringBuilder<Spell> stringBuilder = new MagicSourceContributor<>(resources);
    return stringBuilder.createShortSourceString(getMagic());
  }

  protected String[] getDetailKeys() {
    String target = getMagic().getTarget();
    if (target != null) {
      return new String[]{"Spells.Target." + target};
    }
    return new String[0];
  }

  @Override
  public String[] getDetailStrings(final Resources resources) {
    return transform(getDetailKeys(), String.class, resources::getString);
  }

  @Override
  public String getNameString(Resources resources) {
    return resources.getString(getMagic().getName().text);
  }

  @SuppressWarnings("NullableProblems")
  @Override
  public int compareTo(IMagicStats stats) {
    if (stats instanceof AbstractCharmStats) {
      return 1;
    }
    SpellStats spell = (SpellStats) stats;
    int r = getMagic().getCircleType().compareTo(spell.getMagic().getCircleType());
    if (r == 0) {
      r = getMagic().getName().text.compareTo(spell.getMagic().getName().text);
    }
    return r;
  }

  @SuppressWarnings("SimplifiableIfStatement")
  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    return compareTo((SpellStats) obj) == 0;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(getMagic().getCircleType()).append(getMagic().getName().text).build();
  }
}
