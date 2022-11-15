package sb.bdev.ui.directorybrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreeModel;

public final class FileTree extends JTree {

    public FileTree(TreeModel model) {
        setRootVisible(false);
        setEditable(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        setModel(model);
    }
}
