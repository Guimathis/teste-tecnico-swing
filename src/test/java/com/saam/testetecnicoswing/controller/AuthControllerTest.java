package com.saam.testetecnicoswing.controller;

import com.saam.testetecnicoswing.config.DatabaseInitializer;
import com.saam.testetecnicoswing.dao.UsuarioDAO;
import com.saam.testetecnicoswing.model.Usuario;
import com.saam.testetecnicoswing.util.PasswordUtils;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author guimathis
 */
class AuthControllerTest {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final AuthController authController = new AuthController(usuarioDAO);

    @BeforeAll
    static void setUpSchema() {
        DatabaseInitializer.initialize();
    }

    @Test
    void shouldRegisterUserWithSha256AndAuthenticateSuccessfully() {
        String email = "auth_" + UUID.randomUUID() + "@saam.com.br";
        Usuario cadastrado = authController.cadastrar("Carlos Souza", email, "MinhaSenha@2026");

        assertNotNull(cadastrado.getId());
        assertEquals(PasswordUtils.hashSha256("MinhaSenha@2026"), cadastrado.getSenha());
        assertNotEquals("MinhaSenha@2026", cadastrado.getSenha());

        try {
            Usuario autenticado = authController.autenticar(email, "MinhaSenha@2026");
            assertEquals(cadastrado.getId(), autenticado.getId());
            assertEquals("Carlos Souza", autenticado.getNome());

            // Senha incorreta deve falhar
            assertThrows(IllegalArgumentException.class,
                () -> authController.autenticar(email, "SenhaErrada"));
        } finally {
            usuarioDAO.delete(cadastrado.getId());
        }
    }

    @Test
    void shouldFailAuthenticationWhenUserDoesNotExistOrFieldsAreInvalid() {
        assertThrows(IllegalArgumentException.class,
            () -> authController.autenticar("inexistente_" + UUID.randomUUID() + "@saam.com.br", "123456"));

        assertThrows(IllegalArgumentException.class,
            () -> authController.cadastrar("", "invalido", ""));
    }
}
