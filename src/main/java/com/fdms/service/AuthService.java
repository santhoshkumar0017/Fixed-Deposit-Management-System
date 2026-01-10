package com.fdms.service;

import com.fdms.dao.AuthDAO;
import com.fdms.util.PasswordUtil;
import com.fdms.util.TokenUtil;

public class AuthService {

    private final AuthDAO authDAO = new AuthDAO();

    public String login(String username, String password) throws Exception {

        if (username == null || password == null) {
            throw new IllegalArgumentException("Username & password required");
        }

        // Hash password before validation
        String hashedPassword = PasswordUtil.hash(password);

        // Validate via DAO → returns userId if valid else 0
        Long userId = authDAO.validateLogin(username, hashedPassword);

        if (userId == null || userId == 0) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Generate token
        String token = TokenUtil.generateToken(username);

        // Save token in DB
        authDAO.saveToken(userId, token);

        return token;
    }

    public boolean validateToken(String token) throws Exception {
        if (token == null || token.isBlank()) return false;
        return authDAO.isTokenValid(token);
    }
}
