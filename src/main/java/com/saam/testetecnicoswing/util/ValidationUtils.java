package com.saam.testetecnicoswing.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 *
 * @author guimathis
 */
public final class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final DateTimeFormatter BR_DATE_FORMAT =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private ValidationUtils() {
    }

    public static void validarCadastroUsuario(String nome, String email, String senha) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        validarEmail(email);
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
    }

    public static void validarLogin(String email, String senha) {
        validarEmail(email);
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
    }

    public static void validarEmail(String email) {
        if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Informe um e-mail válido.");
        }
    }

    public static void validarFuncionario(String nome, LocalDate dataAdmissao, BigDecimal salario) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário é obrigatório.");
        }
        if (dataAdmissao == null) {
            throw new IllegalArgumentException("A data de admissão é obrigatória.");
        }
        if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero.");
        }
    }

    public static LocalDate parseData(String dataStr) {
        if (dataStr == null || dataStr.isBlank()) {
            throw new IllegalArgumentException("A data de admissão é obrigatória.");
        }
        String trimmed = dataStr.trim();
        try {
            if (trimmed.contains("/")) {
                return LocalDate.parse(trimmed, BR_DATE_FORMAT);
            }
            return LocalDate.parse(trimmed);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida. Utilize o formato dd/MM/aaaa.");
        }
    }

    public static BigDecimal parseSalario(String salarioStr) {
        if (salarioStr == null || salarioStr.isBlank()) {
            throw new IllegalArgumentException("O valor do salário é obrigatório.");
        }
        String normalized = salarioStr.trim().replace("R$", "").trim();
        if (normalized.contains(",") && normalized.contains(".")) {
            normalized = normalized.replace(".", "").replace(",", ".");
        } else if (normalized.contains(",")) {
            normalized = normalized.replace(",", ".");
        }
        try {
            BigDecimal valor = new BigDecimal(normalized);
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("O salário deve ser maior que zero.");
            }
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor de salário inválido.");
        }
    }
}
