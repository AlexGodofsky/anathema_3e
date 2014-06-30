package net.sf.anathema.hero.framework.library.quality.model;

import net.sf.anathema.hero.framework.library.quality.presenter.IQuality;
import net.sf.anathema.hero.framework.library.quality.presenter.IQualityPredicate;
import net.sf.anathema.hero.framework.library.quality.presenter.IQualitySelection;
import net.sf.anathema.hero.framework.library.quality.presenter.IQualityType;
import net.sf.anathema.lib.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Quality implements IQuality, Identifier {

  private final String id;
  private final List<IQualityPredicate> prerequisites = new ArrayList<>();
  private final IQualityType type;

  public Quality(String id, IQualityType type) {
    this.id = id;
    this.type = type;
  }

  @Override
  public final String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }

  protected final List<IQualityPredicate> getPrerequisiteList() {
    return prerequisites;
  }

  @Override
  public IQualityType getType() {
    return type;
  }

  public void addCondition(IQualityPredicate prerequisite) {
    prerequisites.add(prerequisite);
  }

  @Override
  public boolean prerequisitesFulfilled(IQualitySelection<? extends IQuality>[] selectedQualities) {
    for (IQualityPredicate prerequisite : prerequisites) {
      if (!prerequisite.isFulfilled(selectedQualities)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Quality)) {
      return false;
    }
    Quality otherQuality = (Quality) obj;
    return super.equals(obj) && this.type == otherQuality.type;
  }

  @Override
  public int hashCode() {
    return super.hashCode() + type.hashCode();
  }
}