package net.sf.anathema.hero.type;

import net.sf.anathema.framework.environment.ObjectFactory;
import net.sf.anathema.framework.environment.dependencies.Weight;
import net.sf.anathema.framework.environment.resources.ResourceFile;
import net.sf.anathema.hero.framework.data.ExtensibleDataSet;
import net.sf.anathema.hero.framework.data.IExtensibleDataSetCompiler;
import net.sf.anathema.hero.framework.type.CharacterType;
import net.sf.anathema.initialization.ExtensibleDataSetCompiler;

@Weight(weight = 1)
@ExtensibleDataSetCompiler
public class HeroTypeCompiler implements IExtensibleDataSetCompiler {

  private static final String TEMPLATE_FILE_RECOGNITION_PATTERN = ".+?\\.charactertype";
  private final ExtensibleCharacterTypes types = new ExtensibleCharacterTypes();
  private final CharacterTypeGson gson;

  @SuppressWarnings("UnusedParameters")
  public HeroTypeCompiler(ObjectFactory objectFactory) {
    this.gson = new CharacterTypeGson();
  }

  @Override
  public String getName() {
    return "Character types";
  }

  @Override
  public String getRecognitionPattern() {
    return TEMPLATE_FILE_RECOGNITION_PATTERN;
  }

  @Override
  public void registerFile(ResourceFile resource) throws Exception {
    CharacterType type = gson.fromJson(resource.getURL());
    types.add(type);
  }

  @Override
  public ExtensibleDataSet build() {
    return types;
  }
}