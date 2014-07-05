package net.sf.anathema.hero.charms.model.special.oxbody;

import net.sf.anathema.hero.health.model.HealthLevelType;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.DefaultTraitType;
import net.sf.anathema.hero.traits.model.rules.LimitedTrait;
import net.sf.anathema.hero.traits.model.state.IncrementChecker;
import net.sf.anathema.hero.traits.template.TraitTemplateFactory;

public class OxBodyCategory extends LimitedTrait {

  private final String id;
  private final HealthLevelType[] healthLevelTypes;

  public OxBodyCategory(Hero hero, HealthLevelType[] healthLevelTypes, String id, IncrementChecker incrementChecker) {
    super(hero, new DefaultTraitType("OxBodyTechnique"), TraitTemplateFactory.createEssenceLimitedTemplate(0), incrementChecker);
    this.healthLevelTypes = healthLevelTypes;
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public int getHealthLevelTypeCount(HealthLevelType type) {
    int count = 0;
    for (HealthLevelType categoryType : healthLevelTypes) {
      if (categoryType == type) {
        count++;
      }
    }
    return count;
  }
}