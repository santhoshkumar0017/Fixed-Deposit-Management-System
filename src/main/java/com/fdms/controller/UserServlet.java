package com.fdms.controller;

import com.fdms.model.User;
import com.fdms.service.UserService;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String username = req.getParameter("username");
            String passwordHash = req.getParameter("password");

            User user = userService.authenticate(username, passwordHash);

            if (user == null) {
                resp.getWriter().write("Login Failed");
            } else {
                req.getSession().setAttribute("user", user);
                resp.getWriter().write("Login Success: " + user.getRole());
            }

        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }
}
