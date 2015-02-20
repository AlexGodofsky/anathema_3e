package net.sf.anathema.hero.combat.social;

import net.sf.anathema.hero.combat.model.social.InvestigationSocialAttack;
import net.sf.anathema.hero.sheet.pdf.content.stats.HeroStatsModifiers;
import net.sf.anathema.hero.traits.dummy.DummyTrait;
import net.sf.anathema.hero.traits.model.TraitMap;
import org.junit.Before;
import org.junit.Test;

import static net.sf.anathema.hero.traits.model.types.AbilityType.Investigation;
import static net.sf.anathema.hero.traits.model.types.AttributeType.Charisma;
import static net.sf.anathema.hero.traits.model.types.AttributeType.Manipulation;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvestigationSocialAttackTest {

  TraitMap collection = mock(TraitMap.class);
  HeroStatsModifiers equipment = mock(HeroStatsModifiers.class);
  private InvestigationSocialAttack socialAttack = new InvestigationSocialAttack(collection);

  @Before
  public void setUp() throws Exception {
    when(collection.getTrait(Investigation)).thenReturn(new DummyTrait(Investigation, 0));
    when(collection.getTrait(Manipulation)).thenReturn(new DummyTrait(Manipulation, 0));
    when(collection.getTrait(Charisma)).thenReturn(new DummyTrait(Charisma, 0));
  }
}
