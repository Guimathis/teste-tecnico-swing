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
    void shouldRegisterAuthenticateUpdateAndDeleteLoggedUser() {
        String email = "auth_" + UUID.randomUUID() + "@saam.com.br";
        Usuario cadastrado = authController.cadastrar("Carlos Souza", email, "MinhaSenha@2026");

        assertNotNull(cadastrado.getId());
        assertEquals(PasswordUtils.hashSha256("MinhaSenha@2026"), cadastrado.getSenha());

        authController.autenticar(email, "MinhaSenha@2026");
        assertTrue(SessionManager.isAutenticado());

        // Atualizar usuario logado
        String novoEmail = "edit_" + UUID.randomUUID() + "@saam.com.br";
        Usuario atualizado = authController.atualizarUsuarioLogado("Carlos Editado", novoEmail, "NovaSenha@123");
        assertEquals("Carlos Editado", atualizado.getNome());
        assertEquals(novoEmail, SessionManager.getUsuarioLogado().getEmail());
        assertEquals(PasswordUtils.hashSha256("NovaSenha@123"), atualizado.getSenha());

        // Excluir usuario logado
        authController.excluirUsuarioLogado();
        assertFalse(SessionManager.isAutenticado());
        assertTrue(usuarioDAO.selectById(cadastrado.getId()).isEmpty());
    }

    @Test
    void shouldFailAuthenticationWhenUserDoesNotExistOrFieldsAreInvalid() {
        assertThrows(IllegalArgumentException.class,
            () -> authController.autenticar("inexistente_" + UUID.randomUUID() + "@saam.com.br", "123456"));

        assertThrows(IllegalArgumentException.class,
            () -> authController.cadastrar("", "invalido", ""));
    }
}
