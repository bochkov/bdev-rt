package com.sergeybochkov.ui;

import com.sergeybochkov.layout.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JOptionPaneWithDetails extends JOptionPane {

    private JPanel pane;
    private JDialog dialog;
    private ImageIcon icon1 = new ImageIcon(getClass().getResource("/image/arrowRight.png"));
    private ImageIcon icon2 = new ImageIcon(getClass().getResource("/image/arrowDown.png"));

    private JOptionPaneWithDetails(Object message, Object details) {
        pane = new JPanel(new GridBagLayout());

        pane.add(new JLabel(message.toString()),
                new GBC(0, 0)
                        .setWeight(1.0, 0.0)
                        .setInsets(2, 2, 2, 2)
                        .setIpad(2, 2));
        pane.add(new JLabel(),
                new GBC(0, 1)
                        .setWeight(0.0, 1.0)
                        .setFill(GridBagConstraints.VERTICAL)
                        .setAnchor(GridBagConstraints.CENTER)
                        .setInsets(2, 2, 2, 2)
                        .setIpad(2, 2));

        final JLabel detailHeader = new JLabel("<html><b>Details</b>", icon1, SwingConstants.LEFT);
        pane.add(detailHeader,
                new GBC(0, 2)
                        .setWeight(1.0, 0.0)
                        .setInsets(2, 2, 2, 2)
                        .setIpad(2, 2));

        String formattedDetails = "<html>" + details.toString()
                .replaceAll("\r?\n", "<br/>")
                .replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        final JLabel detailsLabel = new JLabel(formattedDetails);
        pane.add(detailsLabel,
                new GBC(0, 3)
                        .setWeight(1.0, 1.0)
                        .setFill(GridBagConstraints.BOTH)
                        .setInsets(5, 20, 5, 5)
                        .setIpad(2, 2));

        detailsLabel.addHierarchyListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(pane);
            if (window instanceof JDialog)
                dialog = (JDialog) window;
        });
        detailsLabel.setVisible(false);

        detailHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean visible = detailsLabel.isVisible();
                detailHeader.setIcon(visible ? icon1 : icon2);
                detailsLabel.setVisible(!visible);
                if (dialog != null)
                    dialog.pack();
            }
        });
    }

    private JComponent getPane() {
        return pane;
    }

    public static void showDetailedMessageDialog(Component parentComponent, Object message, String title, int messageType, Object details) throws HeadlessException {
        JOptionPaneWithDetails detailedOptionPane = new JOptionPaneWithDetails(message, details);
        JOptionPane.showMessageDialog(parentComponent, detailedOptionPane.getPane(), title, messageType);
    }

    public static void main(String[] args) {
        showDetailedMessageDialog(null, "Hello", "world", JOptionPane.INFORMATION_MESSAGE, "Hello world");
    }
}
