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

    private final TreinoRepository treinoRepository = new TreinoRepository();

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
                        treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                        rs.getString("NM_TP_TREINO"),
                        rs.getString("DS_TP_TREINO")
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
                            treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                            rs.getString("NM_TP_TREINO"),
                            rs.getString("DS_TP_TREINO")
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
        String queryInsert = "INSERT INTO T_VB_TP_TREINO (ID_TP_TREINO, ID_TREINO, NM_TP_TREINO, DS_TP_TREINO, DT_CADASTRO, NM_USUARIO) VALUES (SQ_VB_TIPO_TREINO.nextval, ?, ?, ?, SYSDATE, USER)";
        String querySelect = "SELECT * FROM T_VB_TP_TREINO ORDER BY ID_TP_TREINO DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setLong(1, tipoTreino.getTreino().getId());
            statementInsert.setString(2, tipoTreino.getNome());
            statementInsert.setString(3, tipoTreino.getDescricao());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    TipoTreino novoTipoTreino = new TipoTreino(
                            rs.getLong("ID_TP_TREINO"),
                            treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                            rs.getString("NM_TP_TREINO"),
                            rs.getString("DS_TP_TREINO")
                    );
                    return Optional.of(novoTipoTreino);
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
        String query = "UPDATE T_VB_TP_TREINO SET ID_TREINO = ?, NM_TP_TREINO = ?, DS_TP_TREINO = ? WHERE ID_TP_TREINO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, tipoTreino.getTreino().getId());
            ps.setString(2, tipoTreino.getNome());
            ps.setString(3, tipoTreino.getDescricao());
            ps.setLong(4, tipoTreino.getId());

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
