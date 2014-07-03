package net.sf.anathema.character.main.magic.parser.charms.special;

import net.sf.anathema.charm.parser.template.special.SpecialCharmTemplate;
import net.sf.anathema.hero.magic.parser.charms.special.ReflectionSpecialCharmParser;
import net.sf.anathema.hero.magic.parser.charms.special.SpecialCharmParser;
import net.sf.anathema.framework.environment.ConfigurableDummyObjectFactory;
import org.dom4j.Element;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class ReflectionSpecialCharmParserTest {

  private ConfigurableDummyObjectFactory factory = new ConfigurableDummyObjectFactory();

  @Test
  public void returnsCharmFromRegisteredBuilder() {
    Element element = mock(Element.class);
    registerBuilderForIdYieldingDto(element, "testId");
    SpecialCharmTemplate result = new ReflectionSpecialCharmParser(factory).readCharmDto(element, "testId");
    assertThat(result.charmId, is("testId"));
  }

  private void registerBuilderForIdYieldingDto(Element element, String id) {
    ConfigurableDummySpecialCharmParser parser = new ConfigurableDummySpecialCharmParser();
    parser.support(element).with(id);
    factory.add(SpecialCharmParser.class, parser);
  }
}
