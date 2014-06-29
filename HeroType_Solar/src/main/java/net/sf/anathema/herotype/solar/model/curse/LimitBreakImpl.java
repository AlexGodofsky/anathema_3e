package net.sf.anathema.herotype.solar.model.curse;

import net.sf.anathema.hero.traits.model.DefaultTraitType;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.FriendlyIncrementChecker;
import net.sf.anathema.hero.traits.model.trait.ModificationType;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.traits.model.trait.LimitedTrait;
import net.sf.anathema.hero.traits.template.TraitTemplate;
import net.sf.anathema.hero.traits.template.TraitTemplateFactory;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.workflow.textualdescription.ITextualDescription;
import net.sf.anathema.lib.workflow.textualdescription.SimpleTextualDescription;
import org.jmock.example.announcer.Announcer;

public class LimitBreakImpl implements LimitBreak {

  private TraitType root;
  private Trait limitTrait;
  private final ITextualDescription name = new SimpleTextualDescription("");
  private final Announcer<ChangeListener> control = Announcer.to(ChangeListener.class);
  private Hero hero;

  public LimitBreakImpl(Hero hero) {
    this.hero = hero;
  }

  @Override
  public TraitType getRoot() {
    return root;
  }

  @Override
  public void setRoot(TraitType root) {
    this.root = root;
    control.announce().changeOccurred();
  }

  @Override
  public Trait getLimitTrait() {
    if (limitTrait == null) {
      DefaultTraitType traitType = new DefaultTraitType(getLimitString());
      TraitTemplate limitedTemplate = TraitTemplateFactory.createStaticLimitedTemplate(0, 10, ModificationType.Free);
      limitTrait = new LimitedTrait(hero, traitType, limitedTemplate, new FriendlyIncrementChecker());
    }
    return limitTrait;
  }

  protected String getLimitString() {
    return "VirtueFlaw.LimitTrait";
  }

  @Override
  public ITextualDescription getName() {
    return name;
  }
}