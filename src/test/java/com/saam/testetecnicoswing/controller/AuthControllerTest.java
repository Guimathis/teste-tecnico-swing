package com.saam.testetecnicoswing.controller;

import com.saam.testetecnicoswing.config.DatabaseInitializer;
import com.saam.testetecnicoswing.dao.UsuarioDAO;
import com.saam.testetecnicoswing.model.Usuario;
import com.saam.testetecnicoswing.util.PasswordUtils;
import com.saam.testetecnicoswing.util.SessionManager;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        SessionManager.logout();
    }

    @Test
    void shouldRegisterUserWithSha256AndAuthenticateMaintainingSession() {
        String email = "auth_" + UUID.randomUUID() + "@saam.com.br";
        Usuario cadastrado = authController.cadastrar("Carlos Souza", email, "MinhaSenha@2026");

        assertNotNull(cadastrado.getId());
        assertEquals(PasswordUtils.hashSha256("MinhaSenha@2026"), cadastrado.getSenha());
        assertFalse(SessionManager.isAutenticado());

        try {
            Usuario autenticado = authController.autenticar(email, "MinhaSenha@2026");
            assertEquals(cadastrado.getId(), autenticado.getId());
            assertTrue(SessionManager.isAutenticado());
            assertEquals("Carlos Souza", SessionManager.getUsuarioLogado().getNome());

            authController.logout();
            assertFalse(SessionManager.isAutenticado());
            assertNull(SessionManager.getUsuarioLogado());

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
