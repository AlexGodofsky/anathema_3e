package net.sf.anathema.hero.spells.sheet.magicreport;

import com.google.common.base.Joiner;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.MultiColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.sf.anathema.hero.magic.basic.Magic;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.magic.description.MagicDescription;
import net.sf.anathema.hero.magic.spells.Spell;
import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.environment.Environment;
import net.sf.anathema.hero.framework.reporting.ReportException;
import net.sf.anathema.framework.reporting.pdf.AbstractPdfReport;
import net.sf.anathema.framework.reporting.pdf.PdfReportUtils;
import net.sf.anathema.hero.charms.display.MagicDisplayLabeler;
import net.sf.anathema.hero.charms.display.presenter.CharmDescriptionProviderExtractor;
import net.sf.anathema.hero.charms.display.tooltip.ScreenDisplayInfoContributor;
import net.sf.anathema.hero.charms.display.tooltip.source.MagicSourceContributor;
import net.sf.anathema.hero.charms.display.tooltip.type.VerboseCharmTypeContributor;
import net.sf.anathema.hero.charms.sheet.content.CharmContentHelper;
import net.sf.anathema.hero.charms.sheet.content.stats.CharmStats;
import net.sf.anathema.hero.experience.ExperienceModelFetcher;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.spells.model.SpellsModelFetcher;
import net.sf.anathema.hero.spells.sheet.content.SpellStats;

import static java.text.MessageFormat.format;

public class MagicReport extends AbstractPdfReport {

  private final Environment environment;
  private final IApplicationModel model;
  private final MagicPartFactory partFactory;

  public MagicReport(Environment environment, IApplicationModel model) {
    this.environment = environment;
    this.model = model;
    partFactory = new MagicPartFactory(new PdfReportUtils());
  }

  @Override
  public String toString() {
    return environment.getString("MagicReport.Name");
  }

  @Override
  public void performPrint(Hero hero, Document document, PdfWriter writer) throws ReportException {
    MultiColumnText columnText = new MultiColumnText(document.top() - document.bottom() - 15);
    columnText.addRegularColumns(document.left(), document.right(), 20, 2);
    try {
      printCharms(columnText, hero);
      printSpells(columnText, hero);
      writeColumnText(document, columnText);
    } catch (DocumentException e) {
      throw new ReportException(e);
    }
  }

  private void printSpells(MultiColumnText columnText, Hero hero) throws DocumentException {
    String currentGroup = "";
    for (Spell spell : getCurrentSpells(hero)) {
      SpellStats spellStats = createSpellStats(spell);
      String nextGroupName = format("{0} {1}", spellStats.getType(environment), spellStats.getGroupName(environment));
      if (!currentGroup.equals(nextGroupName)) {
        currentGroup = nextGroupName;
        columnText.addElement(partFactory.createGroupTitle(currentGroup));
      }
      addMagicName(spell, columnText);
      addSpellCost(spell, columnText);
      addSpellTarget(spellStats, columnText);
      addCharmDescription(spell, columnText);
    }
  }

  public void printCharms(MultiColumnText columnText, Hero hero) throws DocumentException {
    String currentGroup = "";
    for (Charm charm : new CharmFetcher().getCharms(hero)) {
      CharmStats charmStats = createCharmStats(hero, charm);
      if (!currentGroup.equals(charmStats.getGroupName(environment))) {
        currentGroup = charmStats.getGroupName(environment);
        columnText.addElement(partFactory.createGroupTitle(currentGroup));
      }
      addMagicName(charm, columnText);
      addCharmData(charmStats, charm, columnText);
      addCharmDescription(charm, columnText);
    }
  }

  private void addSpellCost(Spell charm, MultiColumnText columnText) throws DocumentException {
    String costsLabel = environment.getString("MagicReport.Costs.Label") + ": ";
    String costsValue = new ScreenDisplayInfoContributor(environment).createCostString(charm);
    columnText.addElement(partFactory.createDataPhrase(costsLabel, costsValue));
  }

  private void addSpellTarget(SpellStats spellStats, MultiColumnText columnText) throws DocumentException {
    String targetLabel = environment.getString("MagicReport.Target.Label") + ": ";
    String target = Joiner.on(", ").join(spellStats.getDetailStrings(environment));
    columnText.addElement(partFactory.createDataPhrase(targetLabel, target));
  }

  private void addMagicName(Magic magic, MultiColumnText columnText) throws DocumentException {
    String charmName = new MagicDisplayLabeler(environment).getLabelForMagic(magic);
    columnText.addElement(partFactory.createCharmTitle(charmName));
  }

  private void addCharmData(CharmStats charmStats, Charm charm, MultiColumnText columnText) throws DocumentException {
    PdfPTable table = partFactory.createDataTable();
    addCostsCell(charm, table);
    addTypeCell(charm, table);
    addKeywordsRow(charmStats, table);
    addDurationRow(charmStats, table);
    columnText.addElement(table);
  }

  private void addCostsCell(Charm charm, PdfPTable table) {
    String costsLabel = environment.getString("MagicReport.Costs.Label") + ": ";
    String costsValue = new ScreenDisplayInfoContributor(environment).createCostString(charm);
    table.addCell(partFactory.createDataCell(costsLabel, costsValue));
  }

  private void addTypeCell(Charm charm, PdfPTable table) {
    String typeLabel = environment.getString("MagicReport.Type.Label") + ": ";
    String typeValue = new VerboseCharmTypeContributor(environment).createTypeString(charm.getCharmTypeModel());
    table.addCell(partFactory.createDataCell(typeLabel, typeValue));
  }

  private void addKeywordsRow(CharmStats charmStats, PdfPTable table) {
    String keywords = Joiner.on(", ").join(charmStats.getDetailStrings(environment));
    String keywordsLabel = environment.getString("MagicReport.Keywords.Label") + ": ";
    table.addCell(partFactory.createDoubleDataCell(keywordsLabel, keywords));
  }

  private void addDurationRow(CharmStats charmStats, PdfPTable table) {
    String durationLabel = environment.getString("MagicReport.Duration.Label") + ": ";
    String durationString = charmStats.getDurationString(environment);
    table.addCell(partFactory.createDoubleDataCell(durationLabel, durationString));
  }

  private void addCharmDescription(Magic magic, MultiColumnText columnText) throws DocumentException {
    MagicDescription charmDescription = getCharmDescription(magic);
    if (charmDescription.isEmpty()) {
      String sourceString = new MagicSourceContributor<>(environment).createSourceString(magic);
      String sourceReference = environment.getString("MagicReport.See.Source", sourceString);
      columnText.addElement(partFactory.createDescriptionParagraph(sourceReference));
    }
    for (String paragraph : charmDescription.getParagraphs()) {
      columnText.addElement(partFactory.createDescriptionParagraph(paragraph));
    }
  }

  private CharmStats createCharmStats(Hero hero, Charm charm) {
    return new CharmStats(charm, new CharmContentHelper(hero));
  }

  private SpellStats createSpellStats(Spell spell) {
    return new SpellStats(spell);
  }

  private MagicDescription getCharmDescription(Magic magic) {
    return CharmDescriptionProviderExtractor.CreateFor(model, environment).getCharmDescription(magic);
  }

  private void writeColumnText(Document document, MultiColumnText columnText) throws DocumentException {
    do {
      document.add(columnText);
      columnText.nextColumn();
    } while (columnText.isOverflow());
  }

  @Override
  public boolean supports(Hero hero) {
    return new CharmFetcher().hasCharms(hero);
  }

  private Spell[] getCurrentSpells(Hero hero) {
    boolean experienced = ExperienceModelFetcher.fetch(hero).isExperienced();
    return SpellsModelFetcher.fetch(hero).getLearnedSpells(experienced);
  }
}