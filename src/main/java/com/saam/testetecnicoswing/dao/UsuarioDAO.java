package com.saam.testetecnicoswing.dao;

import com.saam.testetecnicoswing.config.ConnectionFactory;
import com.saam.testetecnicoswing.exception.DatabaseException;
import com.saam.testetecnicoswing.model.Usuario;
import java.sql.Connection;
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
public class UsuarioDAO implements Dao<Usuario> {

    private static final String SQL_STATE_UNIQUE_VIOLATION = "23505";

    @Override
    public Usuario insert(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(UUID.randomUUID());
        }
        String sql = "INSERT INTO usuarios (id, nome, email, senha_hash) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getId().toString());
            stmt.setString(2, usuario.getNome().trim());
            stmt.setString(3, usuario.getEmail().trim().toLowerCase());
            stmt.setString(4, usuario.getSenha());
            stmt.executeUpdate();
            return usuario;
        } catch (SQLException e) {
            if (SQL_STATE_UNIQUE_VIOLATION.equals(e.getSQLState())) {
                throw new IllegalArgumentException("Este e-mail já está cadastrado.", e);
            }
            throw new DatabaseException("Erro ao cadastrar usuário no banco de dados.", e);
        }
    }

    @Override
    public List<Usuario> select() {
        String sql = "SELECT id, nome, email, senha_hash FROM usuarios ORDER BY criado_em DESC";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapRow(rs));
            }
            return usuarios;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar usuários.", e);
        }
    }

    @Override
    public Optional<Usuario> selectById(UUID id) {
        String sql = "SELECT id, nome, email, senha_hash FROM usuarios WHERE id = ?";
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
            throw new DatabaseException("Erro ao buscar usuário por ID.", e);
        }
    }

    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        String sql = "SELECT 1 FROM usuarios WHERE LOWER(email) = LOWER(?) LIMIT 1";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar existência de e-mail.", e);
        }
    }

    public Optional<Usuario> selectByEmail(String email) {
        String sql = "SELECT id, nome, email, senha_hash FROM usuarios WHERE LOWER(email) = LOWER(?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário por e-mail.", e);
        }
    }

    @Override
    public void update(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha_hash = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome().trim());
            stmt.setString(2, usuario.getEmail().trim().toLowerCase());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (SQL_STATE_UNIQUE_VIOLATION.equals(e.getSQLState())) {
                throw new IllegalArgumentException("Este e-mail já está cadastrado.", e);
            }
            throw new DatabaseException("Erro ao atualizar usuário.", e);
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir usuário.", e);
        }
    }

    private Usuario mapRow(ResultSet rs) throws SQLException {
        return new Usuario(
            UUID.fromString(rs.getString("id")),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("senha_hash")
        );
    }
}
