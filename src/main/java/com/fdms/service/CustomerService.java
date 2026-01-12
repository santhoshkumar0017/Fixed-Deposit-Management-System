package com.fdms.service;

import com.fdms.dao.CustomerDAO;
import com.fdms.model.Customer;
import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    public Long registerCustomer(Customer customer) throws Exception {

        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        return customerDAO.create(customer);
    }

    public Customer getCustomerById(Long id) throws Exception {
        return customerDAO.findById(id);
    }

    public List<Customer> getAllCustomers() throws Exception {
        return customerDAO.findAll();
    }

    public boolean updateCustomer(Customer customer) throws Exception {
        return customerDAO.update(customer);
    }

    public boolean deleteCustomer(Long id) throws Exception {
        return customerDAO.delete(id);
    }
}
