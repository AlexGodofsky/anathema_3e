package net.sf.anathema.hero.charms.compiler.special;

import net.sf.anathema.hero.charms.model.special.ISpecialCharm;
import net.sf.anathema.hero.magic.parser.dto.special.SpecialCharmDto;
import net.sf.anathema.framework.environment.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

public class ReflectionSpecialCharmBuilder {

  private final List<SpecialCharmBuilder> builders = new ArrayList<>();

  public ReflectionSpecialCharmBuilder(ObjectFactory objectFactory) {
    this.builders.addAll(objectFactory.instantiateAllImplementers(SpecialCharmBuilder.class));
  }

  public ISpecialCharm readCharm(SpecialCharmDto overallDto) {
    return findBuilder(overallDto).readCharm(overallDto);
  }

  private SpecialCharmBuilder findBuilder(SpecialCharmDto dto) {
    for (SpecialCharmBuilder builder : builders) {
      if (builder.supports(dto)) {
        return builder;
      }
    }
    return new NullSpecialCharmBuilder();
  }
}