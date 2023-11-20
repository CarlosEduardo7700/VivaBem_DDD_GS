package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.Treino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreinoRepository implements IRepository<Treino>{

    @Override
    public List<Treino> findAllRepository() throws SQLException {
        List<Treino> treinos = new ArrayList<>();
        String query = "SELECT * FROM T_VB_TREINO ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Treino treino = new Treino(
                        rs.getLong("ID_TREINO"),
                        rs.getString("NM_TREINO"),
                        rs.getString("DS_TREINO")
                );
                treinos.add(treino);
            }
            return treinos;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Treino> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_TREINO WHERE ID_TREINO = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Treino treino = new Treino(
                            rs.getLong("ID_TREINO"),
                            rs.getString("NM_TREINO"),
                            rs.getString("DS_TREINO")
                    );
                    return Optional.of(treino);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Treino> insertRepository(Treino treino) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_TREINO (ID_TREINO, NM_TREINO, DS_TREINO, DT_CADASTRO, NM_USUARIO) VALUES (SQ_VB_TREINO.nextval, ?, ?, SYSDATE, USER)";
        String querySelect = "SELECT * FROM T_VB_TREINO ORDER BY ID_TREINO DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setString(1, treino.getNome());
            statementInsert.setString(2, treino.getDescricao());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    Treino novoTreino = new Treino(
                            rs.getLong("ID_TREINO"),
                            rs.getString("NM_TREINO"),
                            rs.getString("DS_TREINO")
                    );
                    return Optional.of(novoTreino);
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
    public void updateReposiory(Treino treino) throws SQLException {
        String query = "UPDATE T_VB_TREINO SET NM_TREINO = ?, DS_TREINO = ? WHERE ID_TREINO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, treino.getNome());
            ps.setString(2, treino.getDescricao());
            ps.setLong(3, treino.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_TREINO WHERE ID_TREINO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
