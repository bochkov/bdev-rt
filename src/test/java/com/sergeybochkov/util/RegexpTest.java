package com.sergeybochkov.util;

import org.junit.Assert;
import org.junit.Test;

public class RegexpTest extends Assert {

    @Test
    public void testUrlValid() {
        assertTrue(Regexp.urlValid("https://bitbucket.org/snippets/bochkov/89qE7/edit/"));
    }

    @Test
    public void testPasswordStrong() {

    }
}
