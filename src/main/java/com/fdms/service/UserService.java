package com.fdms.service;

import com.fdms.dao.UserDAO;
import com.fdms.model.User;
import com.fdms.util.Hashing;
import com.fdms.util.Jwt;


public class UserService {

    UserDAO userDAO =new UserDAO();

    public void addUser(User user) {

        String pass = user.getPassword();
        user.setPassword(Hashing.hashPassword(pass));

        userDAO.adduser(user);

    }

    public String verify(String username , String password){
        String hashPassword = userDAO.getPass(username);
        if(hashPassword !=null) {
            boolean isValid = Hashing.verifyPassword(password, hashPassword);
            if (isValid) {
                return Jwt.generateToken(username);
            } else {
                return "Password wrong";
            }
        }
        else {
            return "No username found in this user please register";
        }
    }



}
