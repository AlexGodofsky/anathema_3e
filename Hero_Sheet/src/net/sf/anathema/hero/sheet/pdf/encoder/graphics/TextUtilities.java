package net.sf.anathema.hero.sheet.pdf.encoder.graphics;

import com.itextpdf.text.Font;

public class TextUtilities {

  public static Font createBoldFont(Font font) {
    Font newFont = new Font(font);
    newFont.setStyle(Font.BOLD);
    return newFont;
  }
}
