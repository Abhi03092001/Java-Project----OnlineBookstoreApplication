package com.bookstore.util;

public class ValidationUtil {
    public static boolean isEmailValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    public static boolean isNonEmpty(String s) { return s != null && !s.trim().isEmpty(); }
}
