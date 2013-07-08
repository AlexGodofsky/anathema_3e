package net.sf.anathema.hero.magic.display.coloring;

import net.sf.anathema.character.main.magic.model.charm.ICharm;

public class SimpleCharmColorer implements CharmColorer {
  private final CharmColoring coloring;

  public SimpleCharmColorer(CharmColoring coloring) {
    this.coloring = coloring;
  }

  public void color(ICharm charm) {
    coloring.colorCharm(charm);
  }
}