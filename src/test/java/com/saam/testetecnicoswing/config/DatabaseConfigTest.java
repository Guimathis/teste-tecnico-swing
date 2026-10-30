package com.saam.testetecnicoswing.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author guimathis
 */
class DatabaseConfigTest {

    @Test
    void shouldConnectAndInitializeSchemaSuccessfully() throws Exception {
        assertDoesNotThrow(DatabaseInitializer::initialize);

        try (Connection conn = ConnectionFactory.getConnection()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());

            Set<String> usuarioCols = getTableColumns(conn, "usuarios");
            assertTrue(usuarioCols.containsAll(Set.of("id", "nome", "email", "senha_hash")));

            Set<String> funcCols = getTableColumns(conn, "funcionarios");
            assertTrue(funcCols.containsAll(Set.of("id", "nome", "data_admissao", "salario", "status")));
        }
    }

    private Set<String> getTableColumns(Connection conn, String tableName) throws Exception {
        Set<String> columns = new HashSet<>();
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME").toLowerCase());
            }
        }
        return columns;
    }
}

