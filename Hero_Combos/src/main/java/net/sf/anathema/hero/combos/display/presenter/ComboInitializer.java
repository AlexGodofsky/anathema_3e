package net.sf.anathema.hero.combos.display.presenter;

import net.sf.anathema.hero.application.presenter.HeroModelInitializer;
import net.sf.anathema.hero.application.presenter.RegisteredInitializer;
import net.sf.anathema.hero.charms.display.presenter.CharmDescriptionProviderExtractor;
import net.sf.anathema.hero.charms.model.CharmsModelFetcher;
import net.sf.anathema.hero.combos.model.ComboConfigurationModel;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.view.SectionView;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.magic.description.model.MagicDescriptionProvider;
import net.sf.anathema.platform.environment.Environment;
import net.sf.anathema.platform.frame.ApplicationModel;

import static net.sf.anathema.hero.individual.overview.HeroModelGroup.Magic;

@RegisteredInitializer(Magic)
@Weight(weight = 100)
public class ComboInitializer implements HeroModelInitializer {

  private ApplicationModel model;

  public ComboInitializer(ApplicationModel model) {
    this.model = model;
  }

  @Override
  public void initialize(SectionView sectionView, Hero hero, Environment environment) {
    String header = environment.getString("CardView.CharmConfiguration.ComboCreation.Title");
    ComboConfigurationView comboView = sectionView.addView(header, ComboConfigurationView.class);
    MagicDescriptionProvider magicDescriptionProvider = CharmDescriptionProviderExtractor.CreateFor(model, environment);
    ComboConfigurationModel comboModel = new ComboConfigurationModel(hero, magicDescriptionProvider);
    new ComboConfigurationPresenter(hero, environment, comboModel, comboView).initPresentation();

  }

  @Override
  public boolean canWorkForHero(Hero hero) {
    return CharmsModelFetcher.fetch(hero) != null;
  }
}
