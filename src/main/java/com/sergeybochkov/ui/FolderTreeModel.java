package com.sergeybochkov.ui;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

public class FolderTreeModel implements TreeModel {

    private File root = new File("<root>");
    private Vector<TreeModelListener> listeners = new Vector<>();
    private File[] roots = File.listRoots();

    private FolderTreeModel() {}

    public static Builder newBuilder() {
        return new FolderTreeModel().new Builder();
    }

    private FilenameFilter filter = (dir, name) -> {
        File curFile = new File(dir, name);
        return curFile.isDirectory() && !curFile.isHidden();
    };

    private void excludeRoots(String... excluded) {
        int count = 0;
        for (int i = 0; i < roots.length; ++i)
            for (String exclude : excluded)
                if (roots[i] != null && roots[i].toString().startsWith(exclude)) {
                    roots[i] = null;
                    ++count;
                }

        File[] newRoots = new File[roots.length - count];
        for (int i = 0, j = 0; i < roots.length; ++i) {
            if (roots[i] != null) {
                newRoots[j] = roots[i];
                ++j;
            }
        }
        this.roots = newRoots;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (root.equals(parent))
            return roots[index];
        else {
            File dir = (File) parent;
            String[] children = dir.list(filter);
            return new TreeFile(dir, children[index]);
        }
    }

    @Override
    public int getChildCount(Object parent) {
        if (root.equals(parent))
            return roots.length;
        else {
            File file = (File) parent;
            String[] fileList = file.list(filter);
            if (fileList != null)
                return fileList.length;
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        for (File file : roots)
            if (file.equals(node))
                return false;

        File file = (File) node;
        return !file.isDirectory() && getChildCount(node) == 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) newValue;
        File targetFile = new File(fileParentPath, newFileName);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
        Object[] changedChildren = { targetFile };
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);
    }

    private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        for (TreeModelListener listener : listeners)
            listener.treeNodesChanged(event);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File dir = (File) parent;
        File file = (File) child;
        String[] children = dir.list(filter);
        for (int i = 0; i < children.length; ++i)
            if (file.getName().equals(children[i]))
                return i;

        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

    private class TreeFile extends File {

        public TreeFile(File parent, String child) {
            super(parent, child);
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    public class Builder {

        private Builder() {}

        public File[] roots() {
            return roots;
        }

        public Builder exclude(String... excluded) {
            FolderTreeModel.this.excludeRoots(excluded);
            return this;
        }

        public FolderTreeModel build() {
            if (FolderTreeModel.this.roots == null)
                FolderTreeModel.this.roots = File.listRoots();
            return FolderTreeModel.this;
        }
    }
}
