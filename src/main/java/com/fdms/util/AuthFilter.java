//package com.fdms.util;
//
//import com.fdms.service.AuthService;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//
//public class AuthFilter implements Filter {
//
//    private final AuthService authService = new AuthService();
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//
//        String path = req.getRequestURI();
//
//        if (path.endsWith("/login") || path.endsWith("/logout")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String header = req.getHeader("Authorization");
//
//        if (header == null || !header.startsWith("Bearer ")) {
//            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            resp.getWriter().write("Missing or invalid Authorization header");
//            return;
//        }
//
//        String token = header.substring(7); // remove "Bearer "
//
//        if (!authService.validateToken(token)) {
//            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            resp.getWriter().write("Invalid or expired token");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//}
