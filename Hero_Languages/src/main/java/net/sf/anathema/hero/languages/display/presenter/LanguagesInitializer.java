package net.sf.anathema.hero.languages.display.presenter;

import net.sf.anathema.framework.model.ApplicationModel;
import net.sf.anathema.hero.display.HeroModelGroup;
import net.sf.anathema.hero.display.presenter.HeroModelInitializer;
import net.sf.anathema.hero.display.presenter.RegisteredInitializer;
import net.sf.anathema.hero.framework.display.SectionView;
import net.sf.anathema.hero.languages.model.LanguagesModel;
import net.sf.anathema.hero.languages.model.LanguagesModelFetcher;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.platform.environment.Environment;

@RegisteredInitializer(HeroModelGroup.NaturalTraits)
@Weight(weight = 400)
public class LanguagesInitializer implements HeroModelInitializer {

  @SuppressWarnings("UnusedParameters")
  public LanguagesInitializer(ApplicationModel model) {
    //nothing to do
  }

  @Override
  public void initialize(SectionView sectionView, Hero hero, Environment environment) {
    String viewName = environment.getString("AdditionalTemplateView.TabName.Linguistics");
    LanguagesView view = sectionView.addView(viewName, LanguagesView.class);
    LanguagesModel languagesModel = LanguagesModelFetcher.fetch(hero);
    new LanguagesPresenter(languagesModel, view, environment).initPresentation();
  }

  @Override
  public boolean canWorkForHero(Hero hero) {
    return LanguagesModelFetcher.fetch(hero) != null;
  }
}