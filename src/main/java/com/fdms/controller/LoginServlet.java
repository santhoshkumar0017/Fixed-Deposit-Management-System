package com. fdms. controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdms.exception.DataException;
import com.fdms.model.User;
import com.fdms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID=1L;
    ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService = new UserService();
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = objectMapper.readValue(request.getInputStream(), User.class);
        userService.addUser(user);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.getWriter().write("User Registered Successfully");
        LOG.info("Registered Successfully");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            String username =request.getParameter("username");
            String password= request.getParameter("password");
            String token = userService.verify(username,password);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write("Token: " + token);
            LOG.info("Token Generated");
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new DataException("Failed",e);
        }
    }
}
