package net.sf.anathema.character.generic.framework.module;

import net.sf.anathema.character.generic.backgrounds.IBackgroundTemplate;
import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.impl.backgrounds.EssenceUserBackgroundTemplate;
import net.sf.anathema.character.generic.impl.backgrounds.SimpleBackgroundTemplate;
import net.sf.anathema.character.generic.template.ITemplateRegistry;
import net.sf.anathema.initialization.reflections.Weight;
import net.sf.anathema.lib.registry.IIdentificateRegistry;

@CharacterModule
@Weight(weight = 0) //Must be the first so others can depend on it
public class BasicExaltCharacterModule extends CharacterModuleAdapter {

  public static final String BACKGROUND_ID_FACE = "Face";
  public static final String BACKGROUND_ID_MANSE = "Manse";
  public static final String BACKGROUND_ID_ARTIFACT = "Artifact";

  @Override
  public void addBackgroundTemplates(ICharacterGenerics generics) {
    IIdentificateRegistry<IBackgroundTemplate> registry = generics.getBackgroundRegistry();
    ITemplateRegistry templateRegistry = generics.getTemplateRegistry();
    registry.add(new EssenceUserBackgroundTemplate(BACKGROUND_ID_ARTIFACT, templateRegistry));
    registry.add(new EssenceUserBackgroundTemplate(BACKGROUND_ID_FACE, templateRegistry));
    registry.add(new EssenceUserBackgroundTemplate(BACKGROUND_ID_MANSE, templateRegistry));
    registry.add(new EssenceUserBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_CULT, templateRegistry));
    registry.add(new SimpleBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_ALLIES));
    registry.add(new SimpleBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_BACKING));
    registry.add(new SimpleBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_CONTACTS));
    registry.add(new SimpleBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_FAMILIAR));
    registry.add(new SimpleBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_FOLLOWERS));
    registry.add(new SimpleBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_INFLUENCE));
    registry.add(new SimpleBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_MENTOR));
    registry.add(new SimpleBackgroundTemplate(IBackgroundIds.BACKGROUND_ID_RESOURCES));
  }
}
