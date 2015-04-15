package net.sf.anathema.hero.combat;

import net.sf.anathema.hero.combat.model.CharacterUtilities;
import net.sf.anathema.hero.dummy.DummyHeroType;
import net.sf.anathema.hero.dummy.DummyMundaneHeroType;
import net.sf.anathema.hero.sheet.pdf.content.stats.HeroStatsModifiers;
import net.sf.anathema.hero.traits.dummy.DummyTrait;
import net.sf.anathema.hero.traits.model.DefaultTraitMap;
import net.sf.anathema.hero.traits.model.types.AbilityType;
import net.sf.anathema.hero.traits.model.types.AttributeType;
import org.junit.Test;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Essence;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CharacterUtilitiesTest {

  private DefaultTraitMap traitCollection = new DefaultTraitMap();
  private HeroStatsModifiers modifiers = mock(HeroStatsModifiers.class);

  @Test
  public void testMortalDodgeDvWithEssenceValueOne() throws Exception {
    traitCollection.addTraits(new DummyTrait(AbilityType.Dodge, 1));
    traitCollection.addTraits(new DummyTrait(AttributeType.Dexterity, 2));
    traitCollection.addTraits(new DummyTrait(Essence, 1));
    assertEquals(1, CharacterUtilities.getDodgeDv(new DummyMundaneHeroType(), traitCollection, modifiers));
  }

  @Test
  public void testMortalDodgeDvWithEssenceValueTwo() throws Exception {
    traitCollection.addTraits(new DummyTrait(AbilityType.Dodge, 1));
    traitCollection.addTraits(new DummyTrait(AttributeType.Dexterity, 2));
    traitCollection.addTraits(new DummyTrait(Essence, 2));
    assertEquals(2, CharacterUtilities.getDodgeDv(new DummyMundaneHeroType(), traitCollection, modifiers));
  }

  @Test
  public void testExaltDodgeWithEssenceValueOne() throws Exception {
    traitCollection.addTraits(new DummyTrait(AbilityType.Dodge, 1));
    traitCollection.addTraits(new DummyTrait(AttributeType.Dexterity, 1));
    traitCollection.addTraits(new DummyTrait(Essence, 1));
    assertEquals(1, CharacterUtilities.getDodgeDv(new DummyHeroType(), traitCollection, modifiers));
  }

  @Test
  public void testExaltDodgeWithEssenceValueTwo() throws Exception {
    traitCollection.addTraits(new DummyTrait(AbilityType.Dodge, 1));
    traitCollection.addTraits(new DummyTrait(AttributeType.Dexterity, 1));
    traitCollection.addTraits(new DummyTrait(Essence, 2));
    assertEquals(2, CharacterUtilities.getDodgeDv(new DummyHeroType(), traitCollection, modifiers));
  }

  @Test
  public void testExaltDodgeOddSum() throws Exception {
    traitCollection.addTraits(new DummyTrait(AbilityType.Dodge, 1));
    traitCollection.addTraits(new DummyTrait(AttributeType.Dexterity, 2));
    traitCollection.addTraits(new DummyTrait(Essence, 2));
    assertEquals(3, CharacterUtilities.getDodgeDv(new DummyHeroType(), traitCollection, modifiers));
  }
}