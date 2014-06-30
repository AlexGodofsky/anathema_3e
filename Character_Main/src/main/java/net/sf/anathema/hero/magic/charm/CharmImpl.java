package net.sf.anathema.hero.magic.charm;

import com.google.common.base.Preconditions;
import net.sf.anathema.charm.old.attribute.CharmAttributeList;
import net.sf.anathema.charm.old.cost.CostList;
import net.sf.anathema.hero.framework.type.CharacterType;
import net.sf.anathema.hero.magic.basic.AbstractMagic;
import net.sf.anathema.charm.old.attribute.MagicAttributeImpl;
import net.sf.anathema.charm.old.source.SourceBook;
import net.sf.anathema.hero.magic.charm.combos.IComboRestrictions;
import net.sf.anathema.hero.magic.charm.duration.Duration;
import net.sf.anathema.hero.magic.charm.prerequisite.CharmLearnPrerequisite;
import net.sf.anathema.hero.magic.charm.prerequisite.DirectCharmLearnPrerequisite;
import net.sf.anathema.hero.magic.charm.prerequisite.IndirectCharmLearnPrerequisite;
import net.sf.anathema.hero.magic.charm.prerequisite.SimpleCharmLearnPrerequisite;
import net.sf.anathema.hero.magic.charm.type.ICharmTypeModel;
import net.sf.anathema.hero.magic.parser.charms.CharmPrerequisiteList;
import net.sf.anathema.hero.concept.HeroConcept;
import net.sf.anathema.hero.concept.HeroConceptFetcher;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.traits.model.*;
import net.sf.anathema.hero.traits.model.types.OtherTraitType;
import net.sf.anathema.lib.util.SimpleIdentifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.sf.anathema.hero.traits.model.types.AbilityType.MartialArts;

public class CharmImpl extends AbstractMagic implements Charm, CharmParent {

  private final CharmPrerequisiteList prerequisisteList;

  private final CharacterType characterType;
  private final IComboRestrictions comboRules;
  private final Duration duration;
  private final String group;
  private final boolean isGeneric;

  private final SourceBook[] sources;
  private final CostList temporaryCost;

  private final List<Set<Charm>> alternatives = new ArrayList<>();
  private final List<Set<Charm>> merges = new ArrayList<>();
  private final List<CharmImpl> children = new ArrayList<>();
  private final List<CharmLearnPrerequisite> prerequisites = new ArrayList<>();
  private final Set<String> favoredCasteIds = new HashSet<>();

  private final ICharmTypeModel typeModel;

  public CharmImpl(CharacterType characterType, String id, String group, boolean isGeneric, CharmPrerequisiteList prerequisiteList,
                   CostList temporaryCost, IComboRestrictions comboRules, Duration duration, ICharmTypeModel charmTypeModel,
                   SourceBook[] sources) {
    super(id);
    Preconditions.checkNotNull(prerequisiteList);
    Preconditions.checkNotNull(characterType);
    Preconditions.checkNotNull(id);
    Preconditions.checkNotNull(group);
    Preconditions.checkNotNull(temporaryCost);
    Preconditions.checkNotNull(comboRules);
    Preconditions.checkNotNull(duration);
    Preconditions.checkNotNull(charmTypeModel.getCharmType());
    Preconditions.checkNotNull(sources);
    this.characterType = characterType;
    this.group = group;
    this.isGeneric = isGeneric;
    this.prerequisisteList = prerequisiteList;
    this.temporaryCost = temporaryCost;
    this.comboRules = comboRules;
    this.duration = duration;
    this.typeModel = charmTypeModel;
    this.sources = sources;
  }

  @Override
  public ICharmTypeModel getCharmTypeModel() {
    return typeModel;
  }

  @Override
  public CharacterType getCharacterType() {
    return characterType;
  }

  @Override
  public Duration getDuration() {
    return duration;
  }

  @Override
  public ValuedTraitType getEssence() {
    return prerequisisteList.getEssencePrerequisite();
  }

  @Override
  public ValuedTraitType[] getPrerequisites() {
    return prerequisisteList.getTraitPrerequisites();
  }

  @Override
  public SourceBook[] getSources() {
    return sources.length > 0 ? sources : null;
  }

  @Override
  public SourceBook getPrimarySource() {
    return sources.length > 0 ? sources[0] : null;
  }

  @Override
  public CostList getTemporaryCost() {
    return temporaryCost;
  }

  @Override
  public String getGroupId() {
    return group;
  }

  @Override
  public boolean isInstanceOfGenericCharm() {
    return isGeneric;
  }

  @Override
  public IComboRestrictions getComboRules() {
    return comboRules;
  }

  public void addAlternative(Set<Charm> alternative) {
    alternatives.add(alternative);
  }

  @Override
  public boolean isBlockedByAlternative(ICharmLearnArbitrator learnArbitrator) {
    for (Set<Charm> alternative : alternatives) {
      for (Charm charm : alternative) {
        boolean isThis = charm.getId().equals(getId());
        if (!isThis && learnArbitrator.isLearned(charm)) {
          return true;
        }
      }
    }
    return false;
  }

  public void addMerged(Set<Charm> merged) {
    if (!merged.isEmpty()) {
      merges.add(merged);
      if (!hasAttribute(CharmAttributeList.MERGED_ATTRIBUTE)) {
        addMagicAttribute(new MagicAttributeImpl(CharmAttributeList.MERGED_ATTRIBUTE.getId(), true));
      }
    }
  }

  @Override
  public Set<Charm> getMergedCharms() {
    Set<Charm> mergedCharms = new HashSet<>();
    for (Set<Charm> merge : merges) {
      mergedCharms.addAll(merge);
    }
    return mergedCharms;
  }

  public void extractParentCharms(Map<String, CharmImpl> charmsById) {
    prerequisites.addAll(Arrays.asList(prerequisisteList.getCharmPrerequisites()));
    for (CharmLearnPrerequisite prerequisite : prerequisites) {
      prerequisite.link(charmsById);
    }
    List<DirectCharmLearnPrerequisite> directPrerequisites = getPrerequisitesOfType(DirectCharmLearnPrerequisite.class);
    for (DirectCharmLearnPrerequisite prerequisite : directPrerequisites) {
      Charm[] charms = prerequisite.getDirectPredecessors();
      for (Charm charm : charms) {
        ((CharmParent) charm).addChild(this);
      }
    }
  }

  @Override
  public List<CharmLearnPrerequisite> getLearnPrerequisites() {
    return prerequisites;
  }

  @Override
  public void addChild(CharmImpl child) {
    children.add(child);
  }

  @Override
  public Set<Charm> getRenderingPrerequisiteCharms() {
    Set<Charm> prerequisiteCharms = new HashSet<>();
    for (DirectCharmLearnPrerequisite prerequisite : getPrerequisitesOfType(DirectCharmLearnPrerequisite.class)) {
      prerequisiteCharms.addAll(Arrays.asList(prerequisite.getDirectPredecessors()));
    }
    return prerequisiteCharms;
  }

  @Override
  public Set<Charm> getLearnPrerequisitesCharms(ICharmLearnArbitrator learnArbitrator) {
    Set<Charm> prerequisiteCharms = new HashSet<>();
    for (DirectCharmLearnPrerequisite prerequisite : getPrerequisitesOfType(DirectCharmLearnPrerequisite.class)) {
      prerequisiteCharms.addAll(Arrays.asList(prerequisite.getLearnPrerequisites(learnArbitrator)));
    }
    return prerequisiteCharms;
  }

  @Override
  public boolean isTreeRoot() {
    return getPrerequisitesOfType(DirectCharmLearnPrerequisite.class).isEmpty() &&
            getPrerequisitesOfType(IndirectCharmLearnPrerequisite.class).isEmpty();
  }

  @Override
  public Set<Charm> getLearnFollowUpCharms(ICharmLearnArbitrator learnArbitrator) {
    CompositeLearnWorker learnWorker = new CompositeLearnWorker(learnArbitrator);
    for (CharmImpl child : children) {
      child.addCharmsToForget(learnWorker);
    }
    return learnWorker.getForgottenCharms();
  }

  @Override
  public Set<Charm> getLearnChildCharms() {
    return new HashSet<>(children);
  }

  private void addCharmsToForget(ICharmLearnWorker learnWorker) {
    if (isCharmPrerequisiteListFulfilled(learnWorker)) {
      return;
    }
    learnWorker.forget(this);
    for (CharmImpl child : children) {
      child.addCharmsToForget(learnWorker);
    }
  }

  private boolean isCharmPrerequisiteListFulfilled(ICharmLearnArbitrator learnArbitrator) {
    for (CharmLearnPrerequisite prerequisite : getPrerequisitesOfType(DirectCharmLearnPrerequisite.class)) {
      if (!prerequisite.isSatisfied(learnArbitrator)) {
        return false;
      }
    }
    return true;
  }

  public void addFavoredCasteId(String casteId) {
    favoredCasteIds.add(casteId);
  }

  @Override
  public boolean isFavored(Hero hero) {
    if (isSpecialFavoredForCaste(hero)) {
      return true;
    }
    if (isFavoredMartialArts(hero)) {
      return true;
    }
    return isPrimaryTraitFavored(hero);
  }

  private boolean isPrimaryTraitFavored(Hero hero) {
    TraitModel traitModel = TraitModelFetcher.fetch(hero);
    Trait primaryTrait = traitModel.getTrait(getPrimaryTraitType());
    return primaryTrait.isCasteOrFavored();
  }

  private boolean isFavoredMartialArts(Hero hero) {
    Trait martialArts = TraitModelFetcher.fetch(hero).getTrait(MartialArts);
    boolean isMartialArts = hasAttribute(new SimpleIdentifier("MartialArts"));
    return isMartialArts && martialArts.isCasteOrFavored();
  }

  private boolean isSpecialFavoredForCaste(Hero hero) {
    HeroConcept concept = HeroConceptFetcher.fetch(hero);
    String casteId = concept.getCaste().getType().getId();
    return favoredCasteIds.contains(casteId);
  }

  @Override
  public TraitType getPrimaryTraitType() {
    return getPrerequisites().length == 0 ? OtherTraitType.Essence : getPrerequisites()[0].getType();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends CharmLearnPrerequisite> List<T> getPrerequisitesOfType(Class<T> clazz) {
    List<T> matches = new ArrayList<>();
    for (CharmLearnPrerequisite prerequisite : prerequisites) {
      if (clazz.isInstance(prerequisite)) {
        matches.add((T) prerequisite);
      }
    }
    return matches;
  }

  public void addParentCharms(Charm... parent) {
    for (Charm charm : parent) {
      prerequisites.add(new SimpleCharmLearnPrerequisite(charm));
    }
  }
}