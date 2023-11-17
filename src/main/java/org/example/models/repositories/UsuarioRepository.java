package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository implements  IRepository<Usuario>{

    private final BiotipoRepository biotipoRepository = new BiotipoRepository();
    private final TreinoRepository treinoRepository = new TreinoRepository();
    private final DietaRepository dietaRepository = new DietaRepository();


    @Override
    public List<Usuario> findAllRepository() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM T_VB_USUARIO ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Usuario usuario = new Usuario(
                        rs.getLong("ID_USUARIO"),
                        biotipoRepository.findByIdRepository(rs.getLong("ID_BIOTIPO")).orElse(null),
                        treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                        dietaRepository.findByIdRepository(rs.getLong("ID_DIETA")).orElse(null),
                        rs.getString("NOME_USUARIO"),
                        rs.getString("GENERO_USUARIO"),
                        rs.getInt("IDADE_USUARIO"),
                        rs.getString("EMAIL_USUARIO"),
                        rs.getString("SENHA_USUARIO")
                );
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Usuario> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_USUARIO WHERE ID_USUARIO = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getLong("ID_USUARIO"),
                            biotipoRepository.findByIdRepository(rs.getLong("ID_BIOTIPO")).orElse(null),
                            treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                            dietaRepository.findByIdRepository(rs.getLong("ID_DIETA")).orElse(null),
                            rs.getString("NOME_USUARIO"),
                            rs.getString("GENERO_USUARIO"),
                            rs.getInt("IDADE_USUARIO"),
                            rs.getString("EMAIL_USUARIO"),
                            rs.getString("SENHA_USUARIO")
                    );
                    return Optional.of(usuario);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return Optional.empty();
    }

    public Optional<Usuario> findByEmailRepository(String email) throws SQLException {
        String query = "SELECT * FROM T_VB_USUARIO WHERE EMAIL_USUARIO = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getLong("ID_USUARIO"),
                            biotipoRepository.findByIdRepository(rs.getLong("ID_BIOTIPO")).orElse(null),
                            treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                            dietaRepository.findByIdRepository(rs.getLong("ID_DIETA")).orElse(null),
                            rs.getString("NOME_USUARIO"),
                            rs.getString("GENERO_USUARIO"),
                            rs.getInt("IDADE_USUARIO"),
                            rs.getString("EMAIL_USUARIO"),
                            rs.getString("SENHA_USUARIO")
                    );
                    return Optional.of(usuario);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        catch (SQLException e) {
            if(e.getErrorCode() == 1017) { // Erro de login/senha inválido
                throw new SQLException("Falha de autenticação ao conectar ao banco de dados.", e);
            } else if(e.getErrorCode() == 904) { // Erro de coluna inválida
                throw new SQLException("A query contém uma coluna inválida.", e);
            } else {
                throw new SQLException("Erro ao executar a query.", e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> insertRepository(Usuario usuario) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_USUARIO (ID_USUARIO, ID_BIOTIPO, ID_TREINO, ID_DIETA, NOME_USUARIO, GENERO_USUARIO, IDADE_USUARIO, EMAIL_USUARIO, SENHA_USUARIO) VALUES (SQ_VB_USUARIO.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
        String querySelect = "SELECT * FROM T_VB_USUARIO ORDER BY ID_USUARIO DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setLong(1, usuario.getBiotipo().getId());
            statementInsert.setLong(2, usuario.getTreino().getId());
            statementInsert.setLong(3, usuario.getDieta().getId());
            statementInsert.setString(4, usuario.getNome());
            statementInsert.setString(5, usuario.getGenero());
            statementInsert.setInt(6, usuario.getIdade());
            statementInsert.setString(7, usuario.getEmail());
            statementInsert.setString(8, usuario.getSenha());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    Usuario usuarioCriado = new Usuario(
                            rs.getLong("ID_USUARIO"),
                            biotipoRepository.findByIdRepository(rs.getLong("ID_BIOTIPO")).orElse(null),
                            treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                            dietaRepository.findByIdRepository(rs.getLong("ID_DIETA")).orElse(null),
                            rs.getString("NOME_USUARIO"),
                            rs.getString("GENERO_USUARIO"),
                            rs.getInt("IDADE_USUARIO"),
                            rs.getString("EMAIL_USUARIO"),
                            rs.getString("SENHA_USUARIO")
                    );
                    return Optional.of(usuarioCriado);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateReposiory(Usuario usuario) throws SQLException {
        String query = "UPDATE T_VB_USUARIO SET ID_BIOTIPO = ?, ID_TREINO = ?, ID_DIETA = ?, NOME_USUARIO = ?, GENERO_USUARIO = ?, IDADE_USUARIO = ?, EMAIL_USUARIO = ?, SENHA_USUARIO = ? WHERE ID_USUARIO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, usuario.getBiotipo().getId());
            ps.setLong(2, usuario.getTreino().getId());
            ps.setLong(3, usuario.getDieta().getId());
            ps.setString(4, usuario.getNome());
            ps.setString(5, usuario.getGenero());
            ps.setInt(6, usuario.getIdade());
            ps.setString(7, usuario.getEmail());
            ps.setString(8, usuario.getSenha());
            ps.setLong(9, usuario.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_USUARIO WHERE ID_USUARIO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
