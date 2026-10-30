package com.saam.testetecnicoswing.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author guimathis
 */
public final class ConnectionFactory {

    private static final String URL = env("DB_URL", "jdbc:postgresql://localhost:5432/saam_db");
    private static final String USER = env("DB_USER", "postgres");
    private static final String PASSWORD = env("DB_PASSWORD", "postgres");

    private ConnectionFactory() {}

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? defaultValue : value.trim();
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseException(
                "Falha ao conectar em " + URL + ". Verifique se o PostgreSQL está em execução e se o banco de dados foi criado.", e);
        }
    }
}

