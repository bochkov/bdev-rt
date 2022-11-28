package sb.bdev.ui.swing;

import javax.swing.*;

public final class HMenuItem extends JMenuItem {

    public HMenuItem(Action action, String tooltip) {
        this(action);
        setToolTipText(tooltip);
    }

    public HMenuItem(Action action, KeyStroke keyStroke) {
        this(action);
        setAccelerator(keyStroke);
    }

    public HMenuItem(Action action) {
        super(action);
        new HMnemo(action).setup(this);
    }

    public HMenuItem(String text) {
        super(text);
        new HMnemo(text).setup(this);
    }
}
