package net.sf.anathema.hero.points.display.overview;

public class WeightedCategory implements Comparable<WeightedCategory> {

  private int weight;
  private String category;

  public WeightedCategory(int weight, String category) {
    this.weight = weight;
    this.category = category;
  }

  @Override
  public int compareTo(WeightedCategory o) {
    return weight - o.weight;
  }

  public String getId() {
    return category;
  }
}
