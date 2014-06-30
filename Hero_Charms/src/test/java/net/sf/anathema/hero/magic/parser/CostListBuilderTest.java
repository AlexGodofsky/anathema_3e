package net.sf.anathema.hero.magic.parser;

import net.sf.anathema.charm.old.cost.Cost;
import net.sf.anathema.charm.old.cost.CostList;
import net.sf.anathema.charm.parser.cost.CostListBuilder;
import net.sf.anathema.lib.exception.PersistenceException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CostListBuilderTest {

  private CostListBuilder builder = new CostListBuilder();

  @Test
  public void hasNoEssenceCostForNullElement() throws Exception {
    assertCostIsZero(listFromNull().getEssenceCost());
  }

  @Test
  public void hasNoHealthCostForNullElement() throws Exception {
    assertCostIsZero(listFromNull().getHealthCost());
  }

  @Test
  public void hasNoWillpowerCostForNullElement() throws Exception {
    assertCostIsZero(listFromNull().getWillpowerCost());
  }

  @Test
  public void hasNoXpCostForNullElement() throws Exception {
    assertCostIsZero(listFromNull().getXPCost());
  }

  private CostList listFromNull() throws PersistenceException {
    return builder.buildCostList(null);
  }

  private void assertCostIsZero(Cost cost) {
    assertThat(cost.getCost(), is("0"));
  }
}