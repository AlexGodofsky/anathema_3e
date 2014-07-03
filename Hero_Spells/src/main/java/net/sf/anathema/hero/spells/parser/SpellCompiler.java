package net.sf.anathema.hero.spells.parser;

import net.sf.anathema.framework.environment.ObjectFactory;
import net.sf.anathema.framework.environment.resources.ResourceFile;
import net.sf.anathema.hero.framework.data.ExtensibleDataSet;
import net.sf.anathema.hero.framework.data.IExtensibleDataSetCompiler;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.initialization.ExtensibleDataSetCompiler;
import net.sf.anathema.lib.exception.PersistenceException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.List;

@ExtensibleDataSetCompiler
public class SpellCompiler implements IExtensibleDataSetCompiler {
  private static final String Spell_File_Recognition_Pattern = "Spells.+\\.xml";
  private final List<Document> spellFileList = new ArrayList<>();
  private final SpellBuilder builder = new SpellBuilder();
  private final SAXReader reader = new SAXReader();
  private final SpellCache cache = new SpellCache();

  @SuppressWarnings("UnusedParameters")
  public SpellCompiler(ObjectFactory objectFactory) {
    //nothing to do
  }

  @Override
  public String getName() {
    return "Spells";
  }

  @Override
  public String getRecognitionPattern() {
    return Spell_File_Recognition_Pattern;
  }

  @Override
  public ExtensibleDataSet build() {
    for (Document document : spellFileList) {
      Spell[] spells = builder.buildSpells(document);
      for (Spell spell : spells) {
        cache.addSpell(spell);
      }
    }
    return cache;
  }

  @Override
  public void registerFile(ResourceFile resource) {
    try {
      spellFileList.add(reader.read(resource.getURL()));
    } catch (DocumentException e) {
      throw new PersistenceException(resource.getURL().toExternalForm(), e);
    }
  }
}