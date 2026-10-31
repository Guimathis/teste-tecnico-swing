package com.saam.testetecnicoswing.controller;

import com.saam.testetecnicoswing.dao.FuncionarioDAO;
import com.saam.testetecnicoswing.model.Funcionario;
import com.saam.testetecnicoswing.util.ValidationUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author guimathis
 */
public class FuncionarioController {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this(new FuncionarioDAO());
    }

    public FuncionarioController(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    public Funcionario cadastrar(String nome, String dataAdmissaoStr, String salarioStr, boolean status) {
        LocalDate dataAdmissao = ValidationUtils.parseData(dataAdmissaoStr);
        BigDecimal salario = ValidationUtils.parseSalario(salarioStr);
        ValidationUtils.validarFuncionario(nome, dataAdmissao, salario);

        Funcionario novo = new Funcionario(nome.trim(), dataAdmissao, salario, status);
        return funcionarioDAO.insert(novo);
    }

    public Funcionario atualizar(UUID id, String nome, String dataAdmissaoStr, String salarioStr, boolean status) {
        if (id == null) {
            throw new IllegalArgumentException("ID do funcionário é obrigatório para alteração.");
        }
        LocalDate dataAdmissao = ValidationUtils.parseData(dataAdmissaoStr);
        BigDecimal salario = ValidationUtils.parseSalario(salarioStr);
        ValidationUtils.validarFuncionario(nome, dataAdmissao, salario);

        Funcionario existente = new Funcionario(id, nome.trim(), dataAdmissao, salario, status);
        funcionarioDAO.update(existente);
        return existente;
    }

    public void excluir(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Selecione um funcionário para excluir.");
        }
        funcionarioDAO.delete(id);
    }

    public Funcionario buscarPorId(UUID id) {
        return funcionarioDAO.selectById(id)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
    }

    public List<Funcionario> listarTodos() {
        return funcionarioDAO.select();
    }
}
