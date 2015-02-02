package net.sf.anathema.hero.display.fx.perspective;

import javafx.scene.Node;
import javafx.scene.control.Button;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.sf.anathema.hero.application.SubViewRegistry;
import net.sf.anathema.hero.display.fx.HeroViewSection;
import net.sf.anathema.hero.individual.view.HeroView;
import net.sf.anathema.hero.individual.view.SectionView;
import net.sf.anathema.library.fx.NodeHolder;
import net.sf.anathema.library.fx.Stylesheet;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Collection;

import static net.sf.anathema.library.fx.layout.LayoutUtils.fillWithoutInsets;
import static net.sf.anathema.library.fx.layout.LayoutUtils.withoutInsets;

public class GroupedHeroView implements HeroView, NodeHolder {

  private final TaskedHeroPane taskedHeroPane = new TaskedHeroPane();
  private MigPane content;
  private final SubViewRegistry subViewFactory;
  private final Collection<Stylesheet> stylesheets;

  public GroupedHeroView(SubViewRegistry viewFactory, Collection<Stylesheet> stylesheets) {
    this.subViewFactory = viewFactory;
    this.stylesheets = stylesheets;
  }

  @Override
  public SectionView addSection(String title) {
    return new HeroViewSection(taskedHeroPane, title, subViewFactory);
  }

  @Override
  public Node getNode() {
    if (content == null) {
      content = new MigPane(fillWithoutInsets().wrapAfter(1), new AC().index(0).shrink().shrinkPrio(200));
      stylesheets.forEach(sheet -> sheet.applyToParent(content));
      new Stylesheet("skin/character/hero-view.css").applyToParent(content);
      content.add(createNavigationBar(), new CC().growX());
      content.add(taskedHeroPane.getNode(), new CC().grow().push());
    }
    return content;
  }

  private Node createNavigationBar() {
    MigPane bar = new MigPane(withoutInsets().gridGap("10", "0"));
    bar.getStyleClass().add("hero-link-bar");
    bar.getChildren().add(createNavigationLabel("Background"));
    bar.getChildren().add(createNavigationLabel("Mundane"));
    bar.getChildren().add(createNavigationLabel("Spiritual"));
    bar.getChildren().add(createNavigationLabel("Charms"));
    bar.getChildren().add(createNavigationLabel("Sorcery"));
    bar.getChildren().add(createNavigationLabel("Panoply"));
    return bar;
  }

  private Node createNavigationLabel(String text) {
    Button button = new Button(text);
    button.getStyleClass().add("hero-link-button");
    return button;
  }
}