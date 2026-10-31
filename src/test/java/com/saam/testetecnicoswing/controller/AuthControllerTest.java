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

    @Test
    void shouldFailRegistrationWhenEmailAlreadyExists() {
        String email = "dup_" + UUID.randomUUID() + "@saam.com.br";
        Usuario criado = authController.cadastrar("User A", email, "SenhaForte@123");
        try {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> authController.cadastrar("User B", email, "OutraSenha@123")
            );
            assertEquals("Este e-mail já está cadastrado.", ex.getMessage());
        } finally {
            usuarioDAO.delete(criado.getId());
        }
    }

    @Test
    void shouldFailUpdateWhenEmailAlreadyInUseByAnotherUser() {
        String email1 = "user1_" + UUID.randomUUID() + "@saam.com.br";
        String email2 = "user2_" + UUID.randomUUID() + "@saam.com.br";
        Usuario user1 = authController.cadastrar("User 1", email1, "SenhaForte@123");
        Usuario user2 = authController.cadastrar("User 2", email2, "SenhaForte@123");

        try {
            authController.autenticar(email2, "SenhaForte@123");
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> authController.atualizarUsuarioLogado("User 2 Modificado", email1, "SenhaForte@123")
            );
            assertEquals("Este e-mail já está cadastrado.", ex.getMessage());
        } finally {
            authController.logout();
            usuarioDAO.delete(user1.getId());
            usuarioDAO.delete(user2.getId());
        }
    }
}
