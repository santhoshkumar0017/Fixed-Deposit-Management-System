package com.fdms.service;

import com.fdms.util.TokenUtil;

public class AuthService {

    public String login(String username, String password) {

        // Hardcoded login check (instead of User table)
        if (!"admin".equals(username) || !"admin123".equals(password)) {
            throw new IllegalArgumentException("Bad credentials");
        }

        // Generate token
        return TokenUtil.generateToken(username);
    }

    public boolean validateToken(String token) {

        if (token == null || token.isEmpty()) {
            return false;
        }

        return TokenUtil.isValidToken(token); // You will build this
    }
}
