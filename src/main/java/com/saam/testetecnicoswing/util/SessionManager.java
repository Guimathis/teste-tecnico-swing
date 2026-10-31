package com.saam.testetecnicoswing.util;

import com.saam.testetecnicoswing.model.Usuario;

/**
 *
 * @author guimathis
 */
public final class SessionManager {

    private static Usuario usuarioLogado;

    private SessionManager() {
    }

    public static void login(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static void logout() {
        usuarioLogado = null;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static boolean isAutenticado() {
        return usuarioLogado != null;
    }
}
