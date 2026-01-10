package com.fdms.controller;

import com.fdms.model.LoginRequest;
import com.fdms.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        LoginRequest loginRequest;
        try {
            loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid JSON\"}");
            return;
        }

        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Username and password required\"}");
            return;
        }

        try {
            String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"token\":\"" + token + "\"}");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Invalid credentials\"}");
        }
    }
}
