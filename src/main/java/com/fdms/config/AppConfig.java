package com.fdms.config;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static final Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = AppConfig.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");

            if (inputStream == null) {
                throw new RuntimeException("application.properties not found in classpath");
            }

            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}
