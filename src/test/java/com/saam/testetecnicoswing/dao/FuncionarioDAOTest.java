package com.saam.testetecnicoswing.dao;

import com.saam.testetecnicoswing.config.DatabaseInitializer;
import com.saam.testetecnicoswing.model.Funcionario;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author guimathis
 */
class FuncionarioDAOTest {

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @BeforeAll
    static void setUpSchema() {
        DatabaseInitializer.initialize();
    }

    @Test
    void shouldExecuteCrudOperationsOnFuncionario() {
        Funcionario novo = new Funcionario(
            "Maria Oliveira",
            LocalDate.of(2024, 3, 15),
            new BigDecimal("4500.00"),
            true
        );

        Funcionario salvo = funcionarioDAO.insert(novo);
        assertNotNull(salvo.getId());

        try {
            List<Funcionario> lista = funcionarioDAO.select();
            assertTrue(lista.stream().anyMatch(f -> f.getId().equals(salvo.getId())));

            salvo.setStatus(false);
            salvo.setSalario(new BigDecimal("5200.00"));
            funcionarioDAO.update(salvo);

            Funcionario atualizado = funcionarioDAO.selectById(salvo.getId()).orElseThrow();
            assertFalse(atualizado.isStatus());
            assertEquals(new BigDecimal("5200.00"), atualizado.getSalario());
        } finally {
            funcionarioDAO.delete(salvo.getId());
            assertTrue(funcionarioDAO.selectById(salvo.getId()).isEmpty());
        }
    }
}
