package net.sf.anathema.hero.magic.model.charms;

import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.charms.model.CharmGroup;
import net.sf.anathema.hero.dummy.DummyExaltCharacterType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CharmGroupTest {

  private static final String Default_Group_Id = "AnyId";

  @Test
  public void equalsSelf() throws Exception {
    CharmGroup group = createGroupWithCharacterType();
    assertThat(group.equals(group), is(true));
  }

  @Test
  public void doesNotEqualSimilarGroup() throws Exception {
    CharmGroup group = createGroupWithCharacterType();
    CharmGroup group2 = createGroupWithCharacterType();
    assertThat(group.equals(group2), is(false));
  }

  @Test
  public void identifiesContainedCharm() throws Exception {
    CharmGroup group = createGroupWithCharacterType(new DummyExaltCharacterType());
    Charm charm = CharmMother.createCharmForCharacterTypeFromGroup(new DummyExaltCharacterType(), Default_Group_Id);
    assertThat(group.isCharmFromGroup(charm), is(true));

  }

  private CharmGroup createGroupWithCharacterType() {
    return createGroupWithCharacterType(new DummyExaltCharacterType());
  }

  private CharmGroup createGroupWithCharacterType(DummyExaltCharacterType type) {
    return new CharmGroup(type, Default_Group_Id, new Charm[0]);
  }
}