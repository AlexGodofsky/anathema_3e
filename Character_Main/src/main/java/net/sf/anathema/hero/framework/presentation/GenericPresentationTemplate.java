package net.sf.anathema.hero.framework.presentation;

import net.sf.anathema.hero.template.HeroTemplate;
import net.sf.anathema.hero.template.PresentationProperties;
import net.sf.anathema.hero.template.TemplateType;
import net.sf.anathema.library.resources.RelativePath;

public class GenericPresentationTemplate implements PresentationProperties {

  private final TemplateType templateType;

  public GenericPresentationTemplate(HeroTemplate heroTemplate) {
    this(heroTemplate.getTemplateType());
  }
 
  public GenericPresentationTemplate(TemplateType templateType) {
    this.templateType = templateType;
  }

  @Override
  public RelativePath getSmallCasteIconResource(String casteId) {
    return new RelativePath("icons/" + getCharacterTypeId() + "Button" + casteId + "SecondEdition16.png");
  }

  @Override
  public RelativePath getLargeCasteIconResource(String casteId) {
    return new RelativePath("icons/" + getCharacterTypeId() + "Button" + casteId + "SecondEdition100.png");
  }

  @Override
  public String getNewActionResource() {
    return "CharacterGenerator.Templates." + getCharacterTypeId() + "." + getSubTypeId();
  }

  @Override
  public String getCasteLabelResource() {
    return getCharacterTypeId() + ".Caste.Label";
  }

  private String getCharacterTypeId() {
    return templateType.getCharacterType().getId();
  }

  private String getSubTypeId() {
    return templateType.getSubType().getId();
  }
}