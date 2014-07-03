package net.sf.anathema.hero.spells.model;

import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.hero.spells.data.CircleType;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

import java.util.Collection;
import java.util.List;

public interface SpellsModel extends HeroModel {

  Identifier ID = new SimpleIdentifier("Spells");

  void removeSpells(List<Spell> removedSpells);

  void addSpells(List<Spell> addedSpells);

  Spell[] getLearnedSpells();

  void addChangeListener(ChangeListener listener);

  boolean isSpellAllowed(Spell spell);

  Spell getSpellById(String string);

  boolean isLearnedOnCreation(Spell spell);

  Spell[] getLearnedSpells(boolean experienced);

  void addSpells(List<Spell> addedSpells, boolean experienced);

  void removeSpells(List<Spell> removedSpells, boolean experienced);

  boolean isSpellAllowed(Spell spell, boolean experienced);

  boolean isLearnedOnCreationOrExperience(Spell spell);

  List<Spell> getAvailableSpellsInCircle(CircleType circle);

  List<Spell> getLearnedSpellsInCircles(Collection<CircleType> eligibleCircles);

  boolean canLearnSorcery();

  boolean canLearnNecromancy();

  Collection<CircleType> getNecromancyCircles();

  Collection<CircleType> getSorceryCircles();

  TraitType getFavoringTraitType();
}