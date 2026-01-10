package com.fdms.controller;

import com.fdms.config.DataSourceFactory;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().write("Servlet is running!");
    }

    @Override
    public void init() {
        System.out.println("HelloServlet init - triggering Liquibase");

    }

}
