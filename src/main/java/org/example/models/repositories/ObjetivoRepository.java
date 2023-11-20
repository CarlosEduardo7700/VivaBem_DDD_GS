package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.Dieta;
import org.example.models.Objetivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjetivoRepository implements IRepository<Objetivo>{
    @Override
    public List<Objetivo> findAllRepository() throws SQLException {
        List<Objetivo> objetivos = new ArrayList<>();
        String query = "SELECT * FROM T_VB_OBJETIVO ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Objetivo objetivo = new Objetivo(
                        rs.getLong("ID_OBJETIVO"),
                        rs.getString("NM_OBJETIVO"),
                        rs.getString("TEMPO_OBJETIVO"),
                        rs.getDouble("PESO_OBJETIVO")
                );
                objetivos.add(objetivo);
            }
            return objetivos;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Objetivo> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_OBJETIVO WHERE ID_OBJETIVO = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Objetivo objetivo = new Objetivo(
                            rs.getLong("ID_OBJETIVO"),
                            rs.getString("NM_OBJETIVO"),
                            rs.getString("TEMPO_OBJETIVO"),
                            rs.getDouble("PESO_OBJETIVO")
                    );
                    return Optional.of(objetivo);
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
    public Optional<Objetivo> insertRepository(Objetivo objetivo) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_OBJETIVO (ID_OBJETIVO, NM_OBJETIVO, TEMPO_OBJETIVO, PESO_OBJETIVO, DT_CADASTRO, NM_USUARIO) VALUES (SQ_VB_OBJETIVO.nextval, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, SYSDATE, USER)";
        String querySelect = "SELECT * FROM T_VB_OBJETIVO ORDER BY ID_OBJETIVO DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setString(1, objetivo.getNome());
            statementInsert.setString(2, objetivo.getTempo());
            statementInsert.setDouble(3, objetivo.getPeso());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    Objetivo novoObjetivo = new Objetivo(
                            rs.getLong("ID_OBJETIVO"),
                            rs.getString("NM_OBJETIVO"),
                            rs.getString("TEMPO_OBJETIVO"),
                            rs.getDouble("PESO_OBJETIVO")
                    );
                    return Optional.of(novoObjetivo);
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
    public void updateReposiory(Objetivo objetivo) throws SQLException {
        String query = "UPDATE T_VB_OBJETIVO SET NM_OBJETIVO = ?, TEMPO_OBJETIVO = ?, PESO_OBJETIVO = ? WHERE ID_OBJETIVO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, objetivo.getNome());
            ps.setString(2, objetivo.getTempo());
            ps.setDouble(3, objetivo.getPeso());
            ps.setLong(4, objetivo.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_OBJETIVO WHERE ID_OBJETIVO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
