package com.fdms.dao;


import com.fdms.config.DatabaseConfig;
import com.fdms.exception.DataException;
import com.fdms.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final Logger log= LoggerFactory.getLogger(UserDAO.class);

    static final int INSERT_USER_NAME=1;
    static final int INSERT_USER_PASSWORD=2;

    private static final int GET_PASSWORD=1;


    String InsertSQL="Insert into users(username,password)" +
            "VALUES(?,?)";

    String GET_PASS=" select password from users where username = ?";


    public void adduser(User user){

        try(Connection con= DatabaseConfig.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(InsertSQL)){

            ps.setString(INSERT_USER_NAME,user.getUsername());
            ps.setString(INSERT_USER_PASSWORD, user.getPassword());

            int changedRows=ps.executeUpdate();

            if(changedRows==0){
                log.error("Insert failed,fields are not inserted in user:{}",user.getId());
            }

            else{
                log.info("sucessfully values are inserted in account:{}",user.getId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPass(String username) {
        try(Connection con= DatabaseConfig.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(GET_PASS))
        {
            ps.setString(GET_PASSWORD,username);
            ResultSet rs = ps.executeQuery();
            log.info("Query Executed");
            if(rs.next()) {
                return rs.getString("password");
            } else {
                return null;
            }
        }catch (SQLException e) {
            throw new DataException("Failed to get Password from db",e);
        }

    }



}