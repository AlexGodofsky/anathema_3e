package net.sf.anathema.character.library.virtueflaw.presenter;

import net.sf.anathema.character.generic.framework.additionaltemplate.listening.VirtueChangeListener;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.library.trait.presenter.TraitPresenter;
import net.sf.anathema.character.library.trait.visitor.IDefaultTrait;
import net.sf.anathema.character.library.virtueflaw.model.IVirtueFlaw;
import net.sf.anathema.framework.value.IIntValueView;
import net.sf.anathema.framework.view.AbstractSelectCellRenderer;
import net.sf.anathema.lib.control.IBooleanValueChangedListener;
import net.sf.anathema.lib.control.IChangeListener;
import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.Presenter;
import net.sf.anathema.lib.gui.selection.IObjectSelectionView;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.workflow.textualdescription.ITextView;
import net.sf.anathema.lib.workflow.textualdescription.TextualPresentation;

import java.awt.Component;

public class VirtueFlawPresenter implements Presenter {

  private final IResources resources;
  private final IVirtueFlawView view;
  private final IVirtueFlawModel model;

  public VirtueFlawPresenter(IResources resources, IVirtueFlawView virtueFlawView, IVirtueFlawModel model) {
    this.resources = resources;
    this.view = virtueFlawView;
    this.model = model;
  }

  @Override
  public void initPresentation() {
    initBasicPresentation();
    initAdditionalPresentation();
    initChangeableListening();
    initLimitPresentation(model.getVirtueFlaw());
  }

  protected void initAdditionalPresentation() {
    // Nothing to do
  }

  protected void initBasicPresentation() {
    IVirtueFlaw virtueFlaw = model.getVirtueFlaw();
   	initRootPresentation(virtueFlaw);
    initNamePresentation(virtueFlaw);
  }
  
  protected void initLimitPresentation(IVirtueFlaw virtueFlaw)
  {
	IDefaultTrait trait = virtueFlaw.getLimitTrait();
	IIntValueView traitView = view.addLimitValueView(
	          getResources().getString(trait.getType().getId()),
	          trait.getCurrentValue(),
	          trait.getMaximalValue());
	new TraitPresenter(trait, traitView).initPresentation();
  }

  protected void initRootPresentation(IVirtueFlaw virtueFlaw) {
    initRootPresentation(virtueFlaw, "VirtueFlaw.Root.Name");
  }
  protected void initRootPresentation(final IVirtueFlaw virtueFlaw, String nameReference) {
    final IObjectSelectionView<ITraitType> rootView = view.addVirtueFlawRootSelectionView(
        resources.getString(nameReference), 
        new AbstractSelectCellRenderer<ITraitType>(resources) {
          private static final long serialVersionUID = -5708766075692361407L;

          @Override
          protected String getCustomizedDisplayValue(ITraitType value) {
            return resources.getString("VirtueType.Name." + value.getId()); //$NON-NLS-1$
          }

          @Override
          public Component getListCellRendererComponent(
              javax.swing.JList list,
              Object value,
              int index,
              boolean isSelected,
              boolean cellHasFocus) {
            return super.getListCellRendererComponent(
                list,
                value,
                index,
                isSelected,
                cellHasFocus);
          }
        });
    virtueFlaw.addRootChangeListener(new IChangeListener() {
      @Override
      public void changeOccurred() {
        rootView.setSelectedObject(virtueFlaw.getRoot());
      }
    });
    rootView.addObjectSelectionChangedListener(new ObjectValueListener<ITraitType>() {
      @Override
      public void valueChanged(ITraitType newValue) {
        virtueFlaw.setRoot(newValue);
      }
    });
    model.addVirtueChangeListener(new VirtueChangeListener() {
      @Override
      public void configuredChangeOccured() {
        updateRootView(rootView);
      }
    });
    updateRootView(rootView);
  }

  private void updateRootView(IObjectSelectionView<ITraitType> rootView) {
    ITraitType rootCopy = model.getVirtueFlaw().getRoot();
    rootView.setObjects(model.getFlawVirtueTypes());
    rootView.setSelectedObject(rootCopy);
  }

  protected ITextView initNamePresentation(IVirtueFlaw virtueFlaw) {
    ITextView titleView = view.addTextView(resources.getString("VirtueFlaw.Name.Name"), 30); //$NON-NLS-1$
    new TextualPresentation().initView(titleView, virtueFlaw.getName());
    return titleView;
  }

  protected void initChangeableListening() {
    model.addVirtueFlawChangableListener(new IBooleanValueChangedListener() {
      @Override
      public void valueChanged(boolean newValue) {
        view.setEnabled(newValue);
      }
    });
    view.setEnabled(model.isVirtueFlawChangable());
  }

  protected final IVirtueFlawModel getModel() {
    return model;
  }

  protected final IResources getResources() {
    return resources;
  }
}
