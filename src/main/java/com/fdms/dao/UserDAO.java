package com.fdms.dao;

import com.fdms.config.DataSourceFactory;
import com.fdms.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private static final String FIND_BY_USERNAME_SQL =
            "SELECT id, username, password_hash, role FROM users WHERE username = ?";

    public User findByUsername(String username) throws Exception {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_USERNAME_SQL)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("role")
            );
        }
    }
}
