package net.sf.anathema.hero.charms.display.node;

import java.util.Collection;
import java.util.Map;

import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.magic.charm.prerequisite.IndirectCharmPrerequisite;
import net.sf.anathema.graph.nodes.IIdentifiedRegularNode;
import net.sf.anathema.graph.nodes.NodeFactory;

public class CharmNodeBuilder {

  public static void buildNodes(Collection<Charm> groupCharms, Map<String, IIdentifiedRegularNode> charmNodesById) {
    for (Charm charm : groupCharms) {
      IIdentifiedRegularNode node = NodeFactory.createChildlessNode(charm.getName().text);
      charmNodesById.put(charm.getName().text, node);
    }
    for (Charm charm : groupCharms) {
      for (Charm parentCharm : charm.getRenderingPrerequisiteCharms()) {
        if (!groupCharms.contains(parentCharm)) {
          IIdentifiedRegularNode parentNode = charmNodesById.get(parentCharm.getName().text);
          if (parentNode == null) {
            parentNode = NodeFactory.createChildlessNode(parentCharm.getName().text);
            parentNode.setLowerToChildren(true);
            charmNodesById.put(parentCharm.getName().text, parentNode);
          } else {
            parentNode.setLowerToChildren(false);
          }
        }
      }
    }
    for (Charm charm : groupCharms) {
      for (IndirectCharmPrerequisite prerequisite : charm.getPrerequisitesOfType(IndirectCharmPrerequisite.class)) {
        String label = prerequisite.getStringLabel();
        IIdentifiedRegularNode parentNode = NodeFactory.createChildlessNode(label);
        charmNodesById.put(label, parentNode);
      }
    }
  }
}