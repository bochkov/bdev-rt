package sb.bdev.util.color;

import java.awt.*;

public final class HexColor {

    public static String asString(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6)
            hex = "000000".substring(0, 6 - hex.length()) + hex;
        return "#" + hex.toUpperCase();
    }

    public static Color asColor(String str) {
        int offset = str.startsWith("#") ? 1 : 0;
        return new Color(
                Integer.valueOf(str.substring(offset, offset + 2), 16),
                Integer.valueOf(str.substring(offset + 2, offset + 4), 16),
                Integer.valueOf(str.substring(offset + 4, offset + 6), 16)
        );
    }

    private HexColor() {
    }
}
