package com.saam.testetecnicoswing.controller;

import com.saam.testetecnicoswing.dao.FuncionarioDAO;
import com.saam.testetecnicoswing.model.Funcionario;
import com.saam.testetecnicoswing.util.ValidationUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    public List<Funcionario> listarTodos() {
        return funcionarioDAO.select();
    }
}
