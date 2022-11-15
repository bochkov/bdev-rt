package sb.bdev.ui.common;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.*;

import sb.bdev.text.Html;
import sb.bdev.ui.layout.GBC;

public final class JDetailPane extends JOptionPane {

    private static final ImageIcon ICON_RIGHT = new ImageIcon(Objects.requireNonNull(JDetailPane.class.getResource("/image/arrowRight.png")));
    private static final ImageIcon ICON_DOWN = new ImageIcon(Objects.requireNonNull(JDetailPane.class.getResource("/image/arrowDown.png")));

    private final JPanel pane = new JPanel(new GridBagLayout());
    private final List<Window> ancestors = new ArrayList<>();

    private JDetailPane(Object message, Object details, boolean copyPaste) {
        this(message, details, copyPaste, new Font("Monospaced", Font.PLAIN, 12));
    }

    private JDetailPane(Object message, Object details, boolean copyPaste, Font font) {
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

        final JLabel detailHeader = new JLabel("<html><b>Details</b>", ICON_RIGHT, SwingConstants.LEADING);
        pane.add(detailHeader,
                new GBC(0, 2)
                        .setWeight(1.0, 0.0)
                        .setInsets(2, 2, 2, 2)
                        .setIpad(2, 2));

        final JComponent detailsComponent;
        if (copyPaste) {
            JTextArea textArea = new JTextArea(details.toString(), 4, 50);
            textArea.setFont(font);
            textArea.setEditable(false);
            detailsComponent = new JScrollPane(textArea);
        } else {
            detailsComponent = new JLabel(
                    new Html(details.toString()).format()
            );
        }

        pane.add(detailsComponent,
                new GBC(0, 3)
                        .setFill(GridBagConstraints.BOTH)
                        .setInsets(5, 20, 5, 5)
                        .setIpad(2, 2));

        detailsComponent.addHierarchyListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(pane);
            ancestors.clear();
            ancestors.add(window);
        });
        detailsComponent.setVisible(false);

        detailHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean visible = detailsComponent.isVisible();
                detailHeader.setIcon(visible ? ICON_RIGHT : ICON_DOWN);
                detailsComponent.setVisible(!visible);
                for (Window window : ancestors) {
                    window.pack();
                }
            }
        });
    }

    private JComponent getPane() {
        return pane;
    }

    public static void showDetailDialog(Component parentComponent, Object message, String title, int messageType, Object details) throws HeadlessException {
        JOptionPane.showMessageDialog(
                parentComponent,
                new JDetailPane(message, details, false).getPane(),
                title,
                messageType
        );
    }

    public static void showDetailDialog(Component parentComponent, Object message, String title, int messageType, Object details, boolean copyPaste) throws HeadlessException {
        JOptionPane.showMessageDialog(
                parentComponent,
                new JDetailPane(message, details, copyPaste).getPane(),
                title,
                messageType
        );
    }

    public static void showDetailDialog(Component parentComponent, Object message, String title, int messageType, Object details, boolean copyPaste, Font font) throws HeadlessException {
        JOptionPane.showMessageDialog(
                parentComponent,
                new JDetailPane(message, details, copyPaste, font).getPane(),
                title,
                messageType
        );
    }
}
