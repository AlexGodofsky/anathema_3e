package net.sf.anathema.character.equipment.creation.model;

import net.sf.anathema.character.equipment.creation.presenter.IEquipmentStatisticsModel;
import net.sf.anathema.lib.workflow.textualdescription.ITextualDescription;
import net.sf.anathema.lib.workflow.textualdescription.SimpleTextualDescription;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.event.ObjectValueListener;
import org.jmock.example.announcer.Announcer;

public class EquipmentStatisticsModel implements IEquipmentStatisticsModel {

  private final ITextualDescription name = new SimpleTextualDescription();
  private final Announcer<ChangeListener> announcer = Announcer.to(ChangeListener.class);

  public EquipmentStatisticsModel() {
    name.addTextChangedListener(new ObjectValueListener<String>() {
      @Override
      public void valueChanged(String text) {
        EquipmentStatisticsModel.this.announceValidationChange();
      }
    });
  }

  @Override
  public final ITextualDescription getName() {
    return name;
  }

  @Override
  public void addValidListener(ChangeListener listener) {
    announcer.addListener(listener);
  }

  @Override
  public boolean isValid() {
    return isNameDefined();
  }

  protected void announceValidationChange() {
    announcer.announce().changeOccurred();
  }

  private boolean isNameDefined() {
    return !name.isEmpty();
  }
}