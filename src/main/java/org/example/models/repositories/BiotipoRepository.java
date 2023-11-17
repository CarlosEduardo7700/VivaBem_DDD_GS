package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.Biotipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BiotipoRepository implements IRepository<Biotipo> {

    @Override
    public List<Biotipo> findAllRepository() throws SQLException {
        List<Biotipo> biotipos = new ArrayList<>();
        String query = "SELECT * FROM T_VB_BIOTIPO ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Biotipo biotipo = new Biotipo(
                        rs.getLong("ID_BIOTIPO"),
                        rs.getString("NOME_BIOTIPO"),
                        rs.getString("DESC_BIOTIPO")
                );
                biotipos.add(biotipo);
            }
            return biotipos;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    @Override
    public Optional<Biotipo> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_BIOTIPO WHERE ID_BIOTIPO = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Biotipo biotipo = new Biotipo(
                            rs.getLong("ID_BIOTIPO"),
                            rs.getString("NOME_BIOTIPO"),
                            rs.getString("DESC_BIOTIPO")
                    );
                    return Optional.of(biotipo);
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
    public Optional<Biotipo> insertRepository(Biotipo biotipo) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_BIOTIPO (ID_BIOTIPO, NOME_BIOTIPO, DESC_BIOTIPO) VALUES (SQ_VB_BIOTIPO.nextval, ?, ?)";
        String querySelect = "SELECT * FROM T_VB_BIOTIPO ORDER BY ID_BIOTIPO DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setString(1, biotipo.getNome());
            statementInsert.setString(2, biotipo.getDescricao());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    Biotipo biotipoCriado = new Biotipo(
                            rs.getLong("ID_BIOTIPO"),
                            rs.getString("NOME_BIOTIPO"),
                            rs.getString("DESC_BIOTIPO")
                    );
                    return Optional.of(biotipoCriado);
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
    public void updateReposiory(Biotipo biotipo) throws SQLException {
        String query = "UPDATE T_VB_BIOTIPO SET NOME_BIOTIPO = ?, DESC_BIOTIPO = ? WHERE ID_BIOTIPO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, biotipo.getNome());
            ps.setString(2, biotipo.getDescricao());
            ps.setLong(3, biotipo.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_BIOTIPO WHERE ID_BIOTIPO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
