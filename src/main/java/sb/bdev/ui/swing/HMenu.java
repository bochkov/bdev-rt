package sb.bdev.ui.swing;

import java.util.Arrays;
import java.util.Collection;
import javax.swing.*;

public final class HMenu extends JMenu {

    public static final String SEPARATOR = "---";

    public HMenu(String title, MenuElement... items) {
        this(title, Arrays.asList(items));
    }

    public HMenu(String title, Collection<MenuElement> items) {
        setText(title);
        for (MenuElement item : items) {
            if (item instanceof JMenu mu) {
                add(mu);
            } else if (item instanceof JMenuItem mi) {
                if (mi.getText().equals(SEPARATOR))
                    addSeparator();
                else
                    add(mi);
            }
        }
    }
}
