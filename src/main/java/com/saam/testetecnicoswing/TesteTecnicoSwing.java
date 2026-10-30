package com.saam.testetecnicoswing;

import com.saam.testetecnicoswing.config.DatabaseException;
import com.saam.testetecnicoswing.config.DatabaseInitializer;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author guimathis
 */
public class TesteTecnicoSwing {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DatabaseInitializer.initialize();
                System.out.println("Banco de dados PostgreSQL conectado e inicializado com sucesso!");
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
}
