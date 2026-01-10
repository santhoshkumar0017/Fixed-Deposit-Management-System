package com.fdms.dao;

import com.fdms.config.DataSourceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDAO {

    public Long validateLogin(String username, String passwordHash) throws Exception {
        String sql = "SELECT id FROM users WHERE username = ? AND password_hash = ?";
        try (Connection con = DataSourceFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, passwordHash);

            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getLong("id") : 0L;
        }
    }

    public void saveToken(Long userId, String token) throws Exception {
        String sql = "UPDATE users SET token = ? WHERE id = ?";
        try (Connection con = DataSourceFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setLong(2, userId);
            ps.executeUpdate();
        }
    }

    public boolean isTokenValid(String token) throws Exception {
        String sql = "SELECT id FROM users WHERE token = ?";
        try (Connection con = DataSourceFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}
