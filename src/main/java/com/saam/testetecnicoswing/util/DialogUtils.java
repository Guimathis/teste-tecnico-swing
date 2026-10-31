package com.saam.testetecnicoswing.util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author guimathis
 */
public final class DialogUtils {

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
        int result = JOptionPane.showConfirmDialog(
            parent,
            message,
            title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            IconUtils.load(IconUtils.CONFIRMA)
        );
        return result == JOptionPane.YES_OPTION;
    }
}
