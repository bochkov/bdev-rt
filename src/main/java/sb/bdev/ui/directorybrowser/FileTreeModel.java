package sb.bdev.ui.directorybrowser;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileTreeModel implements TreeModel {

    private final File root = new File("!"); // тут нужен какой-нибудь файл, который в реальности существовать не может
    private final List<File> roots = new ArrayList<>(Arrays.asList(File.listRoots()));
    private final List<TreeModelListener> listeners = new ArrayList<>();
    private final FilenameFilter filter = (dir, name) -> {
        File curFile = new File(dir, name);
        return curFile.isDirectory() && !curFile.isHidden();
    };

    public void excludeRoots(List<String> excluded) {
        Iterator<File> fileIter = roots.iterator();
        while (fileIter.hasNext()) {
            for (String ex : excluded) {
                if (fileIter.toString().startsWith(ex)) {
                    fileIter.remove();
                }
            }
        }
        for (TreeModelListener listener : listeners) {
            listener.treeStructureChanged(new TreeModelEvent(this, new TreePath(root)));
        }
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (root.equals(parent))
            return roots.get(index);
        else {
            File dir = (File) parent;
            String[] children = dir.list(filter);
            return children != null && index < children.length ?
                    new TreeFile(dir, children[index]) :
                    new TreeFile(dir, "<NONE>");
        }
    }

    @Override
    public int getChildCount(Object parent) {
        if (root.equals(parent))
            return roots.size();
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
        Object[] changedChildren = {targetFile};
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
        for (int i = 0; children != null && i < children.length; ++i)
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

    private static final class TreeFile extends File {

        public TreeFile(File parent, String child) {
            super(parent, child);
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
