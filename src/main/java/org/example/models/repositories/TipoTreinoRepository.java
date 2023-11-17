package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.TipoTreino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TipoTreinoRepository implements IRepository<TipoTreino> {

    @Override
    public List<TipoTreino> findAllRepository() throws SQLException {
        List<TipoTreino> tiposTreinos = new ArrayList<>();
        String query = "SELECT * FROM T_VB_TP_TREINO ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                TipoTreino tipoTreino = new TipoTreino(
                        rs.getLong("ID_TP_TREINO"),
                        rs.getString("NM_TP_TREINO"),
                        rs.getString("DESC_TP_TREINO")
                );
                tiposTreinos.add(tipoTreino);
            }
            return tiposTreinos;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<TipoTreino> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_TP_TREINO WHERE ID_TP_TREINO = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    TipoTreino tipoTreino = new TipoTreino(
                            rs.getLong("ID_TP_TREINO"),
                            rs.getString("NM_TP_TREINO"),
                            rs.getString("DESC_TP_TREINO")
                    );
                    return Optional.of(tipoTreino);
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
    public Optional<TipoTreino> insertRepository(TipoTreino tipoTreino) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_TP_TREINO (ID_TP_TREINO, NM_TP_TREINO, DESC_TP_TREINO) VALUES (SQ_VB_TP_TREINO.nextval, ?, ?)";
        String querySelect = "SELECT * FROM T_VB_TP_TREINO ORDER BY ID_TP_TREINO DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setString(1, tipoTreino.getNome());
            statementInsert.setString(2, tipoTreino.getDescricao());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    TipoTreino tipoTreinoCriado = new TipoTreino(
                            rs.getLong("ID_TP_TREINO"),
                            rs.getString("NM_TP_TREINO"),
                            rs.getString("DESC_TP_TREINO")
                    );
                    return Optional.of(tipoTreinoCriado);
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
    public void updateReposiory(TipoTreino tipoTreino) throws SQLException {
        String query = "UPDATE T_VB_TP_TREINO SET NM_TP_TREINO = ?, DESC_TP_TREINO = ? WHERE ID_TP_TREINO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, tipoTreino.getNome());
            ps.setString(2, tipoTreino.getDescricao());
            ps.setLong(3, tipoTreino.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_TP_TREINO WHERE ID_TP_TREINO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
