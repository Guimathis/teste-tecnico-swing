package com.saam.testetecnicoswing.dao;

import com.saam.testetecnicoswing.exception.DatabaseException;
import com.saam.testetecnicoswing.config.DatabaseInitializer;
import com.saam.testetecnicoswing.model.Usuario;
import com.saam.testetecnicoswing.util.PasswordUtils;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author guimathis
 */
class UsuarioDAOTest {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @BeforeAll
    static void setUpSchema() {
        DatabaseInitializer.initialize();
    }

    @Test
    void shouldExecuteCrudAndPreventDuplicateEmail() {
        String uniqueEmail = "user_" + UUID.randomUUID() + "@saam.com.br";
        Usuario novo = new Usuario("Teste SAAM", uniqueEmail, PasswordUtils.hashSha256("senha123"));

        Usuario salvo = usuarioDAO.insert(novo);
        assertNotNull(salvo.getId());

        try {
            Optional<Usuario> porEmail = usuarioDAO.selectByEmail(uniqueEmail);
            assertTrue(porEmail.isPresent());
            assertEquals("Teste SAAM", porEmail.get().getNome());

            // Deve rejeitar e-mail duplicado
            Usuario duplicado = new Usuario("Outro", uniqueEmail, PasswordUtils.hashSha256("outra"));
            assertThrows(DatabaseException.class, () -> usuarioDAO.insert(duplicado));

            // Update
            salvo.setNome("Teste SAAM Atualizado");
            usuarioDAO.update(salvo);
            assertEquals("Teste SAAM Atualizado", usuarioDAO.selectById(salvo.getId()).orElseThrow().getNome());
        } finally {
            usuarioDAO.delete(salvo.getId());
            assertTrue(usuarioDAO.selectById(salvo.getId()).isEmpty());
        }
    }
}
