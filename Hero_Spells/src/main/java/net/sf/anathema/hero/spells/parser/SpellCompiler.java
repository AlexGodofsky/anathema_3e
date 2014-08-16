package net.sf.anathema.hero.spells.parser;

import net.sf.anathema.hero.environment.initialization.ExtensibleDataSet;
import net.sf.anathema.hero.environment.initialization.ExtensibleDataSetCompiler;
import net.sf.anathema.hero.environment.template.TemplateLoader;
import net.sf.anathema.hero.individual.persistence.GenericTemplateLoader;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.library.resources.ResourceFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@net.sf.anathema.platform.initialization.ExtensibleDataSetCompiler
public class SpellCompiler implements ExtensibleDataSetCompiler {
  private static final String Spell_File_Recognition_Pattern = ".+?\\.spells";
  private final ListSpellCache cache = new ListSpellCache();
  private final TemplateLoader<SpellListTemplate> loader = new GenericTemplateLoader<>(SpellListTemplate.class);
  private final SpellBuilder spellBuilder = new SpellBuilder();

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
    return cache;
  }

  @Override
  public void registerFile(ResourceFile resource) {
    SpellListTemplate template = loadTemplate(resource);
    List<Spell> spells = spellBuilder.buildSpells(template);
    spells.forEach(cache::addSpell);
  }

  private SpellListTemplate loadTemplate(ResourceFile resource) {
    try (InputStream inputStream = resource.getURL().openStream()) {
      return loader.load(inputStream);
    } catch (IOException e) {
      throw new PersistenceException(e);
    }
  }
}