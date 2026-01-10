package com.fdms.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Admin Dashboard - Protected Resource");
    }
}
