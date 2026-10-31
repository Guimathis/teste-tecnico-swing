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

    public Usuario atualizarUsuarioLogado(String nome, String email, String novaSenha) {
        Usuario logado = SessionManager.getUsuarioLogado();
        if (logado == null) {
            throw new IllegalStateException("Nenhum usuário autenticado na sessão.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        ValidationUtils.validarEmail(email);

        String senhaHash = (novaSenha == null || novaSenha.isBlank())
            ? logado.getSenha()
            : PasswordUtils.hashSha256(novaSenha);

        Usuario atualizado = new Usuario(
            logado.getId(),
            nome.trim(),
            email.trim().toLowerCase(),
            senhaHash
        );
        usuarioDAO.update(atualizado);
        SessionManager.login(atualizado);
        return atualizado;
    }

    public void excluirUsuarioLogado() {
        Usuario logado = SessionManager.getUsuarioLogado();
        if (logado == null) {
            throw new IllegalStateException("Nenhum usuário autenticado na sessão.");
        }
        usuarioDAO.delete(logado.getId());
        SessionManager.logout();
    }

    public void logout() {
        SessionManager.logout();
    }
}
