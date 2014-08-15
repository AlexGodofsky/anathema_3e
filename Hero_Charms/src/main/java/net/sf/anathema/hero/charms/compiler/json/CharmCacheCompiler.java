package net.sf.anathema.hero.charms.compiler.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.charm.template.CharmListTemplate;
import net.sf.anathema.charm.template.prerequisite.CharmPrerequisiteTemplate;
import net.sf.anathema.charm.template.special.SpecialCharmListTemplate;
import net.sf.anathema.hero.charms.compiler.CharmCacheImpl;
import net.sf.anathema.hero.environment.initialization.ExtensibleDataSet;
import net.sf.anathema.hero.environment.initialization.ExtensibleDataSetCompiler;
import net.sf.anathema.hero.environment.template.TemplateLoader;
import net.sf.anathema.hero.individual.persistence.GenericTemplateLoader;
import net.sf.anathema.hero.individual.persistence.PolymorphicTypeAdapterFactoryFactory;
import net.sf.anathema.hero.individual.persistence.RuntimeTypeAdapterFactory;
import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.library.initialization.ObjectFactory;
import net.sf.anathema.library.resources.ResourceFile;

@net.sf.anathema.platform.initialization.ExtensibleDataSetCompiler
public class CharmCacheCompiler implements ExtensibleDataSetCompiler {

  private static final String Charm_File_Recognition_Pattern = ".+?\\.charms";
  private final List<ResourceFile> resourceFiles = new ArrayList<>();
  private final TemplateLoader<CharmListTemplate> charmsLoader;
  private final TemplateLoader<SpecialCharmListTemplate> specialsLoader = new GenericTemplateLoader<>(SpecialCharmListTemplate.class);
  private final ObjectFactory objectFactory;

  public CharmCacheCompiler(ObjectFactory objectFactory) {
    this.objectFactory = objectFactory;

    RuntimeTypeAdapterFactory[] factories =
    		PolymorphicTypeAdapterFactoryFactory.generateFactories(objectFactory, CharmPrerequisiteTemplate.class);
    
    charmsLoader = new GenericTemplateLoader<>(CharmListTemplate.class, factories);
  }

  @Override
  public String getName() {
    return "Charms";
  }

  @Override
  public String getRecognitionPattern() {
    return Charm_File_Recognition_Pattern;
  }

  @Override
  public void registerFile(ResourceFile resource) {
    resourceFiles.add(resource);
  }

  @Override
  public ExtensibleDataSet build() {
    CharmCacheBuilder charmsBuilder = new CharmCacheBuilder();
    SpecialCharmsBuilder specialBuilder = new SpecialCharmsBuilder(objectFactory);
    resourceFiles.forEach(resourceFile -> {
      charmsBuilder.addTemplate(loadTemplate(resourceFile, charmsLoader));
      specialBuilder.addTemplate(loadTemplate(resourceFile, specialsLoader));
    });
    CharmCacheImpl charmCache = charmsBuilder.createCache();
    specialBuilder.addToCache(charmCache);
    return charmCache;
  }

  private <T> T loadTemplate(ResourceFile resource, TemplateLoader<T> loader) {
    try (InputStream inputStream = resource.getURL().openStream()) {
      return loader.load(inputStream);
    } catch (IOException e) {
      throw new PersistenceException(e);
    }
  }
}
