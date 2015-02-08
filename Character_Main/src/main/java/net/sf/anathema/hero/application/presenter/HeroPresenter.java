package net.sf.anathema.hero.application.presenter;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModelInitializer;
import net.sf.anathema.hero.individual.overview.HeroModelGroup;
import net.sf.anathema.hero.individual.view.HeroView;
import net.sf.anathema.hero.individual.view.SectionView;

import static net.sf.anathema.hero.individual.overview.HeroModelGroup.*;

public class HeroPresenter {

  private final InitializerList initializerList;
  private final Hero hero;
  private final HeroView heroView;
  private final HeroEnvironment environment;

  public HeroPresenter(Hero hero, HeroView view, HeroEnvironment environment) {
    this.initializerList = new InitializerList(environment);
    this.hero = hero;
    this.heroView = view;
    this.environment = environment;
  }

  public void initPresentation() {
    //TODO: Sandra, which of these are still relevant?
    initializeSection("CardView.Background.Title", Background);
    initializeSection("CardView.Outline.Title", Outline);
    initializeSection("CardView.Mundane.Title", Mundane);
    initializeSection("CardView.NaturalTraits.Title", NaturalTraits);
    initializeSection("CardView.Spiritual.Title", Spiritual);
    initializeSection("CardView.SpiritualTraits.Title", SpiritualTraits);
    initializeSection("CardView.Charms.Title", Charms);
    initializeSection("CardView.Sorcery.Title", Sorcery);
    initializeSection("CardView.CharmConfiguration.Title", Magic);
    initializeSection("CardView.Panoply.Title", Panoply);
    initializeSection("CardView.MiscellaneousConfiguration.Title", Miscellaneous);
  }

  private void initializeSection(String titleKey, HeroModelGroup group) {
    SectionView sectionView = prepareSection(titleKey);
    initializeGroup(group, sectionView);
    sectionView.finishInitialization();
  }

  private SectionView prepareSection(String titleKey) {
    String sectionTitle = environment.getResources().getString(titleKey);
    return heroView.addSection(sectionTitle);
  }

  private void initializeGroup(HeroModelGroup group, SectionView sectionView) {
    for (HeroModelInitializer initializer : initializerList.getInOrderFor(group)) {
      if (initializer.canWorkForHero(hero)) {
        initializer.initialize(sectionView, hero);
      }
    }
  }
}