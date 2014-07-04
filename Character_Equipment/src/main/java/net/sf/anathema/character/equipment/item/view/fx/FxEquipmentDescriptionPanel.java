package net.sf.anathema.character.equipment.item.view.fx;

import javafx.scene.Node;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.sf.anathema.character.equipment.item.view.CostSelectionView;
import net.sf.anathema.character.equipment.item.view.EquipmentDescriptionPanel;
import net.sf.anathema.equipment.core.MagicalMaterial;
import net.sf.anathema.equipment.core.MaterialComposition;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.library.text.ITextView;
import net.sf.anathema.library.view.ObjectSelectionView;
import net.sf.anathema.platform.fx.FxObjectSelectionView;
import net.sf.anathema.platform.fx.FxTextView;
import net.sf.anathema.platform.fx.selection.SelectionViewFactory;
import org.tbee.javafx.scene.layout.MigPane;

public class FxEquipmentDescriptionPanel implements EquipmentDescriptionPanel {

  private final SelectionViewFactory selectionViewFactory;
  private MigPane pane;

  public FxEquipmentDescriptionPanel(SelectionViewFactory selectionFactory) {
    this.selectionViewFactory = selectionFactory;
    pane = new MigPane(new LC().wrapAfter(1).fill().insets("4"), new AC(), new AC().index(1).shrinkPrio(200));
  }

  @Override
  public ITextView addNameView(String label) {
    final FxTextView view = FxTextView.SingleLine(label);
    pane.add(view.getNode(), new CC().growX().pushY().span());
    return view;
  }

  @Override
  public ITextView addDescriptionView(String label) {
    final FxTextView view = FxTextView.MultiLine(label);
    pane.add(view.getNode(), new CC().growX().pushY().span());
    return view;
  }

  @Override
  public ObjectSelectionView<MaterialComposition> addCompositionView(String label,
                                                                     AgnosticUIConfiguration<MaterialComposition> ui) {
    final FxObjectSelectionView<MaterialComposition> selectionView = selectionViewFactory.create(label, ui);
    pane.add(selectionView.getNode(), new CC().split());
    return selectionView;
  }

  @Override
  public ObjectSelectionView<MagicalMaterial> addMaterialView(String label,
                                                              AgnosticUIConfiguration<MagicalMaterial> ui) {
    final FxObjectSelectionView<MagicalMaterial> selectionView = selectionViewFactory.create(label, ui);
    pane.add(selectionView.getNode(), new CC().growX().wrap());
    return selectionView;
  }

  @Override
  public CostSelectionView addCostView(String label) {
    final FxCostSelectionView costSelectionView = new FxCostSelectionView(label, selectionViewFactory);
    pane.add(costSelectionView.getNode(), new CC().split(2).pushX());
    return costSelectionView;
  }

  public Node getNode() {
    return pane;
  }
}