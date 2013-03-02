package net.sf.anathema.character.impl.model.context.magic;

import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.model.ISpellConfiguration;
import net.sf.anathema.character.model.ISpellLearnStrategy;

public class ProxySpellLearnStrategy implements ISpellLearnStrategy {

  private ISpellLearnStrategy strategy;

  public ProxySpellLearnStrategy(ISpellLearnStrategy strategy) {
    this.strategy = strategy;
  }

  public void setStrategy(ISpellLearnStrategy strategy) {
    this.strategy = strategy;
  }

  @Override
  public void addSpells(ISpellConfiguration configuration, ISpell[] addedSpells) {
    strategy.addSpells(configuration, addedSpells);
  }

  @Override
  public void removeSpells(ISpellConfiguration configuration, ISpell[] removedSpells) {
    strategy.removeSpells(configuration, removedSpells);
  }

  @Override
  public boolean isSpellAllowed(ISpellConfiguration configuration, ISpell spell) {
    return strategy.isSpellAllowed(configuration, spell);
  }

  @Override
  public ISpell[] getLearnedSpells(ISpellConfiguration configuration) {
    return strategy.getLearnedSpells(configuration);
  }

  @Override
  public boolean isLearned(ISpellConfiguration configuration, ISpell spell) {
    return strategy.isLearned(configuration, spell);
  }
}