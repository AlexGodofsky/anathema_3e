package net.sf.anathema.hero.spells;

import net.sf.anathema.hero.charms.CharmHeroObjectMother;
import net.sf.anathema.hero.charms.advance.costs.CostAnalyzerImpl;
import net.sf.anathema.hero.charms.advance.creation.MagicCreationCostCalculator;
import net.sf.anathema.hero.charms.advance.creation.MagicCreationCostEvaluator;
import net.sf.anathema.hero.charms.advance.creation.MagicCreationData;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.CharmsModelImpl;
import net.sf.anathema.hero.charms.model.favored.CheapenedChecker;
import net.sf.anathema.hero.charms.model.learn.CharmLearner;
import net.sf.anathema.hero.charms.template.advance.MagicPointsTemplate;
import net.sf.anathema.hero.charms.template.model.CharmsTemplate;
import net.sf.anathema.hero.dummy.DummyHero;
import net.sf.anathema.hero.dummy.magic.DummySpell;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.hero.spells.data.Spells;
import net.sf.anathema.hero.spells.model.SpellsLearner;
import net.sf.anathema.hero.traits.model.context.CreationTraitValueStrategy;
import net.sf.anathema.magic.data.Magic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CharmCostCalculatorTest {

  private MagicCreationCostCalculator calculator;
  private DummySpellsModel spells = new DummySpellsModel();
  private CharmsModel charmModel = new CharmsModelImpl(new CharmsTemplate());

  @Before
  public void setUp() throws Exception {
    CharmHeroObjectMother objectMother = new CharmHeroObjectMother();
    CreationTraitValueStrategy valueStrategy = new CreationTraitValueStrategy();
    DummyHero hero = objectMother.createModelContextWithEssence2(valueStrategy);
    hero.addModel(charmModel);
    hero.addModel(spells);
    MagicPointsTemplate template = new MagicPointsTemplate();
    template.generalCreationPoints.freePicks = 3;
    template.generalCreationPoints.costs = 5;
    template.favoredCreationPoints.freePicks = 2;
    template.favoredCreationPoints.costs = 4;
    MagicCreationCostEvaluator magicCostEvaluator = new MagicCreationCostEvaluator();
    magicCostEvaluator.registerMagicLearner(new CharmLearner(charmModel));
    magicCostEvaluator.registerMagicLearner(new SpellsLearner(spells));
    MagicCreationData creationData = new MagicCreationData(template);
    calculator = new MagicCreationCostCalculator(magicCostEvaluator, creationData, new CostAnalyzerImpl(hero));
  }

  @Test
  public void testNoSpellsLearned() {
    assertEquals(0, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointCost());
  }

  @Test
  public void calculatesCategoriesForUnfavoredSpell() {
    spells.addSpells(Spells.singleSpell(new DummySpell()));
    calculator.calculateMagicCosts();
    assertEquals(1, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointCost());
  }

  @Test
  public void calculatesCategoriesForFavoredSpell() {
    setSpellsFavored();
    spells.addSpells(Spells.from(new DummySpell()));
    calculator.calculateMagicCosts();
    assertEquals(0, calculator.getGeneralCharmPicksSpent());
    assertEquals(1, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointCost());
  }

  @Test
  public void testUnfavoredSpellsOverflowToBonus() {
    DummySpell dummySpell = new DummySpell();
    spells.addSpells(Spells.from(dummySpell, dummySpell, dummySpell, dummySpell));
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(5, calculator.getBonusPointCost());
  }

  @Test
  public void testUnfavoredSpellsOverflowToBonusAndAreReset() {
    DummySpell dummySpell = new DummySpell();
    DummySpell dummySpellToRemove = new DummySpell();
    spells.addSpells(Spells.from(dummySpell, dummySpell, dummySpell, dummySpellToRemove));
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(5, calculator.getBonusPointCost());
    spells.removeSpells(Spells.from(dummySpellToRemove), false);
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
  }

  @Test
  public void removalRemovesBonusPointCost() {
    DummySpell dummySpell = new DummySpell();
    DummySpell dummySpellToRemove = new DummySpell();
    spells.addSpells(Spells.from(dummySpell, dummySpell, dummySpell, dummySpellToRemove), false);
    spells.removeSpells(Spells.from(dummySpellToRemove), false);
    calculator.calculateMagicCosts();
    assertThat(calculator.getBonusPointCost(), is(0));
  }

  @Test
  public void testFavoredSpellsOverflowToGeneralAndBonus() {
    setSpellsFavored();
    spells.addSpells(Spells.from(new DummySpell(), new DummySpell(), new DummySpell(), new DummySpell(),
            new DummySpell(), new DummySpell()));
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(2, calculator.getFavoredCharmPicksSpent());
    assertEquals(4, calculator.getBonusPointCost());
  }

  private void setSpellsFavored() {
    charmModel.addCheapenedChecker(new FavorsSpells());
  }

  private static class FavorsSpells implements CheapenedChecker {
    @Override
    public boolean supportsMagic(Magic magic) {
      return magic instanceof Spell;
    }

    @Override
    public boolean isCheapened(Magic magic) {
      return true;
    }
  }
}