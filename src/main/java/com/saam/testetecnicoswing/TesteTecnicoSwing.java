package com.saam.testetecnicoswing;

import com.saam.testetecnicoswing.exception.DatabaseException;
import com.saam.testetecnicoswing.config.DatabaseInitializer;
import com.saam.testetecnicoswing.view.LoginView;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author guimathis
 */
public class TesteTecnicoSwing {

    public static void main(String[] args) {
        configurarLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            try {
                DatabaseInitializer.initialize();
                new LoginView().setVisible(true);
            } catch (DatabaseException ex) {
                JOptionPane.showMessageDialog(
                    null,
                    ex.getMessage(),
                    "Erro de Conexão com o Banco de Dados",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    private static void configurarLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
    }
}
