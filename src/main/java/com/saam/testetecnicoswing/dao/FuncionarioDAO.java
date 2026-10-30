package com.saam.testetecnicoswing.dao;

import com.saam.testetecnicoswing.config.ConnectionFactory;
import com.saam.testetecnicoswing.exception.DatabaseException;
import com.saam.testetecnicoswing.model.Funcionario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author guimathis
 */
public class FuncionarioDAO implements Dao<Funcionario> {

    @Override
    public Funcionario insert(Funcionario funcionario) {
        if (funcionario.getId() == null) {
            funcionario.setId(UUID.randomUUID());
        }
        String sql = "INSERT INTO funcionarios (id, nome, data_admissao, salario, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getId().toString());
            stmt.setString(2, funcionario.getNome().trim());
            stmt.setDate(3, Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setBigDecimal(4, funcionario.getSalario());
            stmt.setBoolean(5, funcionario.isStatus());
            stmt.executeUpdate();
            return funcionario;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao cadastrar funcionário no banco de dados.", e);
        }
    }

    @Override
    public List<Funcionario> select() {
        String sql = "SELECT id, nome, data_admissao, salario, status FROM funcionarios ORDER BY criado_em DESC";
        List<Funcionario> funcionarios = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                funcionarios.add(mapRow(rs));
            }
            return funcionarios;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao consultar funcionários cadastrados.", e);
        }
    }

    @Override
    public Optional<Funcionario> selectById(UUID id) {
        String sql = "SELECT id, nome, data_admissao, salario, status FROM funcionarios WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar funcionário por ID.", e);
        }
    }

    @Override
    public void update(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET nome = ?, data_admissao = ?, salario = ?, status = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome().trim());
            stmt.setDate(2, Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setBigDecimal(3, funcionario.getSalario());
            stmt.setBoolean(4, funcionario.isStatus());
            stmt.setString(5, funcionario.getId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar funcionário.", e);
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir funcionário.", e);
        }
    }

    private Funcionario mapRow(ResultSet rs) throws SQLException {
        return new Funcionario(
            UUID.fromString(rs.getString("id")),
            rs.getString("nome"),
            rs.getDate("data_admissao").toLocalDate(),
            rs.getBigDecimal("salario"),
            rs.getBoolean("status")
        );
    }
}
