package net.sf.anathema.framework.repository.access.printname;

import com.google.gson.Gson;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.view.PrintNameFile;
import net.sf.anathema.library.io.InputOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JsonPrintNameFileReader implements PrintNameFileReader {

  private final Gson gson = new Gson();

  @Override
  public PrintNameFile readPrintName(File file, IItemType itemType) throws IOException {
    try (InputStream stream = new FileInputStream(file)) {
      String content = InputOutput.toString(stream);
      ItemReference itemReference = gson.fromJson(content, ItemReference.class);
      return new PrintNameFile(file, itemReference.printName, itemReference.repositoryId, itemType);
    }
  }
}