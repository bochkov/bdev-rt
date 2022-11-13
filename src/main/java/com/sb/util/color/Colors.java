package com.sb.util.color;

import java.awt.*;

public final class Colors {

    public static String toHexString(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6)
            hex = "000000".substring(0, 6 - hex.length()) + hex;

        return "#" + hex.toUpperCase();
    }

    private Colors() {
    }
}
