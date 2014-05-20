package net.sf.anathema.character.main.template;

import net.sf.anathema.character.main.template.abilities.GroupedTraitType;
import net.sf.anathema.character.main.template.creation.BonusPointCosts;
import net.sf.anathema.character.main.template.creation.ICreationPoints;
import net.sf.anathema.character.main.template.essence.IEssenceTemplate;
import net.sf.anathema.character.main.template.experience.IExperiencePointCosts;
import net.sf.anathema.character.main.template.presentation.IPresentationProperties;
import net.sf.anathema.character.main.traits.TraitType;

import java.util.List;

public interface HeroTemplate {

  ITemplateType getTemplateType();

  IPresentationProperties getPresentationProperties();

  List<ConfiguredModel> getModels();

  GroupedTraitType[] getAttributeGroups();

  IEssenceTemplate getEssenceTemplate();

  BonusPointCosts getBonusPointCosts();

  ICreationPoints getCreationPoints();

  IExperiencePointCosts getExperienceCost();

  ITraitTemplateCollection getTraitTemplateCollection();
}