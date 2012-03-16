package net.sf.anathema.character.abyssal;

import net.sf.anathema.character.abyssal.additional.AdditionalAbyssalRules;
import net.sf.anathema.character.abyssal.additional.AdditionalLoyalAbyssalRules;
import net.sf.anathema.character.abyssal.caste.AbyssalCaste;
import net.sf.anathema.character.abyssal.template.LoyalAbyssalTemplate;
import net.sf.anathema.character.abyssal.template.RenegadeAbyssalTemplate;
import net.sf.anathema.character.generic.backgrounds.IBackgroundTemplate;
import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.framework.module.CharacterModule;
import net.sf.anathema.character.generic.framework.module.NullObjectCharacterModuleAdapter;
import net.sf.anathema.character.generic.impl.backgrounds.CharacterTypeBackgroundTemplate;
import net.sf.anathema.character.generic.impl.backgrounds.TemplateTypeBackgroundTemplate;
import net.sf.anathema.character.generic.impl.caste.CasteCollection;
import net.sf.anathema.character.generic.impl.magic.persistence.CharmCache;
import net.sf.anathema.character.generic.impl.magic.persistence.ICharmCache;
import net.sf.anathema.character.generic.template.ICharacterTemplate;
import net.sf.anathema.character.generic.template.ITemplateRegistry;
import net.sf.anathema.character.generic.traits.LowerableState;
import net.sf.anathema.lib.registry.IIdentificateRegistry;

import static net.sf.anathema.character.generic.type.CharacterType.ABYSSAL;

@CharacterModule
public class AbyssalCharacterModule extends NullObjectCharacterModuleAdapter {
  public static final String BACKGROUND_ID_ABYSSAL_COMMAND = "AbyssalCommand"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_LIEGE = "Liege"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_NECROMANCY = "Necromancy"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_SPIES = "Spies"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_UNDERWORLD_MANSE = "UnderworldManse"; //$NON-NLS-1$
  public static final String BACKGROUND_ID_WHISPERS = "Whispers"; //$NON-NLS-1$
  private final AdditionalLoyalAbyssalRules additionalLoyalAbyssalRules = new AdditionalLoyalAbyssalRules();
  private final AdditionalAbyssalRules additionalRenegadeAbyssalRules = new AdditionalAbyssalRules();

  @Override
  public void registerCommonData(ICharacterGenerics characterGenerics) {
    characterGenerics.getCasteCollectionRegistry().register(ABYSSAL, new CasteCollection(AbyssalCaste.values()));
  }

  @Override
  public void addCharacterTemplates(ICharacterGenerics characterGenerics) {
    ICharmCache charmProvider = CharmCache.getInstance();
    ITemplateRegistry templateRegistry = characterGenerics.getTemplateRegistry();
    initTemplate(templateRegistry, new LoyalAbyssalTemplate(charmProvider, additionalLoyalAbyssalRules));
    initTemplate(templateRegistry, new RenegadeAbyssalTemplate(charmProvider, additionalRenegadeAbyssalRules));
  }

  private void initTemplate(ITemplateRegistry templateRegistry, ICharacterTemplate template) {
    templateRegistry.register(template);
  }

  @Override
  public void addBackgroundTemplates(ICharacterGenerics generics) {
    IIdentificateRegistry<IBackgroundTemplate> backgroundRegistry = generics.getBackgroundRegistry();
    backgroundRegistry.add(new CharacterTypeBackgroundTemplate(BACKGROUND_ID_ABYSSAL_COMMAND, ABYSSAL));
    IBackgroundTemplate backgroundTemplate = new TemplateTypeBackgroundTemplate(BACKGROUND_ID_LIEGE, LoyalAbyssalTemplate.TEMPLATE_TYPE);
    backgroundRegistry.add(backgroundTemplate);
    additionalLoyalAbyssalRules.addLiegeRules(backgroundTemplate);
    IBackgroundTemplate necromancyBackground = new CharacterTypeBackgroundTemplate(BACKGROUND_ID_NECROMANCY, ABYSSAL, LowerableState.Immutable);
    backgroundRegistry.add(necromancyBackground);
    additionalLoyalAbyssalRules.addNecromancyRules(necromancyBackground);
    additionalRenegadeAbyssalRules.addNecromancyRules(necromancyBackground);
    backgroundRegistry.add(new CharacterTypeBackgroundTemplate(BACKGROUND_ID_SPIES, ABYSSAL));
    backgroundRegistry.add(new CharacterTypeBackgroundTemplate(BACKGROUND_ID_UNDERWORLD_MANSE, ABYSSAL));
    backgroundRegistry.add(new CharacterTypeBackgroundTemplate(BACKGROUND_ID_WHISPERS, ABYSSAL));
  }
}