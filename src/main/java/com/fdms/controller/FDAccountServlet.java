package com.fdms.controller;

import com.fdms.model.FDAccount;
import com.fdms.service.FDAccountService;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

public class FDAccountServlet extends HttpServlet {

    private final FDAccountService fdService = new FDAccountService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            FDAccount fd = new FDAccount();
            fd.setCustomerId(Long.parseLong(req.getParameter("customerId")));
            fd.setPrincipalAmount(Double.parseDouble(req.getParameter("principalAmount")));
            fd.setInterestRate(Double.parseDouble(req.getParameter("interestRate")));
            fd.setStartDate(java.time.LocalDate.parse(req.getParameter("startDate")));
            fd.setMaturityDate(java.time.LocalDate.parse(req.getParameter("maturityDate")));

            Long id = fdService.openFD(fd);

            resp.getWriter().write("FD Opened with ID: " + id);

        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("text/plain");

            if (req.getParameter("id") != null) {
                FDAccount fd = fdService.getFDById(Long.parseLong(req.getParameter("id")));
                resp.getWriter().write(fd.toString());
            }
            else if (req.getParameter("customerId") != null) {
                var list = fdService.getFDsByCustomerId(Long.parseLong(req.getParameter("customerId")));
                list.forEach(fd -> {
                    try { resp.getWriter().write(fd.toString() + "\n"); }
                    catch (Exception ignored) {}
                });
            }
            else {
                resp.getWriter().write("Provide id or customerId");
            }

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
