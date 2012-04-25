package net.sf.anathema.framework.repository.tree;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import net.disy.commons.core.util.ArrayUtilities;
import net.disy.commons.core.util.ITransformer;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.view.PrintNameFile;
import net.sf.anathema.lib.gui.Presenter;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.util.TreeUtilities;

public class RepositoryTreePresenter implements Presenter {

  private final DefaultMutableTreeNode root;
  private final DefaultTreeModel treeModel;
  private final IRepositoryTreeModel repositoryModel;
  private final IRepositoryTreeView treeView;
  private final TreeCellRenderer renderer;

  public RepositoryTreePresenter(
      IResources resources,
      IRepositoryTreeModel repositoryModel,
      IRepositoryTreeView treeView,
      TreeCellRenderer renderer,
      String rootKey) {
    this.repositoryModel = repositoryModel;
    this.treeView = treeView;
    this.renderer = renderer;
    this.root = new DefaultMutableTreeNode(resources.getString(rootKey) + " [" //$NON-NLS-1$
        + repositoryModel.getRepositoryPath()
        + "]", true); //$NON-NLS-1$
    this.treeModel = new DefaultTreeModel(root);
    repositoryModel.addRepositoryTreeModelListener(new IRepositoryTreeModelListener() {
      @Override
      public void printNameFileAdded(PrintNameFile file) {
        addPrintNameFileToTree(file);
      }

      @Override
      public void printNameFileRemoved(PrintNameFile file) {
        removePrintNameFileFromTree(file);
      }
    });
  }

  @Override
  public void initPresentation() {
    for (IItemType type : repositoryModel.getAllItemTypes()) {
      if (type.supportsRepository()) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(type);
        treeModel.insertNodeInto(node, root, 0);
        for (PrintNameFile file : repositoryModel.getPrintNameFiles(type)) {
          addPrintNameFileToTree(file);
        }
      }
    }
    final JTree tree = treeView.addTree();
    tree.setModel(treeModel);
    tree.setCellRenderer(renderer);
    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    tree.setCellRenderer(renderer);
    tree.addTreeSelectionListener(new TreeSelectionListener() {
      @Override
      public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode[] nodes = TreeUtilities.getSelectedHierachyNodes(tree);
        if (nodes.length == 0) {
          repositoryModel.setSelectedObject(new Object[0]);
        }
        else {
          repositoryModel.setSelectedObject(ArrayUtilities.transform(
              nodes,
              Object.class,
              new ITransformer<DefaultMutableTreeNode, Object>() {
                @Override
                public Object transform(DefaultMutableTreeNode input) {
                  return input.getUserObject();
                }
              }));
        }
      }
    });
  }

  private void addPrintNameFileToTree(PrintNameFile file) {
    for (int index = 0; index < root.getChildCount(); index++) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(index);
      if (file.getItemType() == node.getUserObject()) {
        treeModel.insertNodeInto(new DefaultMutableTreeNode(file, false), node, node.getChildCount());
      }
    }
  }

  private void removePrintNameFileFromTree(PrintNameFile file) {
    MutableTreeNode foundNode = null;
    for (int typeIndex = 0; typeIndex < root.getChildCount(); typeIndex++) {
      DefaultMutableTreeNode typeNode = (DefaultMutableTreeNode) root.getChildAt(typeIndex);
      if (file.getItemType() == typeNode.getUserObject()) {
        for (int fileIndex = 0; fileIndex < typeNode.getChildCount(); fileIndex++) {
          DefaultMutableTreeNode fileNode = (DefaultMutableTreeNode) typeNode.getChildAt(fileIndex);
          if (file == fileNode.getUserObject()) {
            foundNode = fileNode;
            break;
          }
        }
        break;
      }
    }
    if (foundNode != null) {
      treeModel.removeNodeFromParent(foundNode);
    }
  }
}
