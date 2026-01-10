package com.fdms.util;

import java.util.Base64;

public class TokenUtil {

    public static String generateToken(String username) {
        long now = System.currentTimeMillis();
        String raw = username + ":" + now;
        return Base64.getEncoder().encodeToString(raw.getBytes());
    }

    public static boolean isValidToken(String token) {
        return token != null && !token.isBlank(); // simple check for now
    }
}
