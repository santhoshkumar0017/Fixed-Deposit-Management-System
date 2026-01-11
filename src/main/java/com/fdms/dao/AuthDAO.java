package com.fdms.dao;

import com.fdms.config.DatabaseConfig;
import com.fdms.exception.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO {

    public Long validateLogin(String username, String passwordHash) throws Exception {
        String sql = "SELECT id FROM users WHERE username = ? AND password_hash = ?";
        try (Connection con = DatabaseConfig.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, passwordHash);

            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getLong("id") : 0L;
        } catch (SQLException e) {
            throw new DataException("Login validation failed for user " + username, e);
        }
    }

    public void saveToken(Long userId, String token) throws Exception {
        String sql = "UPDATE users SET token = ? WHERE id = ?";
        try (Connection con = DatabaseConfig.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataException("Token save failed for customer " + userId, e);
        }
    }

    public boolean isTokenValid(String token) throws Exception {
        String sql = "SELECT id FROM users WHERE token = ?";
        try (Connection con = DatabaseConfig.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DataException("Token validation is failed "+e);
        }
    }
}
