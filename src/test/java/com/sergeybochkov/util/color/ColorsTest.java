package com.sergeybochkov.util.color;

import com.sb.util.color.Colors;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class ColorsTest extends Assert {

    @Test
    public void testToHexString() {
        assertEquals("#000000", Colors.toHexString(Color.BLACK));
        assertEquals("#FF0000", Colors.toHexString(Color.RED));
        assertEquals("#00FF00", Colors.toHexString(Color.GREEN));
        assertEquals("#0000FF", Colors.toHexString(Color.BLUE));
        assertEquals("#FFFFFF", Colors.toHexString(Color.WHITE));
    }
}
