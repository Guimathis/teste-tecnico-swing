package com.saam.testetecnicoswing.util;

import java.awt.Component;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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

    public static void showError(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            title,
            JOptionPane.ERROR_MESSAGE,
            load(DELETA_48)
        );
    }

    public static void showInfo(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE,
            load(CONFIRMA)
        );
    }

    public static boolean showConfirm(Component parent, String message, String title) {
        int result = JOptionPane.showConfirmDialog(
            parent,
            message,
            title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            load(CONFIRMA)
        );
        return result == JOptionPane.YES_OPTION;
    }
}
