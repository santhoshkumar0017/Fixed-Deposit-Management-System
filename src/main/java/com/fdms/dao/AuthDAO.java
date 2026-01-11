package com.fdms.dao;

import com.fdms.config.DatabaseConfig;
import com.fdms.exception.DataException;

import java.sql.*;

public class AuthDAO {

    private static final String VALIDATE_LOGIN_SQL =
            "SELECT id FROM users WHERE username=? AND password_hash=?";

    private static final String INSERT_TOKEN_SQL =
            "INSERT INTO auth_token (customer_id, token, created_date) VALUES (?, ?, CURRENT_TIMESTAMP)";

    private static final String VALIDATE_TOKEN_SQL =
            "SELECT COUNT(*) FROM auth_token WHERE token=?";

    public long validateLogin(String username, String hashedPassword) throws Exception {
        try (Connection con = DatabaseConfig.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(VALIDATE_LOGIN_SQL)) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getLong(1) : 0;
        } catch (SQLException e) {
            throw new DataException("Login validation failed for " + username, e);
        }
    }

    public void saveToken(long customerId, String token) throws Exception {
        try (Connection con = DatabaseConfig.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_TOKEN_SQL)) {

            ps.setLong(1, customerId);
            ps.setString(2, token);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("Token save failed for " + customerId, e);
        }
    }

    public boolean isTokenValid(String token) throws Exception {
        try (Connection con = DatabaseConfig.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(VALIDATE_TOKEN_SQL)) {

            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new DataException("Token validation failed", e);
        }
    }
}
