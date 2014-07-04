package net.sf.anathema.hero.languages.model;

import net.sf.anathema.hero.abilities.model.AbilitiesModel;
import net.sf.anathema.hero.concept.HeroConcept;
import net.sf.anathema.hero.environment.template.TemplateFactory;
import net.sf.anathema.hero.experience.ExperienceModel;
import net.sf.anathema.hero.initialization.SimpleModelTreeEntry;
import net.sf.anathema.hero.model.HeroModelFactory;
import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModel;
import net.sf.anathema.hero.traits.model.TraitModel;

@SuppressWarnings("UnusedDeclaration")
public class LanguagesModelFactory extends SimpleModelTreeEntry implements HeroModelFactory {

  public LanguagesModelFactory() {
    super(LanguagesModel.ID, AbilitiesModel.ID, SpiritualTraitModel.ID, TraitModel.ID, ExperienceModel.ID, HeroConcept.ID);
  }

  @SuppressWarnings("unchecked")
  @Override
  public LanguagesModel create(TemplateFactory templateFactory, String templateId) {
    return new LanguagesModelImpl();
  }
}
