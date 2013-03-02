package net.sf.anathema.campaign.music.impl.model;

import net.sf.anathema.campaign.music.impl.persistence.MusicDatabasePersister;
import net.sf.anathema.campaign.music.impl.persistence.categorization.EventProvider;
import net.sf.anathema.campaign.music.impl.persistence.categorization.MoodProvider;
import net.sf.anathema.campaign.music.impl.persistence.categorization.ThemeProvider;
import net.sf.anathema.campaign.music.model.SelectionContainerModel;
import net.sf.anathema.campaign.music.model.util.IMusicCategorizationModel;
import net.sf.anathema.campaign.music.presenter.IMusicEvent;
import net.sf.anathema.campaign.music.presenter.IMusicMood;
import net.sf.anathema.campaign.music.presenter.IMusicTheme;
import net.sf.anathema.campaign.music.presenter.ISelectionContainerModel;

public class DbMusicCategorizationModel implements IMusicCategorizationModel {

  private final ISelectionContainerModel<IMusicMood> feelingsModel;
  private final ISelectionContainerModel<IMusicTheme> themesModel;
  private final ISelectionContainerModel<IMusicEvent> moodsModel;

  public DbMusicCategorizationModel(MusicDatabasePersister databasePersister) {
    this.feelingsModel = SelectionContainerModel.createDefault(
        IMusicMood.class,
        new MoodProvider(databasePersister).getAvailableValues());
    this.themesModel = SelectionContainerModel.createDefault(
        IMusicTheme.class,
        new ThemeProvider(databasePersister).getAvailableValues());
    this.moodsModel = SelectionContainerModel.createDefault(
        IMusicEvent.class,
        new EventProvider(databasePersister).getAvailableValues());
  }

  @Override
  public ISelectionContainerModel<IMusicMood> getMoodsModel() {
    return feelingsModel;
  }

  @Override
  public ISelectionContainerModel<IMusicTheme> getThemesModel() {
    return themesModel;
  }

  @Override
  public ISelectionContainerModel<IMusicEvent> getEventsModel() {
    return moodsModel;
  }
}
