package net.sf.anathema.hero.magic.model.charms;

import net.sf.anathema.charm.data.reference.CharmName;
import net.sf.anathema.hero.traits.model.DefaultTrait;
import net.sf.anathema.hero.traits.model.FriendlyValueChangeChecker;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.FriendlyIncrementChecker;
import net.sf.anathema.hero.traits.model.TraitRules;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.TraitValueStrategy;
import net.sf.anathema.hero.traits.model.context.CreationTraitValueStrategy;
import net.sf.anathema.hero.traits.model.types.AbilityType;
import net.sf.anathema.hero.charms.CharmHeroObjectMother;
import net.sf.anathema.hero.charms.model.special.oxbody.*;
import net.sf.anathema.hero.concept.CasteType;
import net.sf.anathema.hero.dummy.DummyCasteType;
import net.sf.anathema.hero.dummy.DummyHero;
import net.sf.anathema.hero.health.model.HealthLevelType;
import net.sf.anathema.hero.traits.model.TraitModel;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.rules.TraitRulesImpl;
import net.sf.anathema.hero.traits.template.TraitTemplate;
import net.sf.anathema.hero.traits.template.TraitTemplateFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class OxBodyTechniqueSpecialsTest {

  private Trait resistance;
  private OxBodyTechniqueSpecials specials;
  private DummyHero hero;
  private OxBodyTechniqueArbitratorImpl arbitrator;
  private TraitType[] healthTraitTypes = { AbilityType.Resistance};

  @Before
  public void setUp() throws Exception {
    TraitValueStrategy strategy = new CreationTraitValueStrategy();
    hero = new CharmHeroObjectMother().createModelContextWithEssence2(strategy);
    resistance = createResistance(hero);
    this.arbitrator = new OxBodyTechniqueArbitratorImpl(new Trait[]{resistance});
    TraitModel traitModel = TraitModelFetcher.fetch(hero);
    traitModel.addTraits(resistance);
    specials = new OxBodyTechniqueSpecialsImpl(hero, null, healthTraitTypes, arbitrator, createObtCharm());
    arbitrator.addOxBodyTechniqueConfiguration(specials);
  }

  private DefaultTrait createResistance(DummyHero hero) {
    TraitTemplate resistanceTemplate = TraitTemplateFactory.createEssenceLimitedTemplate(0);
    TraitRules resistanceRules = new TraitRulesImpl(AbilityType.Resistance, resistanceTemplate, hero);
    CasteType[] castes = {new DummyCasteType()};
    return new DefaultTrait(hero, resistanceRules, castes, new FriendlyValueChangeChecker(), new FriendlyIncrementChecker());
  }

  @Test
  public void testOxBodyDecrease() {
    OxBodyCategory[] categories = specials.getCategories();
    resistance.setCurrentValue(5);
    assertEquals(5, resistance.getCreationValue());
    categories[0].setCurrentValue(3);
    assertEquals(3, categories[0].getCreationValue());
    categories[1].setCurrentValue(2);
    assertEquals(2, categories[1].getCreationValue());
    resistance.setCurrentValue(0);
    assertEquals(0, resistance.getCreationValue());
    categories[0].setCurrentValue(0);
    assertEquals(0, categories[0].getCreationValue());
    categories[1].setCurrentValue(0);
    assertEquals(0, categories[1].getCreationValue());
  }

  @Test
  public void testTwoOxBodyTechniques() {
    OxBodyTechniqueSpecialsImpl secondConfiguration = new OxBodyTechniqueSpecialsImpl(hero, null, healthTraitTypes, arbitrator, createObtCharm());
    arbitrator.addOxBodyTechniqueConfiguration(secondConfiguration);
    resistance.setCurrentValue(2);
    specials.getCategories()[0].setCurrentValue(2);
    assertEquals(2, specials.getCategories()[0].getCreationValue());
    OxBodyCategory secondOxBodyTechnique = secondConfiguration.getCategories()[0];
    secondOxBodyTechnique.setCurrentValue(1);
    assertEquals(0, secondConfiguration.getCategories()[0].getCreationValue());
  }

  private OxBodyTechniqueCharm createObtCharm() {
    return new OxBodyTechniqueCharm(new CharmName("Abyssal.Ox-BodyTechnique"), AbilityType.Resistance, new LinkedHashMap<String, HealthLevelType[]>() {
      {
        this.put("OxBody0", new HealthLevelType[]{HealthLevelType.ZERO});
        this.put("OxBody1", new HealthLevelType[]{HealthLevelType.ONE});
      }
    });
  }
}