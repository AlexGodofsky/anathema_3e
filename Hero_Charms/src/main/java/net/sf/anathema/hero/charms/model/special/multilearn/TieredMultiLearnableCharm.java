package net.sf.anathema.hero.charms.model.special.multilearn;

import net.sf.anathema.charm.data.reference.CharmName;

public class TieredMultiLearnableCharm extends AbstractMultiLearnableCharm {

  private final CharmTier[] tiers;

  public TieredMultiLearnableCharm(CharmName charmId, CharmTier[] tiers) {
    super(charmId);
    this.tiers = tiers;
  }

  public CharmTier[] getTiers() {
    return tiers;
  }

  @Override
  public int getAbsoluteLearnLimit() {
    return tiers.length;
  }

  @Override
  public int getMaximumLearnCount(LearnRangeContext context) {
    int learnLimit = 0;
    for (CharmTier tier : tiers) {
      if (tier.isLearnable(context)) {
        learnLimit++;
      }
    }
    return learnLimit;
  }
}