package com.fdms.filter;
import com.fdms.exception.DataException;
import com.fdms.service.UserService;
import com.fdms.util.Jwt;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    private static final int SUB_STRING=7;
    UserService userService = new UserService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws
            IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (uri.contains("/user") || uri.contains("/startup")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(SUB_STRING);

        try {

            String username = Jwt.extractUsername(token);


            if (Jwt.validateToken(token, username)) {
                chain.doFilter(request, response);
                return;
            }

        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid token");
            throw new DataException("failed",e);

        }

        chain.doFilter(request, response);
    }
}

