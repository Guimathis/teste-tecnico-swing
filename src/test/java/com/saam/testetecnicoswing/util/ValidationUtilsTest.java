package com.saam.testetecnicoswing.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author guimathis
 */
class ValidationUtilsTest {

    @Test
    void shouldValidateUserRegistrationSuccessfully() {
        assertDoesNotThrow(() -> ValidationUtils.validarCadastroUsuario("João Silva", "joao@saam.com.br", "123456"));
    }

    @Test
    void shouldRejectInvalidUserRegistrationFields() {
        assertThrows(IllegalArgumentException.class,
            () -> ValidationUtils.validarCadastroUsuario("", "joao@saam.com.br", "123456"));
        assertThrows(IllegalArgumentException.class,
            () -> ValidationUtils.validarCadastroUsuario("João", "email-invalido", "123456"));
        assertThrows(IllegalArgumentException.class,
            () -> ValidationUtils.validarCadastroUsuario("João", "joao@saam.com.br", ""));
    }

    @Test
    void shouldParseDateAndSalarioCorrectly() {
        assertEquals(LocalDate.of(2025, 5, 10), ValidationUtils.parseData("10/05/2025"));
        assertEquals(LocalDate.of(2025, 5, 10), ValidationUtils.parseData("2025-05-10"));
        assertEquals(new BigDecimal("3500.50"), ValidationUtils.parseSalario("3.500,50"));
        assertEquals(new BigDecimal("3500.50"), ValidationUtils.parseSalario("3500.50"));
    }

    @Test
    void shouldRejectInvalidDateOrSalario() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.parseData("32/13/2025"));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.parseSalario("-100"));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.parseSalario("abc"));
    }
}
