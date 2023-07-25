package org.atrokhau.vasili.util;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

    private DBUtil() {
    }

    @SneakyThrows
    public static Connection getConnection() {
        Properties applicationProperties = ApplicationPropertiesUtil.readProperties();
        String dbUser = applicationProperties.getProperty("db.username");
        String dbPassword = applicationProperties.getProperty("db.password");
        String dbUrl = applicationProperties.getProperty("db.url");
        String driverClassName = applicationProperties.getProperty("jdbc.driver");

        Class.forName(driverClassName);

        return DriverManager.getConnection(dbUrl,dbUser,dbPassword);
    }
}
