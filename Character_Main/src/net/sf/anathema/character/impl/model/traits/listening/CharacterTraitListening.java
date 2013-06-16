package net.sf.anathema.character.impl.model.traits.listening;

import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.generic.traits.types.VirtueType;
import net.sf.anathema.character.impl.model.context.CharacterListening;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.trait.favorable.FavorableState;
import net.sf.anathema.character.library.trait.favorable.IFavorableStateChangedListener;
import net.sf.anathema.character.library.trait.specialties.ISpecialtiesConfiguration;
import net.sf.anathema.character.library.trait.specialties.Specialty;
import net.sf.anathema.character.library.trait.subtrait.ISpecialtyListener;
import net.sf.anathema.character.main.traits.model.TraitMap;
import net.sf.anathema.character.model.ICharacter;

public class CharacterTraitListening {

  private final CharacterListening listening;
  private final TraitMap traitMap;
  private ICharacter character;

  public CharacterTraitListening(ICharacter character, CharacterListening listening) {
    this.character = character;
    this.traitMap = character.getTraitConfiguration();
    this.listening = listening;
  }

  public void initListening() {
    initAttributeListening();
    initAbilityListening();
    for (Trait virtue : traitMap.getTraits(VirtueType.values())) {
      listening.addTraitListening(virtue);
    }
    listening.addTraitListening(traitMap.getTrait(OtherTraitType.Willpower));
    listening.addTraitListening(traitMap.getTrait(OtherTraitType.Essence));
  }

  private void initAbilityListening() {
    ISpecialtiesConfiguration specialtyConfiguration = character.getAbilities().getSpecialtyConfiguration();
    for (Trait ability : character.getAbilities().getAll()) {
      listening.addTraitListening(ability);
      ability.getFavorization().addFavorableStateChangedListener(new IFavorableStateChangedListener() {
        @Override
        public void favorableStateChanged(FavorableState state) {
          listening.fireCharacterChanged();
        }
      });
      specialtyConfiguration.getSpecialtiesContainer(ability.getType()).addSubTraitListener(new ISpecialtyListener() {
        @Override
        public void subTraitRemoved(Specialty specialty) {
          listening.fireCharacterChanged();
        }

        @Override
        public void subTraitAdded(Specialty specialty) {
          listening.fireCharacterChanged();
        }

        @Override
        public void subTraitValueChanged() {
          listening.fireCharacterChanged();
        }
      });
    }
  }

  private void initAttributeListening() {
    for (Trait attribute : character.getAttributes().getAll()) {
      listening.addTraitListening(attribute);
    }
  }
}