package com.fdms.config;

import com.fdms.exception.DataException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static HikariDataSource dataSource;
    private static boolean liquibaseExecuted = false;
    private static final HikariDataSource datasource ;

    static {

        try{
            Properties props=new Properties();


            InputStream input=Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("application.properties");


            if(input==null){
                throw new DataException("db properties files are not found ");
            }

            props.load(input);
            Class.forName(props.getProperty("db.DRIVER"));

            HikariConfig config=new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.URL"));
            config.setUsername(props.getProperty("db.USER"));
            config.setPassword(props.getProperty("db.PASSWORD"));

            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("Hikari.maximumPool")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("Hikari.minimumIdle")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("Hikari.idletimeout")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("Hikari.connectionTimeout")));
            datasource=new HikariDataSource(config);

        } catch (Exception e) {
            throw new DataException("Failed to Initialize Database connection ",e);
        }



    }

    public static DataSource getConnect(){
        return datasource;
    }
}
