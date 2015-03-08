package net.sf.anathema.hero.charms.sheet.content.mnemonic;

import net.sf.anathema.hero.magic.sheet.content.IMagicStats;
import net.sf.anathema.hero.magic.sheet.content.mnemonic.MagicMnemonic;

import java.util.ArrayList;
import java.util.List;

public class AbstractMagicMnemonic implements MagicMnemonic {

  private final List<IMagicStats> printMagic;

  public AbstractMagicMnemonic(List<IMagicStats> printMagic) {
    this.printMagic = printMagic;
  }

  @Override
  public void removePrintMagic(IMagicStats printMagic) {
    this.printMagic.remove(printMagic);
  }

  @Override
  public List<IMagicStats> getRemainingPrintMagic() {
    return new ArrayList<>(printMagic);
  }
}
