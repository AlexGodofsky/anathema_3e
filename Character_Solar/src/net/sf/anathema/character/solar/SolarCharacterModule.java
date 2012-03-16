package net.sf.anathema.character.solar;

import net.sf.anathema.character.generic.backgrounds.IBackgroundTemplate;
import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.framework.additionaltemplate.IAdditionalViewFactory;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.IAdditionalModelFactory;
import net.sf.anathema.character.generic.framework.additionaltemplate.persistence.IAdditionalPersisterFactory;
import net.sf.anathema.character.generic.framework.module.CharacterModule;
import net.sf.anathema.character.generic.framework.module.NullObjectCharacterModuleAdapter;
import net.sf.anathema.character.generic.impl.backgrounds.EditionSpecificTemplateTypeBackgroundTemplate;
import net.sf.anathema.character.generic.impl.caste.CasteCollection;
import net.sf.anathema.character.generic.template.TemplateType;
import net.sf.anathema.character.solar.caste.SolarCaste;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawModelFactory;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawPersisterFactory;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawTemplate;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawViewFactory;
import net.sf.anathema.lib.registry.IIdentificateRegistry;
import net.sf.anathema.lib.registry.IRegistry;
import net.sf.anathema.lib.util.Identificate;

import static net.sf.anathema.character.generic.impl.rules.ExaltedEdition.SecondEdition;
import static net.sf.anathema.character.generic.type.CharacterType.SOLAR;

@CharacterModule
public class SolarCharacterModule extends NullObjectCharacterModuleAdapter {

  @SuppressWarnings("unused")
  private static final TemplateType solarTemplateType = new TemplateType(SOLAR);

  @SuppressWarnings("unused")
  private static final TemplateType solarRevisedTemplateType = new TemplateType(SOLAR, new Identificate("RevisedSolarSubtype")); //$NON-NLS-1$

  private static final TemplateType dreamsSolarTemplateType = new TemplateType(SOLAR, new Identificate("Dreams")); //$NON-NLS-1$
  private static final TemplateType dreamsSolarRevisedTemplateType = new TemplateType(SOLAR, new Identificate("DreamsRevised")); //$NON-NLS-1$
  private static final TemplateType dreamsSolarRevisedEstablished = new TemplateType(SOLAR, new Identificate("DreamsRevisedEstablished")); //$NON-NLS-1$
  private static final TemplateType dreamsSolarRevisedInfluential = new TemplateType(SOLAR, new Identificate("DreamsRevisedInfluential")); //$NON-NLS-1$
  private static final TemplateType dreamsSolarRevisedLegendary = new TemplateType(SOLAR, new Identificate("DreamsRevisedLegendary")); //$NON-NLS-1$

  private static final TemplateType[] dreams = {dreamsSolarTemplateType, dreamsSolarRevisedTemplateType, dreamsSolarRevisedEstablished, dreamsSolarRevisedInfluential, dreamsSolarRevisedLegendary};

  public static final String BACKGROUND_ID_ARSENAL = "SolarDreamsArsenal"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_COMMAND = "SolarDreamsCommand"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_CONNECTIONS = "SolarDreamsConnections"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_HENCHMEN = "SolarDreamsHenchmen"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_PANOPLY = "SolarDreamsPanoply"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_REPUTATION = "SolarDreamsReputation"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_RETAINERS = "SolarDreamsRetainers"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_SALARY = "SolarDreamsSalary"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_SAVANT = "SolarDreamsSavant"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_SIFU = "SolarDreamsSifu"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_WEALTH = "SolarDreamsWealth"; //$NON-NLS-1$

  @Override
  public void registerCommonData(ICharacterGenerics characterGenerics) {
    characterGenerics.getAdditionalTemplateParserRegistry().register(SolarVirtueFlawTemplate.ID, new SolarVirtueFlawParser());
    characterGenerics.getCasteCollectionRegistry().register(SOLAR, new CasteCollection(SolarCaste.values()));
  }

  @Override
  public void addCharacterTemplates(ICharacterGenerics characterGenerics) {
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsDawnSolar2nd.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsZenithSolar2nd.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsTwilightSolar2nd.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsNightSolar2nd.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsEclipseSolar2nd.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsDawnSolar2ndRevised.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsZenithSolar2ndRevised.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsTwilightSolar2ndRevised.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsNightSolar2ndRevised.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/legacy/DreamsEclipseSolar2ndRevised.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/Solar.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/Solar2nd.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/Solar2ndRevised.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/Solar2ndDreams.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/Solar2ndDreamsRevised.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/Solar2ndDreamsRevisedEstablished.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/Solar2ndDreamsRevisedInfluential.template"); //$NON-NLS-1$
    registerParsedTemplate(characterGenerics, "template/Solar2ndDreamsRevisedLegendary.template"); //$NON-NLS-1$
  }

  @Override
  public void addBackgroundTemplates(ICharacterGenerics generics) {
    IIdentificateRegistry<IBackgroundTemplate> backgroundRegistry = generics.getBackgroundRegistry();
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_ARSENAL, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_COMMAND, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_CONNECTIONS, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_HENCHMEN, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_PANOPLY, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_REPUTATION, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_RETAINERS, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_SALARY, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_SAVANT, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_SIFU, dreams, SecondEdition));
    backgroundRegistry.add(new EditionSpecificTemplateTypeBackgroundTemplate(BACKGROUND_ID_WEALTH, dreams, SecondEdition));
  }

  @Override
  public void addAdditionalTemplateData(ICharacterGenerics characterGenerics) {
    IRegistry<String, IAdditionalModelFactory> additionalModelFactoryRegistry = characterGenerics.getAdditionalModelFactoryRegistry();
    String templateId = SolarVirtueFlawTemplate.ID;
    additionalModelFactoryRegistry.register(templateId, new SolarVirtueFlawModelFactory());
    IRegistry<String, IAdditionalViewFactory> additionalViewFactoryRegistry = characterGenerics.getAdditionalViewFactoryRegistry();
    additionalViewFactoryRegistry.register(templateId, new SolarVirtueFlawViewFactory());
    IRegistry<String, IAdditionalPersisterFactory> persisterFactory = characterGenerics.getAdditonalPersisterFactoryRegistry();
    persisterFactory.register(templateId, new SolarVirtueFlawPersisterFactory());
  }
}
