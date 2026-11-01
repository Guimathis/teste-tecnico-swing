package com.saam.testetecnicoswing.util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author guimathis
 */
public final class DialogUtils {

    // Constante para substituir texto em inglês dos botões no dialog
    private static final Object[] SIM_NAO_OPTIONS = {"Sim", "Não"};

    private DialogUtils() {
    }

    public static void showError(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            title,
            JOptionPane.ERROR_MESSAGE,
            IconUtils.load(IconUtils.DELETA_48)
        );
    }

    public static void showInfo(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE,
            IconUtils.load(IconUtils.CONFIRMA)
        );
    }

    public static boolean showConfirm(Component parent, String message, String title) {
        int result = JOptionPane.showOptionDialog(
            parent,
            message,
            title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            IconUtils.load(IconUtils.DELETA_48),
            SIM_NAO_OPTIONS,
            SIM_NAO_OPTIONS[0]
        );
        return result == JOptionPane.YES_OPTION;
    }
}
