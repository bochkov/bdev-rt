package sb.bdev.ui.swing;

import java.util.Arrays;
import java.util.Collection;
import javax.swing.*;

public final class HPopupMenu extends JPopupMenu {

    public HPopupMenu(JMenuItem... items) {
        this(Arrays.asList(items));
    }

    public HPopupMenu(Collection<JMenuItem> items) {
        for (JMenuItem item : items) {
            add(item);
        }
    }
}
