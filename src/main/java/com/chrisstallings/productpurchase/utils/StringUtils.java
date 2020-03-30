package com.chrisstallings.productpurchase.utils;

public class StringUtils {

    public static boolean isDouble(String s) {
        boolean isDouble = false;
        try {
            Double.parseDouble(s);
            isDouble = true;
        } catch (NumberFormatException e) {
        }
        return isDouble;
    }

    public static boolean isBoolean(String s) {
        boolean isBoolean = false;
        isBoolean = s.equals("true") || s.equals("false");
        return isBoolean;
    }
}
