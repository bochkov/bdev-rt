package sb.bdev.ui.common;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import sb.bdev.ui.HotKey;
import sb.bdev.ui.layout.GBC;

public final class ColorChoose extends JDialog {

    private final JColorChooser colorChooser;
    private Color initColor;
    private Color color;

    public ColorChoose(Frame parent) {
        this(parent, Color.WHITE);
    }

    public ColorChoose(Frame parent, Color initColor) {
        super(parent, "Выберите цвет", ModalityType.APPLICATION_MODAL);
        this.initColor = initColor;
        this.color = initColor;
        this.colorChooser = new JColorChooser(color);

        setLayout(new GridBagLayout());
        add(
                colorChooser,
                new GBC(0, 0)
                        .setAnchor(GridBagConstraints.CENTER)
                        .setFill(GridBagConstraints.BOTH)
        );

        CancelAction cancelAction = new CancelAction("Отмена");
        add(
                new CmdPanel(
                        getRootPane(),
                        new JButton(new OkAction("Выбрать")),
                        new JButton(cancelAction)
                ),
                new GBC(0, 1)
                        .setWeight(1.0, 0.0)
                        .setAnchor(GridBagConstraints.CENTER)
        );
        HotKey.escBy(getRootPane(), cancelAction);
    }

    public void setInitColor(Color color) {
        this.initColor = color;
        this.colorChooser.setColor(this.initColor);
    }

    public Color selectedColor() {
        return color;
    }

    private final class CancelAction extends AbstractAction {

        public CancelAction(String text) {
            super(text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            color = initColor;
            dispose();
        }
    }

    private final class OkAction extends AbstractAction {

        public OkAction(String text) {
            super(text);
            putValue(CmdPanel.DEFAULT_CAPABLE, "true");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            color = colorChooser.getColor();
            dispose();
        }
    }
}
