package com.fdms.controller;

import com.fdms.model.LoginRequest;
import com.fdms.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        LoginRequest loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);

        try {
            String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"token\":\"" + token + "\"}");

        } catch (IllegalArgumentException e) {
            resp.setStatus(401);
            resp.getWriter().write("{\"error\":\"Invalid credentials\"}");
        }
    }
}
