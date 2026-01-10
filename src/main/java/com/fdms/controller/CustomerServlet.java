package com.fdms.controller;

import com.fdms.model.Customer;
import com.fdms.service.CustomerService;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.util.List;

public class CustomerServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Customer c = new Customer();
            c.setName(req.getParameter("name"));
            c.setPhone(req.getParameter("phone"));
            c.setEmail(req.getParameter("email"));

            Long id = customerService.registerCustomer(c);

            resp.getWriter().write("Customer registered with ID: " + id);

        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String idParam = req.getParameter("id");

            resp.setContentType("text/plain");

            if (idParam != null) {
                Customer c = customerService.getCustomerById(Long.parseLong(idParam));
                if (c != null) {
                    resp.getWriter().write(
                            c.getId() + " | " + c.getName() + " | " + c.getPhone() + " | " + c.getEmail()
                    );
                } else {
                    resp.getWriter().write("Customer not found");
                }
            } else {
                List<Customer> list = customerService.getAllCustomers();
                for (Customer c : list) {
                    resp.getWriter().write(
                            c.getId() + " | " + c.getName() + " | " + c.getPhone() + " | " + c.getEmail() + "\n"
                    );
                }
            }

        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Customer c = new Customer();
            c.setId(Long.parseLong(req.getParameter("id")));
            c.setName(req.getParameter("name"));
            c.setPhone(req.getParameter("phone"));
            c.setEmail(req.getParameter("email"));

            boolean updated = customerService.updateCustomer(c);
            resp.getWriter().write(updated ? "Customer Updated" : "Update Failed");

        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            boolean deleted = customerService.deleteCustomer(id);
            resp.getWriter().write(deleted ? "Customer Deleted" : "Delete Failed");

        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }
}
