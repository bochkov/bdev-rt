package com.sb.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.*;

import com.sb.layout.GBC;

public final class JDetailPane extends JOptionPane {

    private final ImageIcon iconRight = new ImageIcon(Objects.requireNonNull(JDetailPane.class.getResource("/image/arrowRight.png")));
    private final ImageIcon iconDown = new ImageIcon(Objects.requireNonNull(JDetailPane.class.getResource("/image/arrowDown.png")));

    private final JPanel pane;
    private JDialog dialog;

    private JDetailPane(Object message, Object details) {
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

        final JLabel detailHeader = new JLabel("<html><b>Details</b>", iconRight, SwingConstants.LEFT);
        pane.add(detailHeader,
                new GBC(0, 2)
                        .setWeight(1.0, 0.0)
                        .setInsets(2, 2, 2, 2)
                        .setIpad(2, 2));

        String formattedDetails = "<html>" + details.toString()
                .replaceAll("\r?\n", "<br/>")
                .replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        final JLabel detailsLabel = new JLabel(formattedDetails);
        pane.add(detailsLabel,
                new GBC(0, 3)
                        .setWeight(1.0, 1.0)
                        .setFill(GridBagConstraints.BOTH)
                        .setInsets(5, 20, 5, 5)
                        .setIpad(2, 2));

        detailsLabel.addHierarchyListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(pane);
            if (window instanceof JDialog dlg)
                dialog = dlg;
        });
        detailsLabel.setVisible(false);

        detailHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean visible = detailsLabel.isVisible();
                detailHeader.setIcon(visible ? iconRight : iconDown);
                detailsLabel.setVisible(!visible);
                if (dialog != null)
                    dialog.pack();
            }
        });
    }

    private JComponent getPane() {
        return pane;
    }

    public static void showDialog(Component parentComponent, Object message, String title, int messageType, Object details) throws HeadlessException {
        JDetailPane detailedOptionPane = new JDetailPane(message, details);
        JOptionPane.showMessageDialog(parentComponent, detailedOptionPane.getPane(), title, messageType);
    }
}
