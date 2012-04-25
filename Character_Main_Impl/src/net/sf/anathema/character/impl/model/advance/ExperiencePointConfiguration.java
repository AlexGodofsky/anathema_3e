package net.sf.anathema.character.impl.model.advance;

import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.character.model.advance.IExperiencePointConfiguration;
import net.sf.anathema.character.model.advance.IExperiencePointConfigurationListener;
import net.sf.anathema.character.model.advance.IExperiencePointEntry;
import net.sf.anathema.lib.control.GenericControl;
import net.sf.anathema.lib.control.IClosure;
import net.sf.anathema.lib.control.objectvalue.IObjectValueChangedListener;

public class ExperiencePointConfiguration implements IExperiencePointConfiguration {

  private final List<IExperiencePointEntry> entries = new ArrayList<IExperiencePointEntry>();
  private final GenericControl<IExperiencePointConfigurationListener> control = new GenericControl<IExperiencePointConfigurationListener>();
  private final IObjectValueChangedListener<IExperiencePointEntry> entryChangeListener = new IObjectValueChangedListener<IExperiencePointEntry>() {
    @Override
    public void valueChanged(IExperiencePointEntry entry) {
      fireEntryChangedEvent(entry);
    }
  };

  @Override
  public IExperiencePointEntry[] getAllEntries() {
    return entries.toArray(new IExperiencePointEntry[entries.size()]);
  }

  @Override
  public IExperiencePointEntry addEntry() {
    IExperiencePointEntry newEntry = addEntryWithoutEvent();
    addEntryListeningAndFireEvent(newEntry);
    return newEntry;
  }

  private void addEntryListeningAndFireEvent(IExperiencePointEntry newEntry) {
    fireEntryAddedEvent(newEntry);
    newEntry.addChangeListener(entryChangeListener);
  }

  private IExperiencePointEntry addEntryWithoutEvent() {
    IExperiencePointEntry newEntry = new ExperiencePointEntry();
    entries.add(newEntry);
    return newEntry;
  }

  @Override
  public void removeEntry(IExperiencePointEntry entry) {
    entries.remove(entry);
    entry.removeChangeListener(entryChangeListener);
    fireEntryRemovedEvent(entry);
  }

  private void fireEntryRemovedEvent(final IExperiencePointEntry entry) {
    control.forAllDo(new IClosure<IExperiencePointConfigurationListener>() {
      @Override
      public void execute(IExperiencePointConfigurationListener input) {
        input.entryRemoved(entry);
      }
    });
  }

  private void fireEntryAddedEvent(final IExperiencePointEntry entry) {
    control.forAllDo(new IClosure<IExperiencePointConfigurationListener>() {
      @Override
      public void execute(IExperiencePointConfigurationListener input) {
        input.entryAdded(entry);
      }
    });
  }

  private void fireEntryChangedEvent(final IExperiencePointEntry entry) {
    control.forAllDo(new IClosure<IExperiencePointConfigurationListener>() {
      @Override
      public void execute(IExperiencePointConfigurationListener input) {
        input.entryChanged(entry);
      }
    });
  }

  @Override
  public void addExperiencePointConfigurationListener(IExperiencePointConfigurationListener listener) {
    control.addListener(listener);
  }

  @Override
  public int getTotalExperiencePoints() {
    int sum = 0;
    for (IExperiencePointEntry entry : getAllEntries()) {
      if (entry.getExperiencePoints() > 0) {
        sum += entry.getExperiencePoints();
      }
    }
    return sum;
  }

  @Override
  public int getExtraSpendings() {
    int sum = 0;
    for (IExperiencePointEntry entry : getAllEntries()) {
      if (entry.getExperiencePoints() < 0) {
        sum -= entry.getExperiencePoints();
      }
    }
    return sum;
  }

  @Override
  public void addEntry(String description, int xpCost) {
    IExperiencePointEntry entry = addEntryWithoutEvent();
    entry.getTextualDescription().setText(description);
    entry.setExperiencePoints(xpCost);
    addEntryListeningAndFireEvent(entry);
  }
}