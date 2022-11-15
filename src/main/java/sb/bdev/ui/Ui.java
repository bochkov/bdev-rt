package sb.bdev.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.View;

public final class Ui {

    /**
     * Вычисляет preferredSize для компонентов Swing, содержащих html
     * @param comp swing component
     * @param prefWidth preferred width for comp
     * @param prefHeight preferred height for comp
     * @return Dimension
     */
    public static Dimension calcPreferredSize(JComponent comp, int prefWidth, int prefHeight) {
        View view = (View) comp.getClientProperty("html");
        view.setSize(prefWidth, prefHeight);
        float w = view.getPreferredSpan(View.X_AXIS);
        float h = view.getPreferredSpan(View.Y_AXIS);
        return new Dimension((int) Math.ceil(w), (int) Math.ceil(h));
    }

    private Ui() {
    }
}
