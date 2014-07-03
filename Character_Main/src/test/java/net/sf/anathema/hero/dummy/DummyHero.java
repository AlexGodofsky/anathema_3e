package net.sf.anathema.hero.dummy;

import net.sf.anathema.hero.dummy.template.DummyHeroTemplate;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.hero.model.change.ChangeAnnouncer;
import net.sf.anathema.hero.model.change.ChangeAnnouncerImpl;
import net.sf.anathema.hero.template.HeroTemplate;
import net.sf.anathema.lib.util.Identifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DummyHero implements Hero {

  private final ChangeAnnouncer changeAnnouncer = new ChangeAnnouncerImpl();

  public final DummyHeroTemplate template = new DummyHeroTemplate();
  public final Map<Identifier, HeroModel> modelsById = new HashMap<>();


  public void addModel(HeroModel model) {
    modelsById.put(model.getId(), model);
  }

  @Override
  public HeroTemplate getTemplate() {
    return template;
  }

  @Override
  public ChangeAnnouncer getChangeAnnouncer() {
    return changeAnnouncer;
  }

  @Override
  public <M extends HeroModel> M getModel(Identifier id) {
    return (M) modelsById.get(id);
  }

  @Override
  public boolean isFullyLoaded() {
    return true;
  }

  @Override
  public Iterator<HeroModel> iterator() {
    return modelsById.values().iterator();
  }
}
