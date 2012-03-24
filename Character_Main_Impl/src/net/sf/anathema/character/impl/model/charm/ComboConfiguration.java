package net.sf.anathema.character.impl.model.charm;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.impl.model.charm.combo.IComboArbitrator;
import net.sf.anathema.character.impl.model.charm.combo.SecondEditionComboArbitrator;
import net.sf.anathema.character.model.charm.CharmLearnAdapter;
import net.sf.anathema.character.model.charm.ICharmConfiguration;
import net.sf.anathema.character.model.charm.ICombo;
import net.sf.anathema.character.model.charm.IComboConfiguration;
import net.sf.anathema.character.model.charm.IComboConfigurationListener;
import net.sf.anathema.character.model.charm.learn.IComboLearnStrategy;
import net.sf.anathema.lib.control.GenericControl;
import net.sf.anathema.lib.control.IClosure;
import net.sf.anathema.lib.control.change.IChangeListener;

import java.util.ArrayList;
import java.util.List;

public class ComboConfiguration implements IComboConfiguration {

  private final List<ICombo> creationComboList = new ArrayList<ICombo>();
  private final List<ICombo> experiencedComboList = new ArrayList<ICombo>();
  private final IComboArbitrator rules;
  private final ICombo editCombo = new Combo();
  private final GenericControl<IComboConfigurationListener> control = new GenericControl<IComboConfigurationListener>();
  private final ComboIdProvider idProvider = new ComboIdProvider();
  private final IComboLearnStrategy learnStrategy;


  public ComboConfiguration(ICharmConfiguration charmConfiguration, IComboLearnStrategy learnStrategy) {
    this.learnStrategy = learnStrategy;
    charmConfiguration.addCharmLearnListener(new CharmLearnAdapter() {
      @Override
      public void charmForgotten(ICharm charm) {
        checkCombos(charm);
      }
    });
    this.rules = new SecondEditionComboArbitrator();
  }

  @Override
  public void setCrossPrerequisiteTypeComboAllowed(boolean allowed) {
    rules.setCrossPrerequisiteTypeComboAllowed(allowed);
  }

  private void checkCombos(ICharm charm) {
    List<ICombo> deletionList = new ArrayList<ICombo>();
    for (ICombo combo : creationComboList) {
      if (combo.contains(charm)) {
        combo.removeCharms(new ICharm[]{charm});
        if (combo.getCharms().length < 2) {
          deletionList.add(combo);
        }
        fireComboChanged(combo);
      }
    }
    if (editCombo.contains(charm)) {
      removeCharmsFromCombo(new ICharm[]{charm});
    }
    for (ICombo combo : deletionList) {
      deleteCombo(combo);
    }
  }

  @Override
  public void addCharmToCombo(ICharm charm, boolean experienced) {
    if (rules.canBeAddedToCombo(getEditCombo(), charm)) {
      getEditCombo().addCharm(charm, experienced);
    } else {
      throw new IllegalArgumentException(
              "The charm " + charm.getId() + " is illegal in this combo."); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  @Override
  public void addComboModelListener(IChangeListener listener) {
    editCombo.addComboModelListener(listener);
  }

  @Override
  public void removeCharmsFromCombo(ICharm[] charms) {
    editCombo.removeCharms(charms);
  }

  @Override
  public void finalizeCombo() {
    learnStrategy.finalizeCombo(this);
  }

  @Override
  public void finalizeCombo(boolean experienced) {
    ICombo combo = editCombo.clone();
    if (combo.getId() == null) {
      combo.setId(idProvider.createId());
      if (experienced) {
        experiencedComboList.add(combo);
      } else {
        creationComboList.add(combo);
      }
      fireComboAdded(combo);
    } else {
      ICombo editedCombo = getComboById(combo.getId());
      editedCombo.getValuesFrom(combo);
      fireComboChanged(editedCombo);
      fireEndEditEvent();
    }
    editCombo.clear();
  }

  @Override
  public ICombo getEditCombo() {
    return editCombo;
  }

  private ICombo getComboById(int id) {
    for (ICombo combo : getCurrentCombos()) {
      if (combo.getId() == id) {
        return combo;
      }
    }
    return null;
  }

  @Override
  public void addComboConfigurationListener(IComboConfigurationListener listener) {
    control.addListener(listener);
  }

  private void fireComboAdded(final ICombo combo) {
    control.forAllDo(new IClosure<IComboConfigurationListener>() {
      @Override
      public void execute(IComboConfigurationListener input) {
        input.comboAdded(combo);
      }
    });
  }

  private void fireComboDeleted(final ICombo combo) {
    control.forAllDo(new IClosure<IComboConfigurationListener>() {
      @Override
      public void execute(IComboConfigurationListener input) {
        input.comboDeleted(combo);
      }
    });
  }

  private void fireComboChanged(final ICombo combo) {
    control.forAllDo(new IClosure<IComboConfigurationListener>() {
      @Override
      public void execute(IComboConfigurationListener input) {
        input.comboChanged(combo);
      }
    });
  }

  private void fireBeginEditEvent(final ICombo combo) {
    control.forAllDo(new IClosure<IComboConfigurationListener>() {
      @Override
      public void execute(IComboConfigurationListener input) {
        input.editBegun(combo);
      }
    });
  }

  private void fireEndEditEvent() {
    control.forAllDo(new IClosure<IComboConfigurationListener>() {
      @Override
      public void execute(IComboConfigurationListener input) {
        input.editEnded();
      }
    });
  }

  @Override
  public ICombo[] getCurrentCombos() {
    return learnStrategy.getCurrentCombos(this);
  }

  @Override
  public ICombo[] getCreationCombos() {
    return creationComboList.toArray(new ICombo[creationComboList.size()]);
  }

  @Override
  public ICombo[] getExperienceLearnedCombos() {
    return experiencedComboList.toArray(new ICombo[experiencedComboList.size()]);
  }

  @Override
  public boolean isComboLegal(ICharm charm) {
    return rules.canBeAddedToCombo(getEditCombo(), charm);
  }

  @Override
  public void deleteCombo(ICombo combo) {
    experiencedComboList.remove(combo);
    creationComboList.remove(combo);
    fireComboDeleted(combo);
    if (combo.getId().equals(editCombo.getId())) {
      clearCombo();
    }
  }

  @Override
  public void clearCombo() {
    editCombo.clear();
    fireEndEditEvent();
  }

  @Override
  public void beginComboEdit(ICombo combo) {
    editCombo.clear();
    editCombo.getValuesFrom(combo);
    fireBeginEditEvent(combo);
  }

  @Override
  public boolean isLearnedOnCreation(ICombo combo) {
    return creationComboList.contains(combo);
  }
}