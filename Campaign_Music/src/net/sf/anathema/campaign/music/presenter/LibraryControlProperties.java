package net.sf.anathema.campaign.music.presenter;

import net.sf.anathema.lib.resources.IResources;

public class LibraryControlProperties extends AbstractMusicProperties implements ILibraryControlProperties {

  public LibraryControlProperties(IResources resources) {
    super(resources);
  }

  @Override
  public String getLibrariesString() {
    return getString("Music.Tabs.Libraries"); //$NON-NLS-1$
  }

  @Override
  public String getNoContentString() {
    return getString("Music.Labels.LibraryTrackView.NoContentTitle"); //$NON-NLS-1$
  }

  @Override
  public String getSearchString() {
    return getString("Music.Tabs.Search"); //$NON-NLS-1$
  }

  @Override
  public String getTracksString() {
    return getString("Music.Tabs.Tracks"); //$NON-NLS-1$
  }

  @Override
  public String getLibraryControlBorderTitle() {
    return getString("Music.Borders.LibraryControl"); //$NON-NLS-1$
  }
}