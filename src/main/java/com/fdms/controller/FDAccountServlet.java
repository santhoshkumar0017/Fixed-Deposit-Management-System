package com.fdms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fdms.model.FDAccount;
import com.fdms.service.FDAccountService;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.util.List;

public class FDAccountServlet extends HttpServlet {

    private final FDAccountService fdService = new FDAccountService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            FDAccount fd = mapper.readValue(req.getInputStream(), FDAccount.class);

            Long id = FDAccountService.openFD(fd);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("FD Account Created with ID: " + id);

        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Long customerId = Long.parseLong(req.getParameter("customerId"));

            List<FDAccount> list = FDAccountService.getFDsByCustomerId(customerId);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // For LocalDate

            String json = mapper.writeValueAsString(list);

            resp.setContentType("application/json");
            resp.getWriter().write(json);

        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            boolean closed = fdService.closeFD(id);
            resp.getWriter().write(closed ? "FD Closed" : "Close Failed");

        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }
}
