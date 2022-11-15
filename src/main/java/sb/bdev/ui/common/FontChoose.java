package sb.bdev.ui.common;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sb.bdev.ui.HotKey;
import sb.bdev.ui.layout.GBC;

public final class FontChoose extends JDialog implements ListSelectionListener {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;

    private static final Integer[] FONT_STYLES = {
            Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD | Font.ITALIC
    };
    private static final Integer[] FONT_SIZES = {
            7, 8, 9, 10, 11, 12, 14, 16, 18, 20,
            22, 24, 26, 30, 32, 40, 72
    };

    private final Frame owner;
    private final Font initialFont;

    private final JList<Integer> fontStyles = new JList<>();
    private final JList<Integer> fontSizes = new JList<>();
    private final JList<String> fontNames = new JList<>() {
        @Override
        public void setModel(ListModel<String> model) {
            String prev = getSelectedValue();
            super.setModel(model);
            setSelectedValue(prev, true);
            if (getSelectedIndex() < 0) {
                setSelectedValue(model.getElementAt(0), true);
            }
        }
    };

    private final JTextField sampleTextField = new JTextField("Пример текста");

    private Font selectedFont;

    public FontChoose(Frame owner, Font initialFont) {
        super(owner, "Выберите шрифт", ModalityType.APPLICATION_MODAL);
        this.owner = owner;
        this.initialFont = initialFont;
        layoutComponents();

        fontNames.setModel(new FontListModel<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        fontNames.setCellRenderer(new FontNameRenderer());
        fontNames.setSelectedValue(initialFont.getFamily(), true);
        fontNames.addListSelectionListener(this);

        fontStyles.setModel(new FontListModel<>(FONT_STYLES));
        fontStyles.setCellRenderer(new FontStyleRenderer());
        fontStyles.setSelectedValue(initialFont.getStyle(), true);
        fontStyles.addListSelectionListener(this);

        fontSizes.setModel(new FontListModel<>(FONT_SIZES));
        fontSizes.setSelectedValue(initialFont.getSize(), true);
        fontSizes.addListSelectionListener(this);

        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        HotKey.escBy(getRootPane(), new CancelAction(""));
        updateFonts();
    }

    public Font selectedFont() {
        return selectedFont;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        updateFonts();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            pack();
            setLocationRelativeTo(owner);
        }
        super.setVisible(visible);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());

        JPanel monoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup grp = new ButtonGroup();
        JRadioButton allButton = new JRadioButton(new AllFontAction("Все шрифты"));
        allButton.setSelected(true);
        grp.add(allButton);
        monoPanel.add(allButton);
        JRadioButton monoButton = new JRadioButton(new MonoFontAction("Моноширинные шрифты"));
        grp.add(monoButton);
        monoPanel.add(monoButton);

        JPanel samplePanel = new JPanel(new GridBagLayout());
        samplePanel.setBorder(BorderFactory.createTitledBorder("Образец"));
        samplePanel.add(sampleTextField, new GBC(0, 0).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.BOTH));

        JPanel cmdPanel = new CmdPanel(
                getRootPane(),
                new JButton(new OkAction("OK")), new JButton(new CancelAction("Отмена"))
        );

        add(monoPanel,
                new GBC(0, 0, 3, 1).setWeight(0.0, 0.0).setInsets(5, 5, 5, 5));
        add(new JLabel("Шрифт"),
                new GBC(0, 1).setWeight(0.0, 0.0).setFill(GridBagConstraints.NONE).setInsets(0, 5, 5, 5));
        add(new JLabel("Стиль"),
                new GBC(1, 1).setWeight(0.0, 0.0).setFill(GridBagConstraints.NONE).setInsets(0, 5, 5, 5));
        add(new JLabel("Размер"),
                new GBC(2, 1).setWeight(0.0, 0.0).setFill(GridBagConstraints.NONE).setInsets(0, 5, 5, 5));
        add(new JScrollPane(fontNames, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
                new GBC(0, 2).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.BOTH).setInsets(0, 5, 5, 5));
        add(new JScrollPane(fontStyles, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
                new GBC(1, 2).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.BOTH).setInsets(0, 5, 5, 5));
        add(new JScrollPane(fontSizes, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
                new GBC(2, 2).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.BOTH).setInsets(0, 5, 5, 5));
        add(samplePanel,
                new GBC(0, 3, 4, 1).setWeight(1.0, 0.4).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.BOTH).setInsets(0, 5, 10, 5));
        add(cmdPanel,
                new GBC(0, 4, 4, 1).setWeight(1.0, 0.0).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.NONE).setInsets(5, 5, 5, 5));
    }

    @SuppressWarnings("MagicConstant")
    private void updateFonts() {
        this.selectedFont = new Font(
                fontNames.getSelectedValue(),
                fontStyles.getSelectedValue(),
                fontSizes.getSelectedValue()
        );
        this.sampleTextField.setFont(selectedFont);
    }


    private final class OkAction extends AbstractAction {

        public OkAction(String text) {
            super(text);
            putValue(CmdPanel.DEFAULT_CAPABLE, "true");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private final class CancelAction extends AbstractAction {

        public CancelAction(String text) {
            super(text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedFont = initialFont;
            dispose();
        }
    }

    private final class AllFontAction extends AbstractAction {

        public AllFontAction(String text) {
            super(text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fontNames.setModel(new FontListModel<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        }
    }

    private final class MonoFontAction extends AbstractAction {

        public MonoFontAction(String text) {
            super(text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> monoFonts = new ArrayList<>();
            FontRenderContext ctx = new FontRenderContext(null,
                    RenderingHints.VALUE_ANTIALIAS_DEFAULT,
                    RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
            for (String fontName : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
                Font f = new Font(fontName, Font.PLAIN, 12);
                double widthI = f.getStringBounds("i", ctx).getWidth();
                double widthM = f.getStringBounds("m", ctx).getWidth();
                if (Math.abs(widthI - widthM) < 0.1)
                    monoFonts.add(fontName);
            }
            fontNames.setModel(new FontListModel<>(monoFonts));
        }
    }

    private static class FontListModel<T> extends AbstractListModel<T> {

        private final List<T> list = new ArrayList<>();

        public FontListModel(T[] array) {
            Collections.addAll(this.list, array);
        }

        public FontListModel(List<T> list) {
            this.list.addAll(list);
        }

        @Override
        public int getSize() {
            return list.size();
        }

        @Override
        public T getElementAt(int index) {
            return list.get(index);
        }
    }

    private static final class FontStyleRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Integer style) {
                Component component;
                switch (style) {
                    case Font.ITALIC -> {
                        component = super.getListCellRendererComponent(list, "Курсив", index, isSelected, cellHasFocus);
                        component.setFont(component.getFont().deriveFont(Font.ITALIC));
                    }
                    case Font.BOLD -> {
                        component = super.getListCellRendererComponent(list, "Полужирный", index, isSelected, cellHasFocus);
                        component.setFont(component.getFont().deriveFont(Font.BOLD));
                    }
                    case Font.BOLD | Font.ITALIC -> {
                        component = super.getListCellRendererComponent(list, "Полужирный курсив", index, isSelected, cellHasFocus);
                        component.setFont(component.getFont().deriveFont(Font.BOLD | Font.ITALIC));
                    }
                    default -> {
                        component = super.getListCellRendererComponent(list, "Обычный", index, isSelected, cellHasFocus);
                        component.setFont(component.getFont().deriveFont(Font.PLAIN));
                    }
                }
                return component;
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    private static final class FontNameRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof String fontName) {
                Component component = super.getListCellRendererComponent(list, fontName, index, isSelected, cellHasFocus);
                component.setFont(new Font(fontName, Font.PLAIN, 12));
                return component;
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
}
