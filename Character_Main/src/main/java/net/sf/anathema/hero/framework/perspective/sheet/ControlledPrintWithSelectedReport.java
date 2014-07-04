package net.sf.anathema.hero.framework.perspective.sheet;

import net.sf.anathema.hero.description.HeroDescriptionFetcher;
import net.sf.anathema.hero.framework.reporting.Report;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.library.io.FileChooserConfiguration;
import net.sf.anathema.library.io.FileExtension;
import net.sf.anathema.library.io.SingleFileChooser;
import net.sf.anathema.library.text.ITextualDescription;
import net.sf.anathema.platform.environment.Environment;

import static net.sf.anathema.hero.framework.perspective.sheet.PrintCommand.PDF_EXTENSION;

public class ControlledPrintWithSelectedReport {
  private final Environment environment;
  private final Hero hero;
  private final Report report;
  private final SingleFileChooser singleFileChooser;

  public ControlledPrintWithSelectedReport(Environment environment, Report report, Hero hero, SingleFileChooser singleFileChooser) {
    this.environment = environment;
    this.report = report;
    this.hero = hero;
    this.singleFileChooser = singleFileChooser;
  }

  public void execute() {
    String name = getFileNameSuggestion()+PDF_EXTENSION;
    FileChooserConfiguration configuration = new FileChooserConfiguration(new FileExtension("PDF Files", "*" + PDF_EXTENSION), name);
    ControlledFileChooser fileChooser = new ControlledFileChooser(singleFileChooser, configuration);
    new PrintCommand(environment, report, fileChooser, hero).execute();
  }

  private String getFileNameSuggestion() {
    ITextualDescription nameContainer = HeroDescriptionFetcher.fetch(hero).getName();
    if (nameContainer.isEmpty()) {
      return "Character";
    }
    return nameContainer.getText();
  }
}