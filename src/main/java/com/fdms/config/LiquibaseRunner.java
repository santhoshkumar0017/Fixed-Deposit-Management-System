package com.fdms.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.database.jvm.JdbcConnection;

import java.sql.Connection;

public class LiquibaseRunner {

    public static void run() {
        System.out.println("=== Liquibase Migration Started ===");

        try {
            Connection conn = DataSourceFactory.getConnection();
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(conn));

            Liquibase liquibase = new Liquibase(
                    "db/changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );

            liquibase.update((String) null);
            conn.close();

        } catch (Exception e) {
            throw new RuntimeException("Liquibase migration failed", e);
        }
    }
}
