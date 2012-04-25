package net.sf.anathema.character.impl.persistence.charm;

import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharmConfiguration;
import net.sf.anathema.character.generic.magic.charms.special.ISubeffect;
import net.sf.anathema.character.model.charm.special.IMultipleEffectCharmConfiguration;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.xml.ElementUtilities;

import org.dom4j.Element;

public class MultipleEffectCharmPersister implements ISpecialCharmPersister {

  private static final String TAG_SUBEFFECTS = "Subeffects"; //$NON-NLS-1$
  private static final String TAG_SUBEFFECT = "Subeffect"; //$NON-NLS-1$
  private static final String ATTRIB_ID = "id"; //$NON-NLS-1$
  private static final String ATTRIB_CREATION_LEARNED = "creationlearned"; //$NON-NLS-1$
  private static final String ATTRIB_EXPERIENCE_LEARNED = "experiencelearned"; //$NON-NLS-1$

  @Override
  public void loadConfiguration(Element specialElement, ISpecialCharmConfiguration specialCharmConfiguration)
      throws PersistenceException {
    Element subeffectsElement = specialElement.element(TAG_SUBEFFECTS);
    if (subeffectsElement == null) {
      return;
    }
    IMultipleEffectCharmConfiguration configuration = (IMultipleEffectCharmConfiguration) specialCharmConfiguration;
    for (Element element : ElementUtilities.elements(subeffectsElement, TAG_SUBEFFECT)) {
      String id = ElementUtilities.getRequiredAttrib(element, ATTRIB_ID);
      boolean creationLearned = ElementUtilities.getBooleanAttribute(element, ATTRIB_CREATION_LEARNED, false);
      boolean experienceLearned = ElementUtilities.getBooleanAttribute(element, ATTRIB_EXPERIENCE_LEARNED, false);
      ISubeffect effect = configuration.getEffectById(id);
      effect.setCreationLearned(creationLearned);
      effect.setExperienceLearned(experienceLearned);
    }
  }

  @Override
  public void saveConfiguration(Element specialElement, ISpecialCharmConfiguration specialCharmConfiguration) {
    IMultipleEffectCharmConfiguration configuration = (IMultipleEffectCharmConfiguration) specialCharmConfiguration;
    Element subeffectsElement = specialElement.addElement(TAG_SUBEFFECTS);
    for (ISubeffect effect : configuration.getEffects()) {
      Element effectElement = subeffectsElement.addElement(TAG_SUBEFFECT);
      effectElement.addAttribute(ATTRIB_ID, effect.getId());
      ElementUtilities.addAttribute(effectElement, ATTRIB_CREATION_LEARNED, effect.isCreationLearned());
      ElementUtilities.addAttribute(effectElement, ATTRIB_EXPERIENCE_LEARNED, effect.isCreationLearned()
          || effect.isLearned());
    }
  }
}