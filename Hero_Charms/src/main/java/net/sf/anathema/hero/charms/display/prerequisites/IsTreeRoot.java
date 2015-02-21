package net.sf.anathema.hero.charms.display.prerequisites;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.charm.data.prerequisite.AnyOneTraitCharmPrerequisite;
import net.sf.anathema.charm.data.prerequisite.AttributeKnownCharmPrerequisite;
import net.sf.anathema.charm.data.prerequisite.EvocationTierPrerequisite;
import net.sf.anathema.charm.data.prerequisite.PrerequisiteVisitor;
import net.sf.anathema.charm.data.prerequisite.SimpleCharmPrerequisite;
import net.sf.anathema.charm.data.prerequisite.SpecificGroupCharmPrerequisite;
import net.sf.anathema.charm.data.prerequisite.TraitGroupCharmPrerequisite;

import static net.sf.anathema.charm.data.prerequisite.AcceptVisitor.acceptVisitor;

public class IsTreeRoot implements PrerequisiteVisitor {

  public static boolean isTreeRoot(Charm charm) {
    IsTreeRoot visitor = new IsTreeRoot();
    charm.getPrerequisites().forEachCharmPrerequisite(acceptVisitor(visitor));
    return visitor.isRoot;
  }

  public boolean isRoot = true;

  @Override
  public void visit(AttributeKnownCharmPrerequisite prerequisite) {
    isRoot = false;
  }

  @Override
  public void visit(EvocationTierPrerequisite prerequisite) {
    isRoot = false;
  }

  @Override
  public void visit(SimpleCharmPrerequisite prerequisite) {
    isRoot = false;
  }

  @Override
  public void visit(TraitGroupCharmPrerequisite prerequisite) {
    isRoot = false;
  }

  @Override
  public void visit(AnyOneTraitCharmPrerequisite prerequisite) {
    isRoot = false;
  }

  @Override
  public void visit(SpecificGroupCharmPrerequisite prerequisite) {
    isRoot = false;
  }
}
