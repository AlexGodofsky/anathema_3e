package net.sf.anathema.character.equipment.creation.presenter;

import net.sf.anathema.hero.health.HealthType;
import net.sf.anathema.lib.gui.AgnosticUIConfiguration;
import net.sf.anathema.lib.gui.layout.AdditiveView;
import net.sf.anathema.lib.gui.selection.ObjectSelectionView;
import net.sf.anathema.lib.gui.widgets.IIntegerSpinner;

public interface WeaponDamageView extends AdditiveView, ObjectSelectionView<HealthType> {

  IIntegerSpinner getDamageIntegerSpinner();

  IIntegerSpinner getMinDamageIntegerSpinner();

  void setHealthTypeRenderer(AgnosticUIConfiguration<HealthType> renderer);

  void setDamageLabelText(String label);
  
  void setMinDamageLabelText(String label);
}