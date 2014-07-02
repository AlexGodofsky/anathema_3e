package net.sf.anathema.hero.charms.display.view;

import com.google.common.base.Preconditions;
import net.sf.anathema.charm.data.reference.CharmName;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.charms.model.CharmIdMap;
import net.sf.anathema.hero.charms.display.MagicDisplayLabeler;
import net.sf.anathema.lib.logging.Logger;
import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.platform.tree.display.NodePresentationProperties;

import static java.text.MessageFormat.format;
import static net.sf.anathema.hero.charms.display.view.NodeIds.toCharmName;

public class DefaultNodePresentationProperties implements NodePresentationProperties {

  private static final Logger logger = Logger.getLogger(DefaultNodePresentationProperties.class);

  private final FunctionalNodeProperties properties;
  private final MagicDisplayLabeler charmLabeler;
  private final Resources resources;
  private final CharmIdMap map;

  public DefaultNodePresentationProperties(Resources resources, FunctionalNodeProperties properties, CharmIdMap map) {
    this.properties = properties;
    this.resources = resources;
    this.map = map;
    this.charmLabeler = new MagicDisplayLabeler(resources);
  }

  @Override
  public String getNodeText(String nodeId) {
    if (properties.isRequirementNode(nodeId)) {
      return textForRequirementNode(nodeId);
    }
    Charm charm = findNonNullCharm(toCharmName(nodeId));
    String name = getNodeName(charm);
    if (charm.isTreeRoot()) {
      return name.toUpperCase();
    }
    return name;
  }

  private String textForRequirementNode(String nodeId) {
    String requirementWithCount = nodeId.replaceFirst(FunctionalNodeProperties.REQUIREMENT + ".", "");
    String[] strings = requirementWithCount.split("\\.");
    int requirementCount = Integer.parseInt(strings[1]);
    String requirementName = resources.getString(FunctionalNodeProperties.REQUIREMENT + "." + strings[0]);
    String charmString = resources.getString(requirementCount == 1 ? "Charms.Charm.Single" : "Charms.Charm.Multiple");
    return resources.getString("Requirement.Message", requirementCount, requirementName, charmString);
  }

  private String getNodeName(Charm charm) {
    if (charmLabeler.supportsMagic(charm)) {
      return charmLabeler.getLabelForMagic(charm);
    }
    logger.warn(format("No resource key found for node {0}. It must be a requirement or a charm.", charm.getName().text));
    return resources.getString(charm.getName().text);
  }

  private Charm findNonNullCharm(CharmName charmId) {
    Charm charm = map.getCharmById(charmId);
    Preconditions.checkNotNull(charm, format("No Charm with id ''{0}'' found.", charmId));
    return charm;
  }
}