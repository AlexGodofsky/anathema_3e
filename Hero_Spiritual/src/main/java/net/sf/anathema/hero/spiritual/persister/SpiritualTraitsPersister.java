package net.sf.anathema.hero.spiritual.persister;

import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.persistence.AbstractModelJsonPersister;
import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModel;
import net.sf.anathema.hero.traits.persistence.TraitListPto;
import net.sf.anathema.hero.traits.persistence.TraitMapPersister;
import net.sf.anathema.lib.util.Identifier;

@SuppressWarnings("UnusedDeclaration")
public class SpiritualTraitsPersister extends AbstractModelJsonPersister<TraitListPto, SpiritualTraitModel> {

  private final TraitMapPersister traitMapPersister = new TraitMapPersister(new SpiritualTraitTypeMap());

  public SpiritualTraitsPersister() {
    super("spiritualTraits", TraitListPto.class);
  }

  @Override
  public Identifier getModelId() {
    return SpiritualTraitModel.ID;
  }

  @Override
  protected void loadModelFromPto(Hero hero, SpiritualTraitModel model, TraitListPto pto) {
    traitMapPersister.loadTraitMap(model, pto);
  }

  @Override
  protected TraitListPto saveModelToPto(SpiritualTraitModel model) {
    return traitMapPersister.saveTraitMap(model);
  }

}
