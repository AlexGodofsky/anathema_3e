package net.sf.anathema.hero.specialties.advance;

import net.sf.anathema.hero.abilities.model.AbilitiesModel;
import net.sf.anathema.hero.abilities.template.AbilitiesTemplate;
import net.sf.anathema.hero.abilities.template.AbilitiesTemplateLoader;
import net.sf.anathema.hero.attributes.model.AttributeModel;
import net.sf.anathema.hero.initialization.SimpleModelTreeEntry;
import net.sf.anathema.hero.model.HeroModelFactory;
import net.sf.anathema.hero.points.PointsModel;
import net.sf.anathema.hero.specialties.template.SpecialtyPointsTemplate;
import net.sf.anathema.hero.specialties.template.SpecialtyPointsTemplateLoader;
import net.sf.anathema.hero.spiritual.template.SpiritualTraitsTemplateLoader;
import net.sf.anathema.hero.template.TemplateFactory;

@SuppressWarnings("UnusedDeclaration")
public class SpecialtiesPointsModelFactory extends SimpleModelTreeEntry implements HeroModelFactory {

  public SpecialtiesPointsModelFactory() {
    super(SpecialtiesPointsModel.ID, AbilitiesModel.ID, AttributeModel.ID, PointsModel.ID);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SpecialtiesPointsModel create(TemplateFactory templateFactory, String templateId) {
    SpecialtyPointsTemplate template = SpecialtyPointsTemplateLoader.loadTemplate(templateFactory, templateId);
    return new SpecialtiesPointsModel(template);
  }
}
