package net.sf.anathema.hero.attributes.model;

import net.sf.anathema.hero.traits.model.lists.DefaultTraitTypeList;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Attributes;

public class AllAttributeTraitTypeList extends DefaultTraitTypeList {

  static AllAttributeTraitTypeList instance = new AllAttributeTraitTypeList();

  public static AllAttributeTraitTypeList getInstance() {
    return instance;
  }

  private AllAttributeTraitTypeList() {
    super(Attributes);
  }
}