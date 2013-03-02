package net.sf.anathema.character.impl.view.magic;

import com.google.common.base.Preconditions;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import net.sf.anathema.character.generic.framework.magic.view.IMagicViewListener;
import net.sf.anathema.character.generic.framework.magic.view.MagicLearnView;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.view.magic.IComboConfigurationView;
import net.sf.anathema.character.view.magic.IComboView;
import net.sf.anathema.character.view.magic.IComboViewListener;
import net.sf.anathema.character.view.magic.IComboViewProperties;
import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.gui.layout.LayoutUtils;
import net.sf.anathema.lib.workflow.textualdescription.ITextView;
import net.sf.anathema.lib.workflow.textualdescription.SwingTextView;
import net.sf.anathema.lib.workflow.textualdescription.view.AreaTextView;
import net.sf.anathema.lib.workflow.textualdescription.view.LineTextView;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jmock.example.announcer.Announcer;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Component;

import static net.sf.anathema.lib.gui.swing.GuiUtilities.revalidate;

public class ComboConfigurationView implements IComboConfigurationView {
  private static final int TEXT_COLUMNS = 20;
  private MagicLearnView magicLearnView = new MagicLearnView();
  private JComponent content;
  private final Announcer<IComboViewListener> comboViewListeners = Announcer.to(IComboViewListener.class);
  private final JPanel namePanel = new JPanel(new MigLayout(LayoutUtils.withoutInsets().wrapAfter(1)));
  private JButton clearButton;
  private JButton finalizeButton;
  private int learnedListModelSize;
  private boolean isNameEntered;
  private boolean isDescriptionEntered;
  private final JXTaskPaneContainer comboPane = new JXTaskPaneContainer();
  private JScrollPane comboScrollPane;
  private IComboViewProperties properties;

  @Override
  public void initGui(final IComboViewProperties viewProperties) {
    this.properties = viewProperties;
    magicLearnView = new MagicLearnView() {
      @Override
      protected ListSelectionListener createLearnedListListener(final JButton button, final JList list) {
        return new ListSelectionListener() {
          @Override
          public void valueChanged(ListSelectionEvent e) {
            button.setEnabled(!list.isSelectionEmpty() && viewProperties.isRemoveButtonEnabled((ICharm) list.getSelectedValue()));
          }
        };
      }
    };
    magicLearnView.init(viewProperties);
    finalizeButton = createFinalizeComboButton(viewProperties.getFinalizeButtonIcon());
    finalizeButton.setToolTipText(viewProperties.getFinalizeButtonToolTip());
    clearButton = createClearButton(viewProperties.getClearButtonIcon());
    clearButton.setToolTipText(viewProperties.getClearButtonToolTip());
    final ListModel learnedListModel = magicLearnView.getLearnedListModel();
    learnedListModel.addListDataListener(new ListDataListener() {
      @Override
      public void intervalAdded(ListDataEvent e) {
        updateClearAndFinalize();
      }

      @Override
      public void intervalRemoved(ListDataEvent e) {
        updateClearAndFinalize();
      }

      @Override
      public void contentsChanged(ListDataEvent e) {
        updateClearAndFinalize();
      }

      private void updateClearAndFinalize() {
        learnedListModelSize = learnedListModel.getSize();
        finalizeButton.setEnabled(learnedListModelSize > 1);
        clearButton.setEnabled(isDescriptionEntered || isNameEntered || learnedListModelSize > 0);
      }
    });
    JPanel viewPort = new JPanel(new MigLayout(new LC().insets("6").fill().wrapAfter(5)));
    viewPort.add(new JLabel(viewProperties.getAvailableComboCharmsLabel()));
    viewPort.add(new JLabel());
    viewPort.add(new JLabel(viewProperties.getComboedCharmsLabel()));
    viewPort.add(new JLabel());
    viewPort.add(namePanel, new CC().spanY(2).alignY("top"));
    magicLearnView.addTo(viewPort);
    comboPane.setBackground(viewPort.getBackground());
    comboScrollPane = new JScrollPane(comboPane);
    viewPort.add(comboScrollPane, new CC().spanX().grow().push());
    content = new JScrollPane(viewPort);
  }

  private JButton createClearButton(Icon icon) {
    Action smartAction = new SmartAction(icon) {
      @Override
      protected void execute(Component parentComponent) {
        fireComboCleared();
      }
    };
    smartAction.setEnabled(false);
    return magicLearnView.addAdditionalAction(smartAction);
  }

  private void fireComboCleared() {
    comboViewListeners.announce().comboCleared();
  }

  private JButton createFinalizeComboButton(Icon icon) {
    Action smartAction = new SmartAction(icon) {
      @Override
      protected void execute(Component parentComponent) {
        fireComboFinalized();
      }
    };
    smartAction.setEnabled(false);
    return magicLearnView.addAdditionalAction(smartAction);
  }

  private void fireComboFinalized() {
    comboViewListeners.announce().comboFinalized();
  }

  @Override
  public void setAllCharms(Object[] charms) {
    magicLearnView.setMagicOptions(charms);
  }

  @Override
  public JComponent getComponent() {
    return content;
  }

  @Override
  public void addComboViewListener(final IComboViewListener listener) {
    magicLearnView.addMagicViewListener(new IMagicViewListener() {
      @Override
      public void magicRemoved(Object[] removedMagic) {
        listener.charmRemoved(removedMagic);
      }

      @Override
      public void magicAdded(Object[] addedMagic) {
        Preconditions.checkArgument(addedMagic.length == 1, "Only one charm may be added."); //$NON-NLS-1$
        listener.charmAdded(addedMagic[0]);
      }
    });
    comboViewListeners.addListener(listener);
  }

  @Override
  public void setComboCharms(Object[] charms) {
    magicLearnView.setLearnedMagic(charms);
  }

  @Override
  public ITextView addComboNameView(String viewTitle) {
    LineTextView textView = new LineTextView(TEXT_COLUMNS);
    textView.addTextChangedListener(new ObjectValueListener<String>() {
      @Override
      public void valueChanged(String newValue) {
        isNameEntered = newValue != null && !newValue.equals(""); //$NON-NLS-1$
        clearButton.setEnabled(isDescriptionEntered || isNameEntered || learnedListModelSize > 0);
      }
    });
    return addTextView(viewTitle, textView);
  }

  @Override
  public IComboView addComboView(String name, String description, Action deleteAction, Action editAction) {
    ComboView comboView = new ComboView(deleteAction, editAction);
    comboView.initGui(name, description);
    comboPane.add(comboView.getTaskGroup());
    revalidateView();
    return comboView;
  }

  private void revalidateView() {
    revalidate(comboPane);
    revalidate(comboScrollPane);
  }

  private ITextView addTextView(String viewTitle, SwingTextView textView) {
    namePanel.add(new JLabel(viewTitle));
    namePanel.add(textView.getComponent(), new CC().growX());
    return textView;
  }

  @Override
  public ITextView addComboDescriptionView(String viewTitle) {
    AreaTextView textView = new AreaTextView(5, TEXT_COLUMNS);
    textView.addTextChangedListener(new ObjectValueListener<String>() {
      @Override
      public void valueChanged(String newValue) {
        isDescriptionEntered = newValue != null && !newValue.equals(""); //$NON-NLS-1$
        clearButton.setEnabled(isDescriptionEntered || isNameEntered || learnedListModelSize > 0);
      }
    });
    return addTextView(viewTitle, textView);
  }

  @Override
  public void deleteView(IComboView view) {
    ComboView comboView = (ComboView) view;
    comboPane.remove(comboView.getTaskGroup());
    revalidateView();
  }

  @Override
  public void setEditState(boolean editing) {
    clearButton.setIcon(editing ? properties.getCancelEditButtonIcon() : properties.getClearButtonIcon());
    clearButton.setToolTipText(editing ? properties.getCancelButtonEditToolTip() : properties.getClearButtonToolTip());
    finalizeButton.setToolTipText(editing ? properties.getFinalizeButtonEditToolTip() : properties.getFinalizeButtonToolTip());
  }
}