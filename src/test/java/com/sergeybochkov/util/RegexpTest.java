package com.sergeybochkov.util;

import com.sb.util.Regexp;
import org.junit.Assert;
import org.junit.Test;

public class RegexpTest extends Assert {

    @Test
    public void testUrlValid() {
        assertTrue(Regexp.isUrlValid("https://bitbucket.org/snippets/bochkov/89qE7/edit/"));
    }

    @Test
    public void testPasswordStrong() {

    }
}
