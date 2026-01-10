//package com.fdms.controller;
//
//import com.fdms.dao.UserDAO;
//import com.fdms.model.User;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import java.io.IOException;
//
//public class LoginServlet extends HttpServlet {
//    private final UserDAO userDAO = new UserDAO();
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//
//        User user = userDAO.login(username, password);
//
//        if (user != null) {
//            HttpSession session = req.getSession();
//            session.setAttribute("user", user);
//
//            resp.getWriter().write("Login Success");
//        } else {
//            resp.setStatus(401);
//            resp.getWriter().write("Invalid Credentials");
//        }
//    }
//}
