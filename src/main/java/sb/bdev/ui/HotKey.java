package sb.bdev.ui;

import java.awt.event.KeyEvent;
import javax.swing.*;

public final class HotKey {

    private HotKey() {
    }

    public static void actionBy(JRootPane rootPane, String tag, int keyCode, Action action) {
        actionBy(rootPane, tag, KeyStroke.getKeyStroke(keyCode, 0), action);
    }

    public static void actionBy(JRootPane rootPane, String tag, KeyStroke keyStroke, Action action) {
        rootPane
                .getActionMap()
                .put(tag, action);
        rootPane
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(keyStroke, tag);
    }

    public static void escBy(JRootPane rootPane, Action action) {
        rootPane
                .getActionMap()
                .put("exitAction", action);
        rootPane
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitAction");
    }
}
