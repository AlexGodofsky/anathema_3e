package net.sf.anathema.hero.dummy.models;

import net.sf.anathema.hero.dummy.trait.DummyTrait;
import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.model.change.ChangeAnnouncer;
import net.sf.anathema.hero.spiritual.SpiritualTraitModel;
import net.sf.anathema.hero.traits.model.DefaultTraitMap;
import net.sf.anathema.hero.traits.model.rules.limitation.StaticTraitLimitation;
import net.sf.anathema.hero.traits.model.rules.limitation.TraitLimitation;
import net.sf.anathema.hero.traits.model.types.OtherTraitType;
import net.sf.anathema.lib.util.Identifier;

public class DummySpiritualTraitModel extends DefaultTraitMap implements SpiritualTraitModel {

  public DummySpiritualTraitModel() {
    addTraits(new DummyTrait(OtherTraitType.Essence, 2));
    addTraits(new DummyTrait(OtherTraitType.Willpower, 5));
  }

  @Override
  public int getEssenceCap(boolean modified) {
    return 10;
  }

  @Override
  public TraitLimitation getEssenceLimitation() {
    return new StaticTraitLimitation(10);
  }

  @Override
  public Identifier getId() {
    return ID;
  }

  @Override
  public void initialize(HeroEnvironment environment, Hero hero) {
    // nothing to do
  }

  @Override
  public void initializeListening(ChangeAnnouncer announcer) {
    // nothing to do
  }
}
