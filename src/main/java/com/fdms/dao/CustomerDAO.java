package com.fdms.dao;

import com.fdms.config.DataSourceFactory;
import com.fdms.exception.DataException;
import com.fdms.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static final String INSERT_SQL =
            "INSERT INTO customers (name, phone, email) VALUES (?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, name, phone, email FROM customers WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT id, name, phone, email FROM customers";

    private static final String UPDATE_SQL =
            "UPDATE customers SET name = ?, phone = ?, email = ? WHERE id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM customers WHERE id = ?";

    // CREATE
    public Long create(Customer customer) throws Exception {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getLong(1) : null;
        }
        catch (SQLException e) {
            throw new DataException("Failed to insert customer", e);
        }
    }

    // READ by ID
    public Customer findById(Long id) throws Exception {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;
            return mapRow(rs);
        }
        catch (SQLException e) {
            throw new DataException("Failed to fetch customer by id", e);
        }
    }

    // READ ALL
    public List<Customer> findAll() throws Exception {
        List<Customer> list = new ArrayList<>();

        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        catch (SQLException e) {
        throw new DataException("Failed to fetch customers", e);
    }


        return list;
    }

    // UPDATE
    public boolean update(Customer customer) throws Exception {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setLong(4, customer.getId());

            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw new DataException("Failed to update customer", e);
        }
    }

    // DELETE
    public boolean delete(Long id) throws Exception {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw new DataException("Failed to delete customer", e);
        }
    }

    // Map DB Row → Customer Object
    private Customer mapRow(ResultSet rs) throws Exception {
        return new Customer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("email")
        );
    }
}
