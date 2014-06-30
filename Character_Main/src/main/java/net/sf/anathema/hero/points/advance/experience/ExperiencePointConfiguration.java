package net.sf.anathema.hero.points.advance.experience;

public interface ExperiencePointConfiguration {

  ExperiencePointEntry[] getAllEntries();

  ExperiencePointEntry addEntry();

  void removeEntry();

  int getTotalExperiencePoints();

  void addExperiencePointConfigurationListener(ExperiencePointConfigurationListener listener);

  void addEntrySelectionListener(ExperienceSelectionListener listener);

  int getExtraSpendings();

  void selectForChange(ExperiencePointEntry entry);

  void updateCurrentSelection(String description, int points);

  ExperiencePointEntry getCurrentSelection();
}