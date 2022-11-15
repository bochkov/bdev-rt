package sb.bdev.ui.swing;

import java.util.Arrays;
import java.util.Collection;
import javax.swing.*;

public final class HMenuBar extends JMenuBar {

    public HMenuBar(HMenu... menus) {
        this(Arrays.asList(menus));
    }

    public HMenuBar(Collection<HMenu> menus) {
        for (HMenu menu : menus) {
            if (menu != null)
                add(menu);
        }
    }
}
