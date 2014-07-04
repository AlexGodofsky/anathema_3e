package net.sf.anathema.character.equipment.item;

import net.sf.anathema.hero.equipment.sheet.content.stats.ArtifactStats;
import net.sf.anathema.library.identifier.Identifier;

public interface MutableArtifactStats extends ArtifactStats {
  void setName(Identifier name);

  void setAttuneCost(int cost);

  void setAllowForeignAttunement(boolean value);

  void setRequireAttunement(boolean value);
}