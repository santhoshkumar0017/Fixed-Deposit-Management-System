package com.fdms.service;

import com.fdms.dao.AuthDAO;
import com.fdms.exception.DataException;
import com.fdms.util.PasswordUtil;
import com.fdms.util.TokenUtil;

public class AuthService {

    private final AuthDAO authDAO = new AuthDAO();

    public String login(String username, String password) {
        try {
            String hashedPassword = PasswordUtil.hash(password);
            long userId = authDAO.validateLogin(username, hashedPassword);

            if (userId == 0) {
                throw new IllegalArgumentException("Invalid username or password");
            }

            String token = TokenUtil.generateToken();
            authDAO.saveToken(userId, token);

            return token;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new DataException("Login failed", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            return authDAO.isTokenValid(token);
        } catch (Exception e) {
            throw new DataException("Token validation failed", e);
        }
    }
}
