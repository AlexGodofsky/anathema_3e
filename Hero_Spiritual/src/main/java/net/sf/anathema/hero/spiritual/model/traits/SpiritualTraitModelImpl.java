package net.sf.anathema.hero.spiritual.model.traits;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.hero.spiritual.template.SpiritualTraitsTemplate;
import net.sf.anathema.hero.traits.model.DefaultTraitMap;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitLimitation;
import net.sf.anathema.hero.traits.model.TraitModel;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.event.TraitValueChangedListener;
import net.sf.anathema.hero.traits.template.TraitTemplateMap;
import net.sf.anathema.library.identifier.Identifier;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Essence;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Willpower;

public class SpiritualTraitModelImpl extends DefaultTraitMap implements SpiritualTraitModel, HeroModel {

  private SpiritualTraitsTemplate template;
  private TraitModel traitModel;

  public SpiritualTraitModelImpl(SpiritualTraitsTemplate template) {
    this.template = template;
  }

  @Override
  public Identifier getId() {
    return ID;
  }

  @Override
  public void initialize(HeroEnvironment environment, Hero hero) {
    addEssence(hero);
    addWillpower(hero);
    this.traitModel = TraitModelFetcher.fetch(hero);
    getTrait(Essence).addCurrentValueListener(new EssenceLimitationListener(traitModel, hero));
    traitModel.addTraits(getAll());
  }

  @Override
  public void initializeListening(ChangeAnnouncer announcer) {
    for (Trait trait : getAll()) {
      trait.addCurrentValueListener(new TraitValueChangedListener(announcer, trait));
    }
  }

  private void addEssence(Hero hero) {
    SpiritualTraitFactory traitFactory = createTraitFactory(hero);
    addTraits(traitFactory.createTrait(Essence));
  }

  private void addWillpower(Hero hero) {
    SpiritualTraitFactory traitFactory = createTraitFactory(hero);
    addTraits(traitFactory.createTrait(Willpower));
  }

  private SpiritualTraitFactory createTraitFactory(Hero hero) {
    TraitTemplateMap map = new SpiritualTraitTemplateMap(template);
    return new SpiritualTraitFactory(hero, map);
  }

  @Override
  public int getEssenceCap(boolean modified) {
    Trait essence = getTrait(Essence);
    return modified ? essence.getModifiedMaximalValue() : essence.getUnmodifiedMaximalValue();
  }

  @Override
  public TraitLimitation getEssenceLimitation() {
    return traitModel.createLimitation(template.essence.limitation);
  }
}
