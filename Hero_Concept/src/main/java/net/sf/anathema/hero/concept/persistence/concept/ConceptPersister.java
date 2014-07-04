package net.sf.anathema.hero.concept.persistence.concept;

import net.sf.anathema.hero.application.persistence.AbstractModelJsonPersister;
import net.sf.anathema.hero.elsewhere.concept.CasteCollection;
import net.sf.anathema.hero.elsewhere.concept.CasteType;
import net.sf.anathema.hero.elsewhere.concept.HeroConcept;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.library.identifier.Identifier;

@SuppressWarnings("UnusedDeclaration")
public class ConceptPersister extends AbstractModelJsonPersister<ConceptPto, HeroConcept> {

  public ConceptPersister() {
    super("concept", ConceptPto.class);
  }

  @Override
  public Identifier getModelId() {
    return HeroConcept.ID;
  }

  @Override
  protected void loadModelFromPto(Hero hero, HeroConcept model, ConceptPto pto) {
    loadCaste(pto, model);
    model.getAge().setValue(pto.age);
  }

  private void loadCaste(ConceptPto pto, HeroConcept model) throws PersistenceException {
    if (pto.caste == null) {
      return;
    }
    CasteCollection casteCollection = model.getCasteCollection();
    CasteType casteType = casteCollection.getById(pto.caste);
    model.getCaste().setType(casteType);
  }

  @Override
  protected ConceptPto saveModelToPto(HeroConcept model) {
    ConceptPto pto = new ConceptPto();
    pto.caste = model.getCaste().getType().getId();
    pto.age = model.getAge().getValue();
    return pto;
  }
}
