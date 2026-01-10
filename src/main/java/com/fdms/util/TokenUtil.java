package com.fdms.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TokenUtil {
    private static final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public static String generateToken(String username) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, username);
        return token;
    }

    public static boolean isValid(String token) {
        return token != null && tokenStore.containsKey(token);
    }

    public static String getUsername(String token) {
        return tokenStore.get(token);
    }

    public static void deleteToken(String token) {
        tokenStore.remove(token);
    }
}
