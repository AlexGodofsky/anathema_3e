package net.sf.anathema.hero.spiritual.template;

import net.sf.anathema.hero.template.GenericTemplateLoader;
import net.sf.anathema.hero.template.TemplateFactory;
import net.sf.anathema.hero.template.TemplateLoader;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public class EssencePoolTemplateLoader {

  public static EssencePoolTemplate loadTemplate(TemplateFactory templateFactory, String templateName) {
    Identifier templateId = new SimpleIdentifier(templateName);
    TemplateLoader<EssencePoolTemplate> loader = new GenericTemplateLoader<>(EssencePoolTemplate.class);
    return templateFactory.loadModelTemplate(templateId, loader);
  }
}