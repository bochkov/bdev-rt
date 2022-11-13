package com.sb.ui;

import com.sb.layout.GBC;
import com.sb.fs.DriveTypeInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class DirectoryBrowser extends JDialog {

    private JTree treeView = new JTree();
    private File selectedFile = null;

    private SelectAction selectAction = new SelectAction();
    private CancelAction cancelAction = new CancelAction();

    public static Builder newBuilder() {
        return newBuilder(null);
    }

    public static Builder newBuilder(Frame parent) {
        return new DirectoryBrowser(parent).new Builder();
    }

    private DirectoryBrowser(Frame parent) {
        super(parent, true);

        setLayout(new GridBagLayout());
        add(new JScrollPane(treeView, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                new GBC(0, 0)
                        .setFill(GridBagConstraints.BOTH)
                        .setWeight(1.0, 1.0)
                        .setInsets(4, 4, 0, 4));

        JPanel cmdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton openButton = new JButton(selectAction);
        openButton.setDefaultCapable(true);
        cmdPanel.add(openButton);

        JButton cancelButton = new JButton(cancelAction);
        cancelButton.setDefaultCapable(false);
        cmdPanel.add(cancelButton);

        add(cmdPanel, new GBC(0, 1)
                .setFill(GridBagConstraints.HORIZONTAL)
                .setWeight(0.7, 0.0)
                .setInsets(4, 4, 4, 4));

        getRootPane().setDefaultButton(openButton);
        setPreferredSize(new Dimension(300, 400));
        pack();
        setLocationRelativeTo(parent);

        getRootPane()
                .getActionMap()
                .put("exitAction", new CancelAction());
        getRootPane()
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitAction");
    }

    private void setRootVisible(boolean visible) {
        treeView.setRootVisible(visible);
    }

    public File getSelectedFolder() {
        return selectedFile;
    }

    public void expand(String path) {
        String[] tokens = path.replace("\\", "\\\\").split("\\\\");
        Object parent = treeView.getModel().getRoot();
        TreePath treePath = new TreePath(parent);
        for (String token : tokens) {
            int childrens = treeView.getModel().getChildCount(parent);
            for (int j = 0; j < childrens; ++j) {
                File node = (File) treeView.getModel().getChild(parent, j);
                if (node.getName().equals(token)) {
                    parent = node;
                    treePath = treePath.pathByAddingChild(node);
                    break;
                }
            }
        }
        treeView.setSelectionPath(treePath);
        treeView.expandPath(treePath);
    }

    public class Builder {

        private FolderTreeModel.Builder modelBuilder = FolderTreeModel.newBuilder();

        private Builder() {
        }

        public Builder setTitle(String title) {
            DirectoryBrowser.this.setTitle(title);
            return this;
        }

        public Builder setRootVisible(boolean visible) {
            DirectoryBrowser.this.setRootVisible(visible);
            return this;
        }

        public Builder exclude(DriveTypeInfo.Info info) {
            File[] roots = modelBuilder.roots();
            modelBuilder.exclude(DriveTypeInfo.findByInfo(roots, info));
            return this;
        }

        public Builder setButtons(String[] buttons) {
            if (buttons.length >= 2) {
                selectAction.putValue(Action.NAME, buttons[0]);
                cancelAction.putValue(Action.NAME, buttons[1]);
            }
            return this;
        }

        public DirectoryBrowser build() {
            treeView.setEditable(false);
            treeView.setBorder(new EmptyBorder(6, 6, 6, 6));
            treeView.addTreeSelectionListener(e -> selectedFile = (File) treeView.getLastSelectedPathComponent());
            treeView.setModel(modelBuilder.build());
            return DirectoryBrowser.this;
        }
    }

    public class SelectAction extends AbstractAction {

        public SelectAction() {
            super("Choose");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public class CancelAction extends AbstractAction {

        public CancelAction() {
            super("Cancel");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedFile = null;
            dispose();
        }
    }

    public static void main(String[] args) {
        DirectoryBrowser db = DirectoryBrowser.newBuilder()
                .setTitle("Test")
                //.exclude(DriveTypeInfo.Info.FLOPPY)
                //.exclude(DriveTypeInfo.Info.OPTICAL)
                .setButtons(new String[]{ "Выбрать", "Отмена"})
                .build();

        db.setVisible(true);

        if (db.getSelectedFolder() != null)
            System.out.println(db.getSelectedFolder().getAbsolutePath());
        else
            System.out.println("Nothing selected");
        System.exit(0);
    }
}
