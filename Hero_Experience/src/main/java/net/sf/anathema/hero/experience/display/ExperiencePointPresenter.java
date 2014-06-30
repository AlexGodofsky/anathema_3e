package net.sf.anathema.hero.experience.display;

import net.sf.anathema.character.framework.display.SectionView;
import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.hero.experience.ExperienceModel;
import net.sf.anathema.hero.experience.ExperienceModelFetcher;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.points.PointModelFetcher;
import net.sf.anathema.lib.control.ChangeListener;

public class ExperiencePointPresenter {

  private Resources resources;
  private Hero hero;

  public ExperiencePointPresenter(Resources resources, Hero hero) {
    this.resources = resources;
    this.hero = hero;
  }

  public void initPresentation(final SectionView section) {
    final ExperienceModel experienceModel = ExperienceModelFetcher.fetch(hero);
    initExperiencePointPresentation(experienceModel.isExperienced(), section);
    experienceModel.addStateChangeListener(new ChangeListener() {
      @Override
      public void changeOccurred() {
        initExperiencePointPresentation(experienceModel.isExperienced(), section);
      }
    });
  }

  private void initExperiencePointPresentation(boolean experienced, SectionView section) {
    if (experienced) {
      String header = resources.getString("CardView.ExperienceConfiguration.Title");
      ExperienceView experienceView = section.addView(header, ExperienceView.class);
      new ExperienceConfigurationPresenter(resources, PointModelFetcher.fetch(hero).getExperiencePoints(), experienceView)
              .initPresentation();
    }
  }
}