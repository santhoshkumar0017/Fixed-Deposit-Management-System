package com.fdms.dao;

import com.fdms.config.DataSourceFactory;
import com.fdms.model.FDAccount;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FDAccountDAO {

    private static final String INSERT_SQL =
            "INSERT INTO fd_accounts(customer_id, principal_amount, interest_rate, start_date, maturity_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_CUSTOMER_SQL =
            "SELECT * FROM fd_accounts WHERE customer_id = ?";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM fd_accounts WHERE id = ?";

    private static final String UPDATE_STATUS_SQL =
            "UPDATE fd_accounts SET status = ? WHERE id = ?";

    // CREATE FD ACCOUNT
    public Long create(FDAccount fd) throws Exception {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, fd.getCustomerId());
            ps.setDouble(2, fd.getPrincipalAmount());
            ps.setDouble(3, fd.getInterestRate());
            ps.setDate(4, Date.valueOf(fd.getStartDate()));
            ps.setDate(5, Date.valueOf(fd.getMaturityDate()));
            ps.setString(6, fd.getStatus());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getLong(1) : null;
        }
    }

    // READ BY CUSTOMER ID
    public List<FDAccount> findByCustomerId(Long customerId) throws Exception {
        List<FDAccount> list = new ArrayList<>();

        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_CUSTOMER_SQL)) {

            ps.setLong(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // READ FD BY ID
    public FDAccount findById(Long id) throws Exception {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;
            return mapRow(rs);
        }
    }

    // UPDATE STATUS (e.g., CLOSED/ACTIVE)
    public boolean updateStatus(Long id, String status) throws Exception {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_STATUS_SQL)) {

            ps.setString(1, status);
            ps.setLong(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    // Map Row → FDAccount
    private FDAccount mapRow(ResultSet rs) throws Exception {
        return new FDAccount(
                rs.getLong("id"),
                rs.getLong("customer_id"),
                rs.getDouble("principal_amount"),
                rs.getDouble("interest_rate"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("maturity_date").toLocalDate(),
                rs.getString("status")
        );
    }
}
