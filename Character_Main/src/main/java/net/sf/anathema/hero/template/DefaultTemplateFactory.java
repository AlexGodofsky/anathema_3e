package net.sf.anathema.hero.template;

import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.library.identifier.Identifier;

public class DefaultTemplateFactory implements TemplateFactory {

  private final HeroModelTemplateCache templateCache;

  public DefaultTemplateFactory(HeroEnvironment generics) {
    this.templateCache = generics.getDataSet(HeroModelTemplateCache.class);
  }

  public <T> T loadModelTemplate(Identifier templateId, TemplateLoader<T> loader) {
    return templateCache.loadTemplate(templateId, loader);
  }
}
