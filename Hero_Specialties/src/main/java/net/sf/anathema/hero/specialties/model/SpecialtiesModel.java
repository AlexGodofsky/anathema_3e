package net.sf.anathema.hero.specialties.model;

import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;
import net.sf.anathema.library.event.ChangeListener;

public interface SpecialtiesModel extends HeroModel {

  Identifier ID = new SimpleIdentifier("Specialties");

  ISubTraitContainer getSpecialtiesContainer(TraitType traitType);

  TraitType[] getAllParentTraits();

  TraitType[] getAllEligibleParentTraits();

  void setCurrentTrait(TraitType newValue);

  void setCurrentSpecialtyName(String newSpecialtyName);

  void commitSelection();

  void clear();

  boolean isEntryComplete();

  boolean isExperienced();

  void addSelectionChangeListener(ChangeListener listener);

  TraitType getCurrentTrait();

  String getCurrentName();
}