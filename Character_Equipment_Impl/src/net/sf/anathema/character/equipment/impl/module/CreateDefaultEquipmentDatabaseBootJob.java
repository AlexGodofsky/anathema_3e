package net.sf.anathema.character.equipment.impl.module;

import net.sf.anathema.ProxySplashscreen;
import net.sf.anathema.character.equipment.impl.item.model.gson.EquipmentGson;
import net.sf.anathema.character.equipment.impl.item.model.gson.GsonEquipmentDatabase;
import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.initialization.BootJob;
import net.sf.anathema.initialization.IAnathemaBootJob;
import net.sf.anathema.initialization.reflections.ResourceLoader;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.resources.ResourceFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static net.sf.anathema.character.equipment.impl.module.DatabaseConversionBootJob.OLD_DATABASE_FILE;
import static net.sf.anathema.character.equipment.impl.module.DatabaseConversionBootJob.OLD_DATABASE_FOLDER;

@BootJob
public class CreateDefaultEquipmentDatabaseBootJob implements IAnathemaBootJob {

  private final static String EQUIPMENT_REGEX = "^.*\\.item$";

  @Override
  public void run(IResources resources, IAnathemaModel anathemaModel) {
    /* Once bootjob ordering is in place, any of this class' variables/functions with
the word 'legacy' in it, and any code that calls it, can be safely removed.
Just make sure this runs *after* DatabaseConversionBootJob. */
    Path legacyDatabaseFile = anathemaModel.getRepository().getDataBaseDirectory(OLD_DATABASE_FOLDER).resolve(OLD_DATABASE_FILE);
    GsonEquipmentDatabase database = GsonEquipmentDatabase.CreateFrom(anathemaModel);
    boolean thereIsNoDataYet = !Files.exists(legacyDatabaseFile) && database.isEmpty();
    if (thereIsNoDataYet) {
      ProxySplashscreen.getInstance().displayStatusMessage(
              resources.getString("Equipment.Bootjob.DefaultDatabaseSplashmessage")); //$NON-NLS-1$
      populateRepository(database, anathemaModel.getResourceLoader());
    }
  }

  private void populateRepository(GsonEquipmentDatabase database, ResourceLoader reflections) {
    EquipmentGson gson = new EquipmentGson();
    try {
      for (ResourceFile file : reflections.getResourcesMatching(EQUIPMENT_REGEX)) {
        String itemJSON = getStringFromStream(file.getURL().openStream());
        database.saveTemplateNoOverwrite(gson.fromJson(itemJSON));
      }
    } catch (IOException e) {
      throw new RuntimeException("Could not create default database.");
    }
  }

  private String getStringFromStream(InputStream stream) {
    return new Scanner(stream).useDelimiter("\\A").next();
  }
}