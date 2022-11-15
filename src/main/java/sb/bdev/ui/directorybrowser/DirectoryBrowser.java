package sb.bdev.ui.directorybrowser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import sb.bdev.ui.HotKey;
import sb.bdev.ui.common.CmdPanel;
import sb.bdev.ui.layout.GBC;

public class DirectoryBrowser extends JDialog {

    private static final String SEPARATOR = Pattern.quote(File.separator);

    private final AtomicBoolean approved = new AtomicBoolean(Boolean.FALSE);
    private final List<File> selected = new ArrayList<>();

    private final transient FileTreeModel treeModel = new FileTreeModel();
    private final JTree tree = new FileTree(treeModel);
    private final JTextField pathField = new JTextField();

    public DirectoryBrowser(Window parent, String title, ModalityType modalityType) {
        this(parent, title, modalityType, "Choose", "Cancel");
    }

    public DirectoryBrowser(Window parent, String title, ModalityType modalityType, String okText, String cancelText) {
        this(parent, title, modalityType, okText, cancelText, TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    public DirectoryBrowser(Window parent, String title, ModalityType modalityType, String okText, String cancelText, int selectionMode) {
        super(parent, title, modalityType);

        this.pathField.setEditable(false);
        this.tree.getSelectionModel().setSelectionMode(selectionMode);
        this.tree.addTreeSelectionListener(e -> {
            selected.clear();
            for (TreePath val : tree.getSelectionModel().getSelectionPaths()) {
                File file = (File) val.getLastPathComponent();
                selected.add(file);
            }
            pathField.setText(concatPaths(selected));
            pathField.setCaretPosition(0);
        });

        Action selectAction = new SelectAction(okText);
        Action cancelAction = new CancelAction(cancelText);

        setLayout(new GridBagLayout());
        add(pathField,
                new GBC(0, 0).setWeight(1.0, 0.0).setAnchor(GridBagConstraints.CENTER).setInsets(4, 4, 0, 4));
        add(new JScrollPane(tree),
                new GBC(0, 1).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.BOTH).setInsets(4, 4, 0, 4));
        add(new CmdPanel(getRootPane(), new JButton(selectAction), new JButton(cancelAction)),
                new GBC(0, 2).setWeight(0.7, 0.0).setAnchor(GridBagConstraints.CENTER).setInsets(4, 4, 4, 4).setIpad(2, 2));

        setPreferredSize(new Dimension(300, 400));
        pack();
        setLocationRelativeTo(parent);

        HotKey.escBy(getRootPane(), cancelAction);
        HotKey.actionBy(getRootPane(), "okAction", KeyEvent.VK_ENTER, selectAction);

        tree.requestFocus();
    }

    private String concatPaths(List<File> paths) {
        if (paths.isEmpty())
            return "";
        return paths.stream()
                .map(File::getAbsolutePath)
                .collect(Collectors.joining(";"));
    }

    public void expand(String path) {
        String[] tokens = path.split(SEPARATOR);
        Object parent = treeModel.getRoot();
        TreePath treePath = new TreePath(parent);
        for (String token : tokens) {
            int children = treeModel.getChildCount(parent);
            for (int j = 0; j < children; ++j) {
                File node = (File) treeModel.getChild(parent, j);
                if (node.getName().equals(token) || (j == 0 && node.getAbsolutePath().startsWith(token))) {
                    parent = node;
                    treePath = treePath.pathByAddingChild(node);
                    break;
                }
            }
        }
        tree.setSelectionPath(treePath);
        tree.expandPath(treePath);
        tree.scrollRowToVisible(tree.getLeadSelectionRow() + 10);
    }

    public void excludeRoots(List<String> roots) {
        treeModel.excludeRoots(roots);
    }

    public List<File> selected() {
        return selected;
    }

    public boolean isApproved() {
        return approved.get();
    }

    private final class CancelAction extends AbstractAction {

        public CancelAction(String text) {
            super(text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selected.clear();
            approved.set(false);
            dispose();
        }
    }

    private final class SelectAction extends AbstractAction {

        public SelectAction(String text) {
            super(text);
            putValue(CmdPanel.DEFAULT_CAPABLE, "true");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            approved.set(true);
            dispose();
        }
    }

}
