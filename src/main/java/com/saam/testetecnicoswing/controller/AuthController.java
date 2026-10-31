package com.saam.testetecnicoswing.controller;

import com.saam.testetecnicoswing.dao.UsuarioDAO;
import com.saam.testetecnicoswing.model.Usuario;
import com.saam.testetecnicoswing.util.PasswordUtils;
import com.saam.testetecnicoswing.util.SessionManager;
import com.saam.testetecnicoswing.util.ValidationUtils;

/**
 *
 * @author guimathis
 */
public class AuthController {

    private final UsuarioDAO usuarioDAO;

    public AuthController() {
        this(new UsuarioDAO());
    }

    public AuthController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario cadastrar(String nome, String email, String senha) {
        ValidationUtils.validarCadastroUsuario(nome, email, senha);
        String senhaHash = PasswordUtils.hashSha256(senha);
        Usuario novo = new Usuario(nome.trim(), email.trim().toLowerCase(), senhaHash);
        return usuarioDAO.insert(novo);
    }

    public Usuario autenticar(String email, String senha) {
        ValidationUtils.validarLogin(email, senha);
        String senhaHash = PasswordUtils.hashSha256(senha);

        Usuario autenticado = usuarioDAO.selectByEmail(email)
            .filter(u -> u.getSenha().equals(senhaHash))
            .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos."));

        SessionManager.login(autenticado);
        return autenticado;
    }

    public void logout() {
        SessionManager.logout();
    }
}
