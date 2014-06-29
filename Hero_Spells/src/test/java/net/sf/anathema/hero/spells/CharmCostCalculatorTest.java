package net.sf.anathema.hero.spells;

import com.google.common.collect.ImmutableList;
import net.sf.anathema.character.magic.charm.martial.MartialArtsLevel;
import net.sf.anathema.hero.charms.advance.creation.MagicCreationData;
import net.sf.anathema.hero.traits.model.DefaultTrait;
import net.sf.anathema.hero.traits.model.FriendlyValueChangeChecker;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.ValueChangeChecker;
import net.sf.anathema.hero.traits.model.FavorableState;
import net.sf.anathema.hero.traits.model.FriendlyIncrementChecker;
import net.sf.anathema.hero.traits.model.TraitRules;
import net.sf.anathema.character.magic.spells.Spell;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.context.CreationTraitValueStrategy;
import net.sf.anathema.hero.traits.model.types.AbilityType;
import net.sf.anathema.hero.traits.model.types.OtherTraitType;
import net.sf.anathema.hero.charms.CharmHeroObjectMother;
import net.sf.anathema.hero.charms.advance.costs.CostAnalyzerImpl;
import net.sf.anathema.hero.charms.advance.creation.MagicCreationCostCalculator;
import net.sf.anathema.hero.charms.advance.creation.MagicCreationCostEvaluator;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.CharmsModelImpl;
import net.sf.anathema.hero.charms.template.advance.MagicPointsTemplate;
import net.sf.anathema.hero.charms.template.model.CharmsTemplate;
import net.sf.anathema.hero.concept.CasteType;
import net.sf.anathema.hero.dummy.DummyHero;
import net.sf.anathema.hero.dummy.magic.DummySpell;
import net.sf.anathema.hero.magic.dummy.DummyCharmsModel;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.traits.model.TraitModel;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.trait.TraitRulesImpl;
import net.sf.anathema.hero.traits.template.TraitTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CharmCostCalculatorTest {

  private static void addAbilityAndEssence(TraitModel traitModel, DummyHero hero) {
    traitModel.addTraits(createTrait(hero, OtherTraitType.Essence, new TraitTemplate()));
    for (final AbilityType traitType : AbilityType.values()) {
      traitModel.addTraits(createFavorableTrait(hero, traitType, new TraitTemplate()));
    }
  }

  public static Trait createTrait(Hero hero, TraitType traitType, TraitTemplate traitTemplate) {
    ValueChangeChecker checker = new FriendlyValueChangeChecker();
    TraitRules rules = new TraitRulesImpl(traitType, traitTemplate, hero);
    return new DefaultTrait(hero, rules, checker);
  }

  public static Trait createFavorableTrait(Hero hero, TraitType traitType, TraitTemplate traitTemplate) {
    ValueChangeChecker checker = new FriendlyValueChangeChecker();
    TraitRules rules = new TraitRulesImpl(traitType, traitTemplate, hero);
    return new DefaultTrait(hero, rules, new CasteType[0], checker, new FriendlyIncrementChecker());
  }

  private MagicCreationCostCalculator calculator;
  private DummySpellsModel spells = new DummySpellsModel();
  private DummyCharmsModel charms = new DummyCharmsModel();
  private TraitModel traitModel;

  @Before
  public void setUp() throws Exception {
    CharmsModel charmModel = new CharmsModelImpl(new CharmsTemplate());
    spells.initializeMagicModel(charmModel);
    DummyHero hero = new CharmHeroObjectMother().createModelContextWithEssence2(new CreationTraitValueStrategy());
    traitModel = TraitModelFetcher.fetch(hero);
    addAbilityAndEssence(traitModel, hero);
    hero.addModel(charmModel);
    hero.addModel(charms);
    hero.addModel(spells);
    MagicPointsTemplate template = new MagicPointsTemplate();
    template.generalCreationPoints.freePicks = 3;
    template.generalCreationPoints.costs = 5;
    template.favoredCreationPoints.freePicks = 2;
    template.favoredCreationPoints.costs = 4;
    MagicCreationCostEvaluator magicCostEvaluator = charmModel.getMagicCostEvaluator();
    MagicCreationData creationData = new MagicCreationData(template, MartialArtsLevel.Celestial);
    calculator = new MagicCreationCostCalculator(magicCostEvaluator, creationData, new CostAnalyzerImpl(hero));
  }

  @Test
  public void testNoSpellsLearned() {
    assertEquals(0, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointCost());
  }

  @Test
  public void testOneSpellLearnedOccultUnfavored() {
    spells.addSpells(Collections.<Spell>singletonList(new DummySpell()));
    calculator.calculateMagicCosts();
    assertEquals(1, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointCost());
  }

  @Test
  public void testOneSpellLearnedOccultFavored() {
    setOccultFavored();
    spells.addSpells(Collections.<Spell>singletonList(new DummySpell()));
    calculator.calculateMagicCosts();
    assertEquals(0, calculator.getGeneralCharmPicksSpent());
    assertEquals(1, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointCost());
  }

  private void setOccultFavored() {
    traitModel.getTrait(AbilityType.Occult).getFavorization().setFavorableState(FavorableState.Favored);
  }

  @Test
  public void testUnfavoredSpellsOverflowToBonus() {
    DummySpell dummySpell = new DummySpell();
    spells.addSpells(ImmutableList.<Spell>of(dummySpell, dummySpell, dummySpell, dummySpell));
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(5, calculator.getBonusPointCost());
  }

  @Test
  public void testUnfavoredSpellsOverflowToBonusAndAreReset() {
    DummySpell dummySpell = new DummySpell();
    DummySpell dummySpellToRemove = new DummySpell();
    spells.addSpells(ImmutableList.<Spell>of(dummySpell, dummySpell, dummySpell, dummySpellToRemove));
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(5, calculator.getBonusPointCost());
    spells.removeSpells(Collections.<Spell>singletonList(dummySpellToRemove), false);
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
  }

  @Test
  public void removalRemovesBonusPointCost() {
    DummySpell dummySpell = new DummySpell();
    DummySpell dummySpellToRemove = new DummySpell();
    spells.addSpells(ImmutableList.<Spell>of(dummySpell, dummySpell, dummySpell, dummySpellToRemove), false);
    spells.removeSpells(Collections.<Spell>singletonList(dummySpellToRemove), false);
    calculator.calculateMagicCosts();
    assertThat(calculator.getBonusPointCost(), is(0));
  }

  @Test
  public void testFavoredSpellsOverflowToGeneralAndBonus() {
    setOccultFavored();
    DummySpell dummySpell = new DummySpell();
    spells.addSpells(ImmutableList.<Spell>of(dummySpell, dummySpell, dummySpell, dummySpell, dummySpell, dummySpell));
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(2, calculator.getFavoredCharmPicksSpent());
    assertEquals(4, calculator.getBonusPointCost());
  }
}