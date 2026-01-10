package com.fdms.controller;

import com.fdms.config.AppConfig;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

public class ConfigTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String appName = AppConfig.get("app.name");
        resp.getWriter().write("App Name: " + appName);
    }
}
