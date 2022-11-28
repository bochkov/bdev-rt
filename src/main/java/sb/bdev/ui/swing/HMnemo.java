package sb.bdev.ui.swing;

import javax.swing.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class HMnemo {

    private final String text;

    HMnemo(Action action) {
        this((String) action.getValue(Action.NAME));
    }

    void setup(AbstractButton btn) {
        int idx = text.indexOf('&');
        if (idx < 0) {
            btn.setText(text);
        } else {
            btn.setText(text.substring(0, idx) + text.substring(idx + 1));
            btn.setMnemonic(text.charAt(idx + 1));
        }
    }

}
