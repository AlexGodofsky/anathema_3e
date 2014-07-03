package net.sf.anathema.hero.combos;

import net.sf.anathema.hero.dummy.DummyCharm;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.charm.data.CharmType;
import net.sf.anathema.hero.combos.model.ComboRules;
import net.sf.anathema.hero.combos.model.rules.ExtraActionCharmComboRules;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExtraActionCharmComboRulesTest extends AbstractComboRulesTestCase {

  private ComboRules rules = new ExtraActionCharmComboRules();

  @Test
  public void testCharmComboTwoExtraAction() throws Exception {
    Charm charm1 = new DummyCharm("Instant", CharmType.ExtraAction);
    Charm charm2 = new DummyCharm("Instant", CharmType.ExtraAction);
    assertFalse(rules.isComboLegal(charm1, charm2));
  }

  @Test
  public void testCharmComboExtraActionReflexive() throws Exception {
    Charm charm1 = new DummyCharm("Instant", CharmType.ExtraAction);
    Charm charm2 = new DummyCharm("Instant", CharmType.Reflexive);
    assertTrue(rules.isComboLegal(charm1, charm2));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSupplementalOfSameAbility() throws Exception {
    assertTrue(comboSameAbilityCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSupplementalOfDifferentAbility() throws Exception {
    assertFalse(comboDifferentAbilityCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSupplementalOfDifferentAttribute() throws Exception {
    assertTrue(comboDifferentAttributeCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboAbilityExtraActionCharmCombosWithAttributeSupplementalAllowed() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(true);
    assertTrue(comboAbilityAttributeCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboAbilityExtraActionCharmCombosWithAttributeSupplementalForbidden() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(false);
    assertFalse(comboAbilityAttributeCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboAbilityExtraActionCharmCombosWithAttributeSimpleAllowed() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(true);
    assertTrue(comboAbilityAttributeCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboAbilityExtraActionCharmCombosWithAttributeSimpleForbidden() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(false);
    assertFalse(comboAbilityAttributeCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSimpleOfSameAbility() throws Exception {
    assertTrue(comboSameAbilityCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSimpleOfDifferentAbility() throws Exception {
    assertFalse(comboDifferentAbilityCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSimpleOfDifferentAttribute() throws Exception {
    assertTrue(comboDifferentAttributeCharms(CharmType.ExtraAction, CharmType.Simple));
  }
}