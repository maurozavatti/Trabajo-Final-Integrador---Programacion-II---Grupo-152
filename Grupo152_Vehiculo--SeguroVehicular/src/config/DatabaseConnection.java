package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "/config/database.properties";
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        try (InputStream is = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties props = new Properties();
            if (is == null) {
                throw new RuntimeException("No se encontró " + PROPERTIES_FILE + " en el classpath.");
            }
            props.load(is);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error cargando configuración de la BD: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
