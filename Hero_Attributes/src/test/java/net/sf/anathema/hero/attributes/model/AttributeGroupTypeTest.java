package net.sf.anathema.hero.attributes.model;

import net.sf.anathema.hero.traits.model.types.AttributeGroupType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class AttributeGroupTypeTest {

  @Test
  public void testAll() throws Exception {
    List<AttributeGroupType> allTypes = Arrays.asList(AttributeGroupType.values());
    assertEquals(3, allTypes.size());
    assertTrue(allTypes.contains(AttributeGroupType.Mental));
    assertTrue(allTypes.contains(AttributeGroupType.Social));
    assertTrue(allTypes.contains(AttributeGroupType.Physical));
  }

  @Test
  public void testGetById() throws Exception {
    AttributeGroupType[] groupTypes = AttributeGroupType.values();
    for (AttributeGroupType groupType : groupTypes) {
      assertSame(groupType, AttributeGroupType.valueOf(groupType.getId()));
    }
  }
}