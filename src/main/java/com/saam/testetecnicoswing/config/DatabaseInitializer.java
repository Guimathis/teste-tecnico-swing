package com.saam.testetecnicoswing.config;

import com.saam.testetecnicoswing.exception.DatabaseException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author guimathis
 */
public final class DatabaseInitializer {

    private static final String SCHEMA_PATH = "/sql/schema.sql";

    private DatabaseInitializer() {}

    public static void initialize() {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(loadSchemaSql());
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao executar o script de criação do schema.", e);
        }
    }

    private static String loadSchemaSql() {
        try (InputStream is = DatabaseInitializer.class.getResourceAsStream(SCHEMA_PATH)) {
            if (is == null) {
                throw new DatabaseException("Schema SQL não encontrado em: " + SCHEMA_PATH);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DatabaseException("Erro ao ler o schema SQL: " + SCHEMA_PATH, e);
        }
    }
}

