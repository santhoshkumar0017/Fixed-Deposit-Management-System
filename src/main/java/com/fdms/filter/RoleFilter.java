package com.fdms.filter;

import com.fdms.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class RoleFilter implements Filter {

    private String role;

    @Override
    public void init(FilterConfig filterConfig) {
        this.role = filterConfig.getInitParameter("role");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.setStatus(401);
            resp.getWriter().write("Unauthorized: No session found");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null || !user.getRole().equals(role)) {
            resp.setStatus(403);
            resp.getWriter().write("Forbidden: Role not allowed");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
