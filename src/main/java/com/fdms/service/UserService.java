package com.fdms.service;

import com.fdms.dao.UserDAO;
import com.fdms.model.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    // Validate user login
    public User authenticate(String username, String passwordHash) throws Exception {
        User user = userDAO.findByUsername(username);
        if (user == null) return null;

        // In real production: use BCrypt check
        if (!user.getPasswordHash().equals(passwordHash)) {
            return null;
        }
        return user;
    }

    // Fetch role for filters etc.
    public String getUserRole(String username) throws Exception {
        User user = userDAO.findByUsername(username);
        return (user != null) ? user.getRole() : null;
    }
}
