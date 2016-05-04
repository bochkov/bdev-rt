package com.sergeybochkov.util.color;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class ColorUtilTest extends Assert {

    @Test
    public void testToHexString() {
        assertEquals("#000000", ColorUtil.toHexString(Color.BLACK));
        assertEquals("#FF0000", ColorUtil.toHexString(Color.RED));
        assertEquals("#00FF00", ColorUtil.toHexString(Color.GREEN));
        assertEquals("#0000FF", ColorUtil.toHexString(Color.BLUE));
        assertEquals("#FFFFFF", ColorUtil.toHexString(Color.WHITE));
    }
}
