package sb.bdev.ui;

import lombok.RequiredArgsConstructor;

import javax.swing.*;

@RequiredArgsConstructor
public final class HotKey {

    private final KeyStroke keyStroke;
    private final Action action;
    private final String alias;

    public HotKey(int keyCode, Action action) {
        this(keyCode, action, action.getClass().getSimpleName());
    }

    public HotKey(int keyCode, Action action, String alias) {
        this(KeyStroke.getKeyStroke(keyCode, 0), action, alias);
    }

    public HotKey(KeyStroke keyStroke, Action action) {
        this(keyStroke, action, action.getClass().getSimpleName());
    }

    public void on(JComponent component) {
        component.getActionMap().put(alias, action);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, alias);
    }
}
