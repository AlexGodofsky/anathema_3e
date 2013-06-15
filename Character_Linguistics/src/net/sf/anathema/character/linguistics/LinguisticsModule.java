package net.sf.anathema.character.linguistics;

import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.framework.additionaltemplate.IAdditionalInitializer;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.IAdditionalModelFactory;
import net.sf.anathema.character.generic.framework.additionaltemplate.persistence.IAdditionalPersisterFactory;
import net.sf.anathema.character.generic.framework.module.CharacterModule;
import net.sf.anathema.character.generic.framework.module.CharacterModuleAdapter;
import net.sf.anathema.character.linguistics.template.LinguisticsTemplate;
import net.sf.anathema.lib.registry.IRegistry;

@CharacterModule
public class LinguisticsModule extends CharacterModuleAdapter {

  @Override
  public void addAdditionalTemplateData(ICharacterGenerics characterGenerics) {
    IRegistry<String, IAdditionalModelFactory> additionalModelFactoryRegistry = characterGenerics.getAdditionalModelFactoryRegistry();
    String templateId = LinguisticsTemplate.ID;
    additionalModelFactoryRegistry.register(templateId, new LinguisticsModelFactory());
    IRegistry<String, IAdditionalInitializer> additionalViewFactoryRegistry = characterGenerics.getAdditionalInitializerRegistry();
    additionalViewFactoryRegistry.register(templateId, new LinguisticsInitializer());
    IRegistry<String, IAdditionalPersisterFactory> persisterFactory = characterGenerics.getAdditonalPersisterFactoryRegistry();
    persisterFactory.register(templateId, new LinguisticsPersisterFactory());
    characterGenerics.getGlobalAdditionalTemplateRegistry().add(new LinguisticsTemplate());
  }
}