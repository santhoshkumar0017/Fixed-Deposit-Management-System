//package com.fdms.config;
//
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//
//@WebListener
//public class AppStartupListener implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        System.out.println("=== FDMS Startup: Initializing DataSource and Liquibase ===");
//
//        try {
//
//            LiquibaseRunner.run();
//
//            System.out.println("=== Liquibase Migration Completed Successfully ===");
//
//        } catch (Exception e) {
//            System.err.println("Liquibase failed on startup: " + e.getMessage());
//            e.printStackTrace();
//
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("=== FDMS Shutdown ===");
//    }
//}
