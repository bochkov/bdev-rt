package sb.bdev.ui.swing;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import javax.swing.*;

public final class HPopup extends PopupMenu {

    public HPopup(JMenuItem... items) {
        this(Arrays.asList(items));
    }

    public HPopup(Collection<JMenuItem> items) {
        for (JMenuItem it : items) {
            MenuItem item = new MenuItem(it.getText());
            item.addActionListener(it.getAction());
            add(item);
        }
    }
}
