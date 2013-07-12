package net.sf.anathema.hero.advance.creation.virtue;

import net.sf.anathema.character.main.template.creation.ICreationPoints;
import net.sf.anathema.hero.advance.experience.models.AbstractSpendingModel;

public class VirtueBonusModel extends AbstractSpendingModel {
  private final VirtueCostCalculator virtueCalculator;
  private final ICreationPoints creationPoints;

  public VirtueBonusModel(VirtueCostCalculator virtueCalculator, ICreationPoints creationPoints) {
    super("Spiritual", "Virtues");
    this.virtueCalculator = virtueCalculator;
    this.creationPoints = creationPoints;
  }

  @Override
  public Integer getValue() {
    return virtueCalculator.getVirtueDotsSpent();
  }

  @Override
  public int getSpentBonusPoints() {
    return virtueCalculator.getBonusPointsSpent();
  }

  @Override
  public int getAllotment() {
    return creationPoints.getVirtueCreationPoints();
  }
}