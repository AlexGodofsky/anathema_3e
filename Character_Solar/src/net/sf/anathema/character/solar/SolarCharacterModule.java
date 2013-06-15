package net.sf.anathema.character.solar;

import net.sf.anathema.character.generic.backgrounds.IBackgroundTemplate;
import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.framework.additionaltemplate.IAdditionalInitializer;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.IAdditionalModelFactory;
import net.sf.anathema.character.generic.framework.additionaltemplate.persistence.IAdditionalPersisterFactory;
import net.sf.anathema.character.generic.framework.module.CharacterModule;
import net.sf.anathema.character.generic.framework.module.CharacterTypeModule;
import net.sf.anathema.character.generic.impl.backgrounds.TemplateTypeBackgroundTemplate;
import net.sf.anathema.character.generic.impl.caste.CasteCollection;
import net.sf.anathema.character.generic.template.TemplateType;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.solar.caste.SolarCaste;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawInitializer;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawModelFactory;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawPersisterFactory;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawTemplate;
import net.sf.anathema.lib.registry.IIdentificateRegistry;
import net.sf.anathema.lib.registry.IRegistry;
import net.sf.anathema.lib.util.Identifier;

@CharacterModule
public class SolarCharacterModule extends CharacterTypeModule {

  public static final ICharacterType type = new SolarCharacterType();
  @SuppressWarnings("unused")
  private static final TemplateType solarTemplateType = new TemplateType(type);
  private static final TemplateType dreamsSolarTemplateType = new TemplateType(type, new Identifier("Dreams"));
  private static final TemplateType dreamsSolarEstablished = new TemplateType(type, new Identifier("DreamsEstablished"));
  private static final TemplateType dreamsSolarInfluential = new TemplateType(type, new Identifier("DreamsInfluential"));
  private static final TemplateType dreamsSolarLegendary = new TemplateType(type, new Identifier("DreamsLegendary"));

  private static final TemplateType[] dreams = {dreamsSolarTemplateType, dreamsSolarEstablished, dreamsSolarInfluential, dreamsSolarLegendary};

  public static final String BACKGROUND_ID_ARSENAL = "SolarDreamsArsenal";
  public static final String BACKGROUND_ID_COMMAND = "SolarDreamsCommand";
  public static final String BACKGROUND_ID_CONNECTIONS = "SolarDreamsConnections";
  public static final String BACKGROUND_ID_HENCHMEN = "SolarDreamsHenchmen";
  public static final String BACKGROUND_ID_PANOPLY = "SolarDreamsPanoply";
  public static final String BACKGROUND_ID_REPUTATION = "SolarDreamsReputation";
  public static final String BACKGROUND_ID_RETAINERS = "SolarDreamsRetainers";
  public static final String BACKGROUND_ID_SALARY = "SolarDreamsSalary";
  public static final String BACKGROUND_ID_SAVANT = "SolarDreamsSavant";
  public static final String BACKGROUND_ID_SIFU = "SolarDreamsSifu";
  public static final String BACKGROUND_ID_WEALTH = "SolarDreamsWealth";

  @Override
  public void registerCommonData(ICharacterGenerics characterGenerics) {
    characterGenerics.getCasteCollectionRegistry().register(type, new CasteCollection(SolarCaste.values()));
  }

  @Override
  public void addBackgroundTemplates(ICharacterGenerics generics) {
    IIdentificateRegistry<IBackgroundTemplate> backgroundRegistry = generics.getBackgroundRegistry();
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_ARSENAL, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_COMMAND, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_CONNECTIONS, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_HENCHMEN, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_PANOPLY, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_REPUTATION, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_RETAINERS, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_SALARY, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_SAVANT, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_SIFU, dreams));
    backgroundRegistry.add(new TemplateTypeBackgroundTemplate(BACKGROUND_ID_WEALTH, dreams));
  }

  @Override
  public void addAdditionalTemplateData(ICharacterGenerics characterGenerics) {
    IRegistry<String, IAdditionalModelFactory> additionalModelFactoryRegistry = characterGenerics.getAdditionalModelFactoryRegistry();
    String templateId = SolarVirtueFlawTemplate.ID;
    additionalModelFactoryRegistry.register(templateId, new SolarVirtueFlawModelFactory());
    IRegistry<String, IAdditionalInitializer> additionalViewFactoryRegistry = characterGenerics.getAdditionalInitializerRegistry();
    additionalViewFactoryRegistry.register(templateId, new SolarVirtueFlawInitializer());
    IRegistry<String, IAdditionalPersisterFactory> persisterFactory = characterGenerics.getAdditonalPersisterFactoryRegistry();
    persisterFactory.register(templateId, new SolarVirtueFlawPersisterFactory());
  }

  @Override
  protected ICharacterType getType() {
	  return type;
  }
}
