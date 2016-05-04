package com.sergeybochkov.util.color;

import java.awt.*;

public class ColorUtil {

    public static String toHexString(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6)
            hex = "000000".substring(0, 6 - hex.length()) + hex;

        return "#" + hex.toUpperCase();
    }
}
