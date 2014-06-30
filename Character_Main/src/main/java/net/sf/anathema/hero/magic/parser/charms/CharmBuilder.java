package net.sf.anathema.hero.magic.parser.charms;

import net.sf.anathema.charm.old.cost.CostList;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.magic.charm.CharmException;
import net.sf.anathema.hero.magic.charm.CharmImpl;
import net.sf.anathema.charm.parser.ICharmXMLConstants;
import net.sf.anathema.hero.magic.charm.combos.IComboRestrictions;
import net.sf.anathema.charm.old.attribute.MagicAttribute;
import net.sf.anathema.charm.old.source.SourceBook;
import net.sf.anathema.hero.magic.parser.charms.prerequisite.IAttributePrerequisiteBuilder;
import net.sf.anathema.hero.magic.parser.charms.prerequisite.ICharmPrerequisiteBuilder;
import net.sf.anathema.hero.magic.parser.charms.prerequisite.ITraitPrerequisitesBuilder;
import net.sf.anathema.hero.magic.parser.charms.prerequisite.PrerequisiteListBuilder;
import net.sf.anathema.hero.magic.parser.charms.special.ReflectionSpecialCharmParser;
import net.sf.anathema.hero.magic.parser.combos.IComboRulesBuilder;
import net.sf.anathema.hero.magic.parser.dto.special.SpecialCharmDto;
import net.sf.anathema.charm.parser.cost.CostListBuilder;
import net.sf.anathema.charm.parser.cost.ICostListBuilder;
import net.sf.anathema.charm.parser.source.SourceBuilder;
import net.sf.anathema.hero.traits.model.ValuedTraitType;
import net.sf.anathema.hero.framework.type.CharacterType;
import net.sf.anathema.hero.framework.type.CharacterTypes;
import net.sf.anathema.hero.magic.charm.duration.Duration;
import net.sf.anathema.hero.magic.charm.type.ICharmTypeModel;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.charm.parser.util.ElementUtilities;
import org.dom4j.Element;

import java.util.List;

import static net.sf.anathema.charm.parser.ICharmXMLConstants.ATTRIB_EXALT;
import static net.sf.anathema.charm.parser.ICharmXMLConstants.TAG_COST;
import static net.sf.anathema.charm.parser.ICharmXMLConstants.TAG_DURATION;
import static net.sf.anathema.charm.parser.ICharmXMLConstants.TAG_PREREQUISITE_LIST;

public class CharmBuilder implements ICharmBuilder {

  private final CharmTypeBuilder charmTypeBuilder = new CharmTypeBuilder();
  private final ICostListBuilder costListBuilder = new CostListBuilder();
  private final DurationBuilder durationBuilder = new DurationBuilder();
  private final GroupStringBuilder groupBuilder = new GroupStringBuilder();
  private final SourceBuilder sourceBuilder = new SourceBuilder();
  private final CharmAttributeBuilder attributeBuilder = new CharmAttributeBuilder();
  private final ReflectionSpecialCharmParser specialCharmParser;
  private final IIdStringBuilder idBuilder;
  private final ITraitPrerequisitesBuilder traitsBuilder;
  private final IAttributePrerequisiteBuilder attributeRequirementsBuilder;
  private final IComboRulesBuilder comboBuilder;
  private final ICharmPrerequisiteBuilder charmPrerequisiteBuilder;
  private final CharacterTypes characterTypes;

  public CharmBuilder(IIdStringBuilder idBuilder, ITraitPrerequisitesBuilder traitsBuilder, IAttributePrerequisiteBuilder attributeRequirementsBuilder,
                      IComboRulesBuilder comboBuilder, ICharmPrerequisiteBuilder charmPrerequisiteBuilder, CharacterTypes characterTypes,
                      ReflectionSpecialCharmParser specialCharmParser) {
    this.idBuilder = idBuilder;
    this.traitsBuilder = traitsBuilder;
    this.attributeRequirementsBuilder = attributeRequirementsBuilder;
    this.comboBuilder = comboBuilder;
    this.charmPrerequisiteBuilder = charmPrerequisiteBuilder;
    this.characterTypes = characterTypes;
    this.specialCharmParser = specialCharmParser;
  }

  @Override
  public CharmImpl buildCharm(Element charmElement, List<SpecialCharmDto> specialCharms) throws PersistenceException {
    String id = idBuilder.build(charmElement);
    try {
      CharacterType characterType = getCharacterType(charmElement);
      CostList temporaryCost;
      try {
        temporaryCost = costListBuilder.buildCostList(charmElement.element(TAG_COST));
      } catch (PersistenceException e) {
        throw new CharmException("Error building costlist for charm " + id, e);
      }
      IComboRestrictions comboRules = comboBuilder.buildComboRules(charmElement);
      Duration duration = buildDuration(charmElement);
      ICharmTypeModel charmTypeModel = charmTypeBuilder.build(charmElement);
      SourceBook[] sources = sourceBuilder.buildSourceList(charmElement);
      CharmPrerequisiteList prerequisiteList = getPrerequisites(charmElement);
      ValuedTraitType[] prerequisites = prerequisiteList.getTraitPrerequisites();
      ValuedTraitType primaryPrerequisite = prerequisites.length != 0 ? prerequisites[0] : null;
      String group = groupBuilder.build(charmElement, primaryPrerequisite);
      CharmImpl charm =
              new CharmImpl(characterType, id, group, isBuildingGenericCharms(), prerequisiteList, temporaryCost, comboRules, duration, charmTypeModel,
                      sources);
      for (MagicAttribute attribute : attributeBuilder.buildCharmAttributes(charmElement, primaryPrerequisite)) {
        charm.addMagicAttribute(attribute);
      }
      loadSpecialLearning(charmElement, charm);

      SpecialCharmDto dto = specialCharmParser.readCharmDto(charmElement, id);
      if (dto.isSpecial()) {
        specialCharms.add(dto);
      }
      return charm;
    } catch (PersistenceException e) {
      throw new PersistenceException("Parsing error for Charm " + id, e);
    }
  }

  private Duration buildDuration(Element charmElement) throws CharmException {
    Duration duration;
    try {
      duration = durationBuilder.buildDuration(charmElement.element(TAG_DURATION));
    } catch (PersistenceException e) {
      throw new CharmException("Error in Charm duration.", e);
    }
    return duration;
  }

  private CharmPrerequisiteList getPrerequisites(Element charmElement) throws CharmException {
    try {
      Element prerequisiteListElement = ElementUtilities.getRequiredElement(charmElement, TAG_PREREQUISITE_LIST);
      return new PrerequisiteListBuilder(traitsBuilder, attributeRequirementsBuilder, charmPrerequisiteBuilder)
              .buildPrerequisiteList(prerequisiteListElement);
    } catch (PersistenceException e) {
      throw new CharmException("Error in Charm prerequisites.", e);
    }
  }

  private CharacterType getCharacterType(Element charmElement) throws CharmException {
    String typeAttribute = charmElement.attributeValue(ATTRIB_EXALT);
    CharacterType characterType;
    try {
      characterType = characterTypes.findById(typeAttribute);
    } catch (IllegalArgumentException e) {
      throw new CharmException("No chararacter type given.", e);
    }
    return characterType;
  }

  private void loadSpecialLearning(Element charmElement, CharmImpl charm) {
    for (MagicAttribute attribute : charm.getAttributes()) {
      if (attribute.getId().startsWith(Charm.FAVORED_CASTE_PREFIX)) {
        String casteId = attribute.getId().substring(Charm.FAVORED_CASTE_PREFIX.length());
        charm.addFavoredCasteId(casteId);
      }
    }
    Element learningElement = charmElement.element(ICharmXMLConstants.TAG_LEARNING);
    if (learningElement == null) {
      return;
    }
    for (Element favoredElement : ElementUtilities.elements(learningElement, ICharmXMLConstants.ATTRB_FAVORED)) {
      String casteId = favoredElement.attributeValue(ICharmXMLConstants.TAG_CASTE);
      charm.addFavoredCasteId(casteId);
    }
  }

  protected boolean isBuildingGenericCharms() {
    return false;
  }
}