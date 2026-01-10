package com.fdms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdms.model.Customer;
import com.fdms.service.CustomerService;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CustomerServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        try {
            ObjectMapper mapper = new ObjectMapper();
            Customer customer = mapper.readValue(req.getInputStream(), Customer.class);

            Long id = customerService.registerCustomer(customer);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\":\"Customer created\",\"id\":" + id + "}");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
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
