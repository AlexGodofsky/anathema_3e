package net.sf.anathema.hero.spiritual.model.traits;

import net.sf.anathema.hero.spiritual.template.SpiritualTraitsTemplate;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.template.TraitTemplate;
import net.sf.anathema.hero.traits.template.TraitTemplateMap;

import java.util.HashMap;
import java.util.Map;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Essence;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Willpower;

public class SpiritualTraitTemplateMap implements TraitTemplateMap {
  private Map<TraitType, TraitTemplate> templatesByType = new HashMap<>();

  public SpiritualTraitTemplateMap(SpiritualTraitsTemplate template) {
    templatesByType.put(Essence, template.essence);
    templatesByType.put(Willpower, template.willpower);
  }

  @Override
  public TraitTemplate getTemplate(TraitType type) {
    return templatesByType.get(type);
  }
}
