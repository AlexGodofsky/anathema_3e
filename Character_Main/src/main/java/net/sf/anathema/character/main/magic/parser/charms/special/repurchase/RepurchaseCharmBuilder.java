package net.sf.anathema.character.main.magic.parser.charms.special.repurchase;

import net.sf.anathema.character.main.magic.charm.special.CharmTier;
import net.sf.anathema.character.main.magic.charm.special.ISpecialCharm;
import net.sf.anathema.character.main.magic.charm.special.StaticMultiLearnableCharm;
import net.sf.anathema.character.main.magic.charm.special.TieredMultiLearnableCharm;
import net.sf.anathema.character.main.magic.charm.special.TraitCharmTier;
import net.sf.anathema.character.main.magic.charm.special.TraitDependentMultiLearnableCharm;
import net.sf.anathema.character.main.magic.parser.charms.TraitTypeFinder;
import net.sf.anathema.character.main.magic.parser.charms.special.RegisteredSpecialCharmBuilder;
import net.sf.anathema.character.main.magic.parser.charms.special.SpecialCharmBuilder;
import net.sf.anathema.character.main.magic.parser.dto.special.RepurchaseDto;
import net.sf.anathema.character.main.magic.parser.dto.special.RequirementDto;
import net.sf.anathema.character.main.magic.parser.dto.special.SpecialCharmDto;
import net.sf.anathema.character.main.magic.parser.dto.special.TierDto;
import net.sf.anathema.character.main.magic.parser.dto.special.TierRepurchaseDto;
import net.sf.anathema.character.main.magic.parser.dto.special.TraitRepurchaseDto;
import net.sf.anathema.character.main.traits.TraitType;
import net.sf.anathema.character.main.traits.types.ValuedTraitType;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

@RegisteredSpecialCharmBuilder
public class RepurchaseCharmBuilder implements SpecialCharmBuilder {

  private static final String TAG_REPURCHASES = "repurchases";

  private final TraitTypeFinder traitTypeFinder = new TraitTypeFinder();

  @Override
  public ISpecialCharm readCharm(Element charmElement, String id) {
    SpecialCharmDto overallDto = new SpecialCharmDto();
    overallDto.charmId = id;
    new RepurchaseParser().parse(charmElement, overallDto);
    RepurchaseDto repurchaseDto = overallDto.repurchase;
    if (repurchaseDto.traitRepurchase != null) {
      return createTraitMultiLearnable(id, repurchaseDto.traitRepurchase);
    }
    if (repurchaseDto.staticRepurchase != null) {
      return new StaticMultiLearnableCharm(id, repurchaseDto.staticRepurchase.limit);
    }
    return createTierMultiLearnable(id, repurchaseDto);
  }

  private ISpecialCharm createTierMultiLearnable(String id, RepurchaseDto repurchaseDto) {
    TierRepurchaseDto dto = repurchaseDto.tierRepurchase;
    List<CharmTier> tiers = new ArrayList<>();
    for (TierDto tierDto : dto.tiers) {
      tiers.add(createTier(tierDto));
    }
    return new TieredMultiLearnableCharm(id, tiers.toArray(new CharmTier[tiers.size()]));
  }

  private CharmTier createTier(TierDto dto) {
    TraitCharmTier traitCharmTier = new TraitCharmTier();
    for (RequirementDto requirement : dto.requirements) {
      TraitType traitType = traitTypeFinder.getTrait(requirement.traitType);
      traitCharmTier.addRequirement(new ValuedTraitType(traitType, requirement.traitValue));
    }
    return traitCharmTier;
  }

  private ISpecialCharm createTraitMultiLearnable(String id, TraitRepurchaseDto dto) {
    TraitType trait = traitTypeFinder.getTrait(dto.limitingTrait);
    int modifier = dto.modifier;
    int absoluteMax = dto.absoluteMax;
    return new TraitDependentMultiLearnableCharm(id, absoluteMax, trait, modifier);
  }

  @Override
  public boolean willReadCharm(Element charmElement) {
    return charmElement.element(TAG_REPURCHASES) != null;
  }
}