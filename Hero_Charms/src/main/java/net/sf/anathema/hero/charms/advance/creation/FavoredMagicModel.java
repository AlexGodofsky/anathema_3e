package net.sf.anathema.hero.charms.advance.creation;

import net.sf.anathema.hero.magic.advance.creation.MagicCreationCostCalculator;
import net.sf.anathema.hero.magic.advance.creation.MagicCreationData;
import net.sf.anathema.points.display.overview.model.AbstractSpendingModel;

public class FavoredMagicModel extends AbstractSpendingModel {
  private final MagicCreationCostCalculator magicCalculator;
  private final MagicCreationData creationData;

  public FavoredMagicModel(MagicCreationCostCalculator magicCalculator, MagicCreationData creationData) {
    super("Charms", "Favored");
    this.magicCalculator = magicCalculator;
    this.creationData = creationData;
  }

  @Override
  public int getSpentBonusPoints() {
    return 0;
  }

  @Override
  public Integer getValue() {
    return magicCalculator.getFavoredCharmPicksSpent();
  }

  @Override
  public int getAllotment() {
    return creationData.getFavoredMagicPicks();
  }
}