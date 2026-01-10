package com.fdms.controller;

import com.fdms.config.DataSourceFactory;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Connection;

public class DBTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (Connection conn = DataSourceFactory.getConnection()) {
            resp.getWriter().write("DB Connection Success!");
        } catch (Exception e) {
            resp.getWriter().write("DB Connection Failed: " + e.getMessage());
        }
    }
}
