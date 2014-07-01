package net.sf.anathema.hero.charms.compiler;

import net.sf.anathema.hero.framework.type.CharacterTypes;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.magic.charm.CharmException;
import net.sf.anathema.hero.magic.charm.CharmImpl;
import net.sf.anathema.hero.magic.parser.charms.CharmAlternativeBuilder;
import net.sf.anathema.hero.magic.parser.charms.CharmMergedBuilder;
import net.sf.anathema.hero.magic.parser.charms.CharmSetBuilder;
import net.sf.anathema.hero.magic.parser.charms.IIdentificateRegistry;
import net.sf.anathema.hero.magic.parser.charms.IdentificateRegistry;
import net.sf.anathema.hero.magic.parser.charms.special.ReflectionSpecialCharmParser;
import net.sf.anathema.hero.magic.parser.dto.special.SpecialCharmDto;
import net.sf.anathema.framework.environment.ObjectFactory;
import net.sf.anathema.framework.environment.resources.ResourceFile;
import net.sf.anathema.hero.charms.compiler.special.ReflectionSpecialCharmBuilder;
import net.sf.anathema.hero.framework.data.ExtensibleDataSet;
import net.sf.anathema.hero.framework.data.IExtensibleDataSetCompiler;
import net.sf.anathema.hero.framework.data.IExtensibleDataSetProvider;
import net.sf.anathema.initialization.ExtensibleDataSetCompiler;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtensibleDataSetCompiler
public class CharmCompiler implements IExtensibleDataSetCompiler {
  private static final String Charm_File_Recognition_Pattern = "Charms.*\\.xml";
  //matches stuff like data/charms/solar/Charms_Solar_SecondEdition_Occult.xml
  //the pattern is data/charms/REST_OF_PATH/Charms_TYPE_EDITION_ANYTHING.xml
  private static final String Charm_Data_Extraction_Pattern = ".*/Charms_(.*?)_(.*?)(?:_.*)?\\.xml";
  private final Map<Identifier, List<Document>> charmFileTable = new HashMap<>();
  private final IIdentificateRegistry<Identifier> registry = new IdentificateRegistry<>();
  private final CharmAlternativeBuilder alternativeBuilder = new CharmAlternativeBuilder();
  private final CharmMergedBuilder mergedBuilder = new CharmMergedBuilder();
  private final SAXReader reader = new SAXReader();
  private final CharmCacheImpl charmCache;
  private final CharmSetBuilder setBuilder;

  public CharmCompiler(ObjectFactory objectFactory, IExtensibleDataSetProvider provider) {
    ReflectionSpecialCharmBuilder specialCharmBuilder = new ReflectionSpecialCharmBuilder(objectFactory);
    ReflectionSpecialCharmParser specialCharmParser = new ReflectionSpecialCharmParser(objectFactory);
    this.charmCache =  new CharmCacheImpl(specialCharmBuilder);
    CharacterTypes characterTypes = provider.getDataSet(CharacterTypes.class);
    this.setBuilder = new CharmSetBuilder(characterTypes, specialCharmParser);
  }

  @Override
  public String getName() {
    return "Charms";
  }

  @Override
  public String getRecognitionPattern() {
    return Charm_File_Recognition_Pattern;
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Override
  public void registerFile(ResourceFile resource) throws Exception {
    Matcher matcher = Pattern.compile(Charm_Data_Extraction_Pattern).matcher(resource.getFileName());
    matcher.matches();
    String typeString = matcher.group(1);
    Identifier type = new SimpleIdentifier(typeString);
    if (!registry.idRegistered(typeString)) {
      registry.add(type);
    }
    List<Document> list = charmFileTable.get(type);
    if (list == null) {
      list = new ArrayList<>();
      charmFileTable.put(type, list);
    }
    try {
      list.add(reader.read(resource.getURL()));
    } catch (DocumentException e) {
      throw new CharmException(resource.getURL().toExternalForm(), e);
    }
  }

  @Override
  public ExtensibleDataSet build() throws PersistenceException {
    for (Identifier type : registry.getAll()) {
      buildStandardCharms(type);
      buildCharmAlternatives(type);
      buildCharmMerges(type);
    }
    extractParents(charmCache.getCharms());
    return charmCache;
  }

  private void buildStandardCharms(Identifier type) throws PersistenceException {
    if (charmFileTable.containsKey(type)) {
      List<Document> documents = charmFileTable.get(type);
      for (Document charmDocument : documents) {
        buildTypeCharms(type, charmDocument);
      }
    }
  }

  private void buildCharmAlternatives(Identifier type) {
    if (charmFileTable.containsKey(type)) {
      for (Document charmDocument : charmFileTable.get(type)) {
        alternativeBuilder.buildAlternatives(charmDocument, charmCache.getCharms(type));
      }
    }
  }

  private void buildCharmMerges(Identifier type) {
    if (charmFileTable.containsKey(type)) {
      for (Document charmDocument : charmFileTable.get(type)) {
        mergedBuilder.buildMerges(charmDocument, charmCache.getCharms(type));
      }
    }
  }

  private void buildTypeCharms(Identifier type, Document charmDocument) throws PersistenceException {
    List<SpecialCharmDto> specialCharms = new ArrayList<>();
    Charm[] charmArray = setBuilder.buildCharms(charmDocument, specialCharms);
    for (Charm charm : charmArray) {
      charmCache.addCharm(type, charm);
    }
    charmCache.addSpecialCharmData(type, specialCharms);
  }

  private void extractParents(Iterable<Charm> charms) {
    Map<String, CharmImpl> charmsById = new HashMap<>();
    for (Charm charm : charms) {
      charmsById.put(charm.getMagicName().text, (CharmImpl) charm);
    }
    for (CharmImpl charm : charmsById.values()) {
      charm.extractParentCharms(charmsById);
    }
  }
}