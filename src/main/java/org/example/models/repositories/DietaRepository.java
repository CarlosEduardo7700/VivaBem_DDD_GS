package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.Dieta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DietaRepository implements IRepository<Dieta> {
    @Override
    public List<Dieta> findAllRepository() throws SQLException {
        List<Dieta> dietas = new ArrayList<>();
        String query = "SELECT * FROM T_VB_DIETA ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Dieta dieta = new Dieta(
                        rs.getLong("ID_DIETA"),
                        rs.getString("NM_DIETA"),
                        rs.getString("DS_DIETA")
                );
                dietas.add(dieta);
            }
            return dietas;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Dieta> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_DIETA WHERE ID_DIETA = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Dieta dieta = new Dieta(
                            rs.getLong("ID_DIETA"),
                            rs.getString("NM_DIETA"),
                            rs.getString("DS_DIETA")
                    );
                    return Optional.of(dieta);
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
    public Optional<Dieta> insertRepository(Dieta dieta) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_DIETA (ID_DIETA, NM_DIETA, DS_DIETA, DT_CADASTRO, NM_USUARIO) VALUES (SQ_VB_DIETA.nextval, ?, ?, SYSDATE, USER)";
        String querySelect = "SELECT * FROM T_VB_DIETA ORDER BY ID_DIETA DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setString(1, dieta.getNome());
            statementInsert.setString(2, dieta.getDescricao());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    Dieta novaDieta = new Dieta(
                            rs.getLong("ID_DIETA"),
                            rs.getString("NM_DIETA"),
                            rs.getString("DS_DIETA")
                    );
                    return Optional.of(novaDieta);
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
    public void updateReposiory(Dieta dieta) throws SQLException {
        String query = "UPDATE T_VB_DIETA SET NM_DIETA = ?, DS_DIETA = ? WHERE ID_DIETA = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, dieta.getNome());
            ps.setString(2, dieta.getDescricao());
            ps.setLong(3, dieta.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_DIETA WHERE ID_DIETA = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
