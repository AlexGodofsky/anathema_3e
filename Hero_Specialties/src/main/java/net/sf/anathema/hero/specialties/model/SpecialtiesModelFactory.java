package net.sf.anathema.hero.specialties.model;

import net.sf.anathema.hero.abilities.model.AbilitiesModel;
import net.sf.anathema.hero.initialization.SimpleModelTreeEntry;
import net.sf.anathema.hero.model.HeroModelFactory;
import net.sf.anathema.hero.template.TemplateFactory;

@SuppressWarnings("UnusedDeclaration")
public class SpecialtiesModelFactory extends SimpleModelTreeEntry implements HeroModelFactory {

  public SpecialtiesModelFactory() {
    super(SpecialtiesModel.ID, AbilitiesModel.ID);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SpecialtiesModel create(TemplateFactory templateFactory, String templateId) {
    return new SpecialtiesModelImpl();
  }
}
