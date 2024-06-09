package sb.bdev.ui.swing;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collection;

public final class HPopupMenu extends JPopupMenu {

    public HPopupMenu(JMenuItem... items) {
        this(Arrays.asList(items));
    }

    public HPopupMenu(Collection<JMenuItem> items) {
        for (JMenuItem item : items) {
            if (item.getText().equals(HMenu.SEPARATOR)) {
                addSeparator();
            } else {
                add(item);
            }
        }
    }
}
