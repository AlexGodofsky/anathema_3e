package net.sf.anathema;

import com.google.inject.Inject;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.sf.anathema.hero.framework.Character;
import net.sf.anathema.hero.framework.item.Item;
import net.sf.anathema.hero.framework.persistence.HeroItemPersister;
import net.sf.anathema.hero.framework.persistence.RepositoryItemPersister;
import net.sf.anathema.hero.framework.type.CharacterTypes;
import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.fx.hero.perspective.CharacterSystemInitializer;
import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.hero.framework.HeroEnvironmentExtractor;
import net.sf.anathema.hero.template.HeroTemplate;
import net.sf.anathema.hero.template.TemplateTypeImpl;
import net.sf.anathema.lib.util.SimpleIdentifier;

public class CharacterCreationSteps {
  private CharacterTypes characterTypes;
  private IApplicationModel model;
  private final CharacterHolder holder;

  @Inject
  public CharacterCreationSteps(CharacterHolder holder) {
    this.holder = holder;
  }

  @Before
  public void startAnathema() {
    TestInitializer initializer = TestInitializer.Create();
    this.model = initializer.initialize();
    new CharacterSystemInitializer(model,initializer.getEnvironment()).initializeCharacterSystem();
    this.characterTypes = HeroEnvironmentExtractor.getGenerics(model).getCharacterTypes();
  }

  @Given("Anathema is running")
  public void Anathema_is_running() {
    //for readable tests only
  }

  @Given("^a new God-Blooded of any kind$")
  public void I_create_a_new_god_blooded() throws Throwable {
    I_create_a_new_character_with_subtype("Lunar", "HalfCasteLunar");
  }

  @Given("^a new (.*) using rules for (.*)$")
  public void I_create_a_new_character_with_subtype(String type, String subtype) throws Throwable {
    Character character = createCharacter(type, subtype);
    holder.setCharacter(character);
  }

  @Then("^I can create a new (.*) using rules for (.*)$")
  public void I_can_create_a_new_character(String type, String subtype) throws Throwable {
    createCharacter(type, subtype);
  }


  @Given("^a new Character of any kind$")
  public void a_new_Character_of_any_kind() throws Throwable {
    I_create_a_new_character_with_subtype("Solar", "RookieLawgiver");
  }

  private Character createCharacter(String type, String subtype) {
    HeroTemplate characterTemplate = loadTemplateForType(type, subtype);
    return createCharacter(characterTemplate);
  }

  private HeroTemplate loadTemplateForType(String type, String subtype) {
    HeroEnvironment generics = getCharacterGenerics();
    return generics.getTemplateRegistry().getTemplate(new TemplateTypeImpl(characterTypes.findById(type), new SimpleIdentifier(subtype)));
  }

  private Character createCharacter(HeroTemplate template) {
    RepositoryItemPersister itemPersister = new HeroItemPersister(getCharacterGenerics(), model.getMessaging());
    Item item = itemPersister.createNew(template);
    return (Character) item.getItemData();
  }

  private HeroEnvironment getCharacterGenerics() {
    return HeroEnvironmentExtractor.getGenerics(model);
  }
}