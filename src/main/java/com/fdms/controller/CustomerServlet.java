package com.fdms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdms.model.Customer;
import com.fdms.service.CustomerService;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
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

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            String idParam = req.getParameter("id");

            if (idParam == null || idParam.isBlank()) {

                List<Customer> list = customerService.getAllCustomers();
                out.write(new ObjectMapper().writeValueAsString(list));
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }


            long id = Long.parseLong(idParam);
            Customer customer = customerService.getCustomerById(id);

            if (customer == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"error\":\"Customer not found\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                out.write(new ObjectMapper().writeValueAsString(customer));
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\":\"Invalid customer ID format\"}");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"Internal server error\"}");
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
