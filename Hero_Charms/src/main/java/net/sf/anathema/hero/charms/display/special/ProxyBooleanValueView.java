package net.sf.anathema.hero.charms.display.special;

import net.sf.anathema.library.event.IBooleanValueChangedListener;
import net.sf.anathema.library.view.BooleanValueView;
import org.jmock.example.announcer.Announcer;

public class ProxyBooleanValueView implements BooleanValueView {
  private final String label;
  private final Announcer<IBooleanValueChangedListener> listeners = new Announcer<>(IBooleanValueChangedListener.class);
  private BooleanValueView actualView;
  private boolean selected = false;

  public ProxyBooleanValueView(String label) {
    this.label = label;
  }

  public void setActualView(BooleanValueView actualView) {
    this.actualView = actualView;
    actualView.addChangeListener(new IBooleanValueChangedListener() {
      @Override
      public void valueChanged(boolean newValue) {
        listeners.announce().valueChanged(newValue);
      }
    });
    actualView.setSelected(selected);
  }

  @Override
  public void setSelected(boolean selected) {
    if (actualView != null) {
      actualView.setSelected(selected);
    }
    if (selected == this.selected) {
      return;
    }
    this.selected = selected;
    listeners.announce().valueChanged(selected);
  }

  @Override
  public void addChangeListener(IBooleanValueChangedListener listener) {
    if (actualView != null) {
      actualView.addChangeListener(listener);
      return;
    }
    listeners.addListener(listener);
  }

  public String getLabel() {
    return label;
  }
}