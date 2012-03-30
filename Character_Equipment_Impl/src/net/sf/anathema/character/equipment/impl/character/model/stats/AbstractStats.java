package net.sf.anathema.character.equipment.impl.character.model.stats;

import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.lib.util.IIdentificate;

public abstract class AbstractStats implements IEquipmentStats {

  private IIdentificate name;
  private boolean useAttunementModifiers = true;
  private MagicalMaterial[] materials = null;

  @Override
  public IIdentificate getName() {
    return name;
  }

  public final void setName(IIdentificate name) {
    if (name == null) name = null;
    this.name = name;
  }

  @Override
  public boolean useAttunementModifiers() {
    return useAttunementModifiers;
  }

  @Override
  public void setUseAttunementModifiers(boolean value) {
    useAttunementModifiers = value;
  }

  @Override
  public Object[] getApplicableMaterials() {
    if (materials != null && materials.length == MagicalMaterial.values().length) return null;
    return materials;
  }

  @Override
  public void setApplicableMaterials(Object[] materials) {
    this.materials = (MagicalMaterial[]) materials;
  }
}