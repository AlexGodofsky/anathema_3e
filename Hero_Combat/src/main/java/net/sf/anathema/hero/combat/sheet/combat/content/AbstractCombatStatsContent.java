package net.sf.anathema.hero.combat.sheet.combat.content;

import net.sf.anathema.hero.combat.model.CharacterUtilities;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.splat.CharacterType;
import net.sf.anathema.hero.sheet.pdf.content.AbstractSubBoxContent;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.library.resources.Resources;

public abstract class AbstractCombatStatsContent extends AbstractSubBoxContent {

  private Hero hero;

  protected AbstractCombatStatsContent(Resources resources, Hero hero) {
    super(resources);
    this.hero = hero;
  }

  public int getKnockdownPool() {
    return CharacterUtilities.getKnockdownPool(hero);
  }

  public int getStunningThreshold() {
    return CharacterUtilities.getStunningThreshold(getTraits());
  }

  public int getKnockdownThreshold() {
    return CharacterUtilities.getKnockdownThreshold(getTraits());
  }

  public int getStunningPool() {
    return CharacterUtilities.getStunningPool(getTraits());
  }

  public String getKnockdownLabel() {
    return getString("Sheet.Combat.Knockdown");
  }

  public String getStunningLabel() {
    return getString("Sheet.Combat.Stunning");
  }

  public String getThresholdPoolLabel() {
    return getString("Sheet.Combat.ThresholdPool");
  }

  @Override
  public String getHeaderKey() {
    return "Combat";
  }

  @Override
  public boolean hasContent() {
    return true;
  }

  protected CharacterType getCharacterType() {
    return hero.getSplat().getTemplateType().getCharacterType();
  }

  protected TraitMap getTraits() {
    return TraitModelFetcher.fetch(hero);
  }
}
