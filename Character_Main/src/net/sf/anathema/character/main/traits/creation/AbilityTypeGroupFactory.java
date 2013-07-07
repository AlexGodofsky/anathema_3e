package net.sf.anathema.character.main.traits.creation;

import net.sf.anathema.character.generic.caste.ICasteCollection;
import net.sf.anathema.character.generic.template.abilities.AbilityGroupType;
import net.sf.anathema.lib.util.Identifier;

public class AbilityTypeGroupFactory extends AbstractTraitTypeGroupFactory {

  @Override
  protected Identifier getGroupIdentifier(ICasteCollection casteCollection, String groupId) {
    return casteCollection.containsCasteType(groupId) ? casteCollection.getById(groupId) : AbilityGroupType.valueOf(groupId);
  }
}