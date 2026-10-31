package com.saam.testetecnicoswing.util;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author guimathis
 */
public final class IconUtils {

    public static final String ADICIONA = "adiciona24.png";
    public static final String CONFIRMA = "confirma24.png";
    public static final String DELETA = "deleta24.png";
    public static final String DELETA_48 = "deleta48.png";
    public static final String EDIT = "Edit24.png";
    public static final String FUNCIONARIO = "funcionarioAdd48.png";
    public static final String LIXEIRA = "lixeira24.png";
    public static final String SAIR = "sair48.png";
    public static final String SAVE = "confirma24.png";
    public static final String UNDO = "Undo24.png";
    public static final String USUARIO = "usuario48.png";

    private IconUtils() {
    }

    public static ImageIcon load(String iconName) {
        URL url = IconUtils.class.getResource("/icons/" + iconName);
        if (url == null) {
            throw new IllegalStateException("Ícone não encontrado em /icons/" + iconName);
        }
        return new ImageIcon(url);
    }
}
