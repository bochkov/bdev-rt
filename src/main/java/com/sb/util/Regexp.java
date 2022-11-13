package com.sb.util;

import java.util.regex.Pattern;

public final class Regexp {

    /**
     * The following regexp will make sure that:
     * <ul>
     * <li>Passwords will contain at least (1) upper case letter</li>
     * <li>Passwords will contain at least (1) lower case letter</li>
     * <li>Passwords will contain at least (1) number or special character</li>
     * <li>Passwords will contain at least (8) characters in length</li>
     * <li>Password maximum length should not be arbitrarily limited</li>
     * </ul>
     * @param password testing password
     * @return boolean
     */
    public static boolean isPasswordStrong(String password) {
        return Pattern
                .compile("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
                .matcher(password)
                .matches();

    }


    public static boolean isUrlValid(String url) {
        return Pattern
                .compile("^(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\?=.-]*)*/?$")
                .matcher(url)
                .matches();
    }

    private Regexp() {
    }
}
