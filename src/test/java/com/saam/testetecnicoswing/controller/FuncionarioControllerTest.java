package com.saam.testetecnicoswing.controller;

import com.saam.testetecnicoswing.config.DatabaseInitializer;
import com.saam.testetecnicoswing.dao.FuncionarioDAO;
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
class FuncionarioControllerTest {

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final FuncionarioController funcionarioController = new FuncionarioController(funcionarioDAO);

    @BeforeAll
    static void setUpSchema() {
        DatabaseInitializer.initialize();
    }

    @Test
    void shouldRegisterAndListEmployeeSuccessfully() {
        Funcionario cadastrado = funcionarioController.cadastrar(
            "Ana Beatriz",
            "15/08/2024",
            "6500,50",
            true
        );

        assertNotNull(cadastrado.getId());
        assertEquals("Ana Beatriz", cadastrado.getNome());
        assertEquals(LocalDate.of(2024, 8, 15), cadastrado.getDataAdmissao());
        assertEquals(new BigDecimal("6500.50"), cadastrado.getSalario());
        assertTrue(cadastrado.isStatus());

        try {
            List<Funcionario> lista = funcionarioController.listarTodos();
            assertTrue(lista.stream().anyMatch(f -> f.getId().equals(cadastrado.getId())));
        } finally {
            funcionarioDAO.delete(cadastrado.getId());
        }
    }

    @Test
    void shouldRejectInvalidEmployeeInputs() {
        assertThrows(IllegalArgumentException.class,
            () -> funcionarioController.cadastrar("", "15/08/2024", "3000", true));
        assertThrows(IllegalArgumentException.class,
            () -> funcionarioController.cadastrar("Ana", "data-invalida", "3000", true));
        assertThrows(IllegalArgumentException.class,
            () -> funcionarioController.cadastrar("Ana", "15/08/2024", "-100", true));
    }
}
