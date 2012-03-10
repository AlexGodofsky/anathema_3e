package net.sf.anathema.character.presenter.charm;

import net.sf.anathema.character.generic.impl.rules.ExaltedEdition;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.rules.IExaltedEdition;
import net.sf.anathema.character.generic.rules.IExaltedSourceBook;
import net.sf.anathema.charmtree.filters.ICharmFilter;
import net.sf.anathema.charmtree.filters.SourceBookCharmFilterPage;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SourceBookCharmFilter implements ICharmFilter {

  private final Map<IExaltedEdition, ArrayList<IExaltedSourceBook>> allMaterial = new HashMap<IExaltedEdition, ArrayList<IExaltedSourceBook>>();
  protected final Map<IExaltedEdition, ArrayList<IExaltedSourceBook>> excludedMaterial = new HashMap<IExaltedEdition, ArrayList<IExaltedSourceBook>>();
  private IExaltedEdition edition;
  protected boolean includePrereqs = true;

  private ArrayList<IExaltedSourceBook> workingExcludedMaterial;
  private boolean[] workingIncludePrereqs = new boolean[1];

  public SourceBookCharmFilter(IExaltedEdition edition) {
    this.edition = edition;
    for (ExaltedEdition thisEdition : ExaltedEdition.values()) {
      prepareEdition(thisEdition);
    }
  }

  private void prepareEdition(IExaltedEdition edition) {
    ArrayList<IExaltedSourceBook> materialList = new ArrayList<IExaltedSourceBook>();
    List<IExaltedSourceBook> bookSet = new SourceBookCollection().getSourcesForEdition(edition);
    materialList.addAll(bookSet);
    allMaterial.put(edition, materialList);
    ArrayList<IExaltedSourceBook> materialExcluded = new ArrayList<IExaltedSourceBook>();
    excludedMaterial.put(edition, materialExcluded);
  }

  @Override
  public boolean acceptsCharm(ICharm charm, boolean isAncestor) {
    if (isAncestor && includePrereqs) {
      return true;
    }
    if (mustBeShownDueToCircumstance(charm)) {
      return true;
    }
    return !isExcluded(charm.getPrimarySource());
  }

  private boolean isExcluded(IExaltedSourceBook primarySource) {
    List<IExaltedSourceBook> excludedSourceList = getExcludedMaterialFor(getEdition());
    return excludedSourceList.contains(primarySource);
  }

  protected abstract boolean mustBeShownDueToCircumstance(ICharm charm);

  @Override
  public JPanel getFilterPreferencePanel(IResources resources) {
    workingExcludedMaterial = new ArrayList<IExaltedSourceBook>(getExcludedMaterialFor(getEdition()));
    workingIncludePrereqs[0] = includePrereqs;
    SourceBookCharmFilterPage page = new SourceBookCharmFilterPage(resources, getApprovedList(getEdition()),
            workingExcludedMaterial, workingIncludePrereqs);
    return page.getContent();
  }

  private List<IExaltedSourceBook> getApprovedList(IExaltedEdition edition) {
    List<IExaltedSourceBook> approvedMaterial = new ArrayList<IExaltedSourceBook>(allMaterial.get(edition));
    approvedMaterial.removeAll(getExcludedMaterialFor(edition));
    return approvedMaterial;
  }

  @Override
  public void apply() {
    excludedMaterial.put(getEdition(), workingExcludedMaterial);
    includePrereqs = workingIncludePrereqs[0];
    reset();
  }

  @Override
  public boolean isDirty() {
    return workingExcludedMaterial != null || includePrereqs != workingIncludePrereqs[0];
  }

  @Override
  public void reset() {
    workingExcludedMaterial = null;
  }

  protected IExaltedEdition getEdition() {
    return edition;
  }

  private List<IExaltedSourceBook> getExcludedMaterialFor(IExaltedEdition edition) {
    return excludedMaterial.get(edition);
  }
}