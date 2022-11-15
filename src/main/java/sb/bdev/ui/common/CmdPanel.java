package sb.bdev.ui.common;

import java.awt.*;
import javax.swing.*;

public final class CmdPanel extends JPanel {

    public static final String DEFAULT_CAPABLE = "DEFAULT_CAPABLE";

    public CmdPanel(JRootPane rootPane, JButton... buttons) {
        this(rootPane, new FlowLayout(FlowLayout.CENTER), buttons);
    }

    public CmdPanel(JRootPane rootPane, LayoutManager lm, JButton... buttons) {
        this.setLayout(lm);
        for (JButton button : buttons) {
            add(button);
            if ("true".equals(button.getAction().getValue(DEFAULT_CAPABLE))) {
                button.setDefaultCapable(true);
                rootPane.setDefaultButton(button);
            }
        }
    }
}
