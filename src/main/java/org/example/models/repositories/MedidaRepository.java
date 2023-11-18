package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.Biotipo;
import org.example.models.Medida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedidaRepository implements IRepository<Medida> {

    @Override
    public List<Medida> findAllRepository() throws SQLException {
        List<Medida> medidas = new ArrayList<>();
        String query = "SELECT * FROM T_VB_MEDIDA ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Medida medida = new Medida(
                        rs.getLong("ID_MEDIDA"),
                        rs.getDouble("CINTURA_MEDIDA"),
                        rs.getDouble("TORAX_MEDIDA"),
                        rs.getDouble("BRACO_DIREITO_MEDIDA"),
                        rs.getDouble("BRACO_ESQUERDO_MEDIDA"),
                        rs.getDouble("COXA_DIREITO_MEDIDA"),
                        rs.getDouble("COXA_ESQUERDO_MEDIDA"),
                        rs.getDouble("PANTURRILHA_DIREITO_MEDIDA"),
                        rs.getDouble("PANTURRILHA_ESQUERDO_MEDIDA")

                );
                medidas.add(medida);
            }
            return medidas;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Medida> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_MEDIDA WHERE ID_MEDIDA = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Medida medida = new Medida(
                            rs.getLong("ID_MEDIDA"),
                            rs.getDouble("CINTURA_MEDIDA"),
                            rs.getDouble("TORAX_MEDIDA"),
                            rs.getDouble("BRACO_DIREITO_MEDIDA"),
                            rs.getDouble("BRACO_ESQUERDO_MEDIDA"),
                            rs.getDouble("COXA_DIREITO_MEDIDA"),
                            rs.getDouble("COXA_ESQUERDO_MEDIDA"),
                            rs.getDouble("PANTURRILHA_DIREITO_MEDIDA"),
                            rs.getDouble("PANTURRILHA_ESQUERDO_MEDIDA")

                    );
                    return Optional.of(medida);
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
    public Optional<Medida> insertRepository(Medida medida) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_MEDIDA (ID_MEDIDA, CINTURA_MEDIDA, TORAX_MEDIDA, BRACO_DIREITO_MEDIDA, BRACO_ESQUERDO_MEDIDA, COXA_DIREITO_MEDIDA, COXA_ESQUERDO_MEDIDA, PANTURRILHA_DIREITO_MEDIDA, PANTURRILHA_ESQUERDO_MEDIDA) VALUES (SQ_VB_BIOTIPO.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
        String querySelect = "SELECT * FROM T_VB_MEDIDA ORDER BY ID_MEDIDA DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setDouble(1, medida.getCintura());
            statementInsert.setDouble(2, medida.getTorax());
            statementInsert.setDouble(3, medida.getBracoDireito());
            statementInsert.setDouble(4, medida.getBracoEsquerdo());
            statementInsert.setDouble(5, medida.getCoxaDireita());
            statementInsert.setDouble(6, medida.getCoxaEsquerda());
            statementInsert.setDouble(7, medida.getPanturrilhaDireita());
            statementInsert.setDouble(8, medida.getPanturrilhaEsquerda());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    Medida medidaCriada = new Medida(
                            rs.getLong("ID_MEDIDA"),
                            rs.getDouble("CINTURA_MEDIDA"),
                            rs.getDouble("TORAX_MEDIDA"),
                            rs.getDouble("BRACO_DIREITO_MEDIDA"),
                            rs.getDouble("BRACO_ESQUERDO_MEDIDA"),
                            rs.getDouble("COXA_DIREITO_MEDIDA"),
                            rs.getDouble("COXA_ESQUERDO_MEDIDA"),
                            rs.getDouble("PANTURRILHA_DIREITO_MEDIDA"),
                            rs.getDouble("PANTURRILHA_ESQUERDO_MEDIDA")

                    );
                    return Optional.of(medidaCriada);
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
    public void updateReposiory(Medida medida) throws SQLException {
        String query = "UPDATE T_VB_MEDIDA SET CINTURA_MEDIDA = ?, TORAX_MEDIDA = ?, BRACO_DIREITO_MEDIDA = ?, BRACO_ESQUERDO_MEDIDA = ?, COXA_DIREITO_MEDIDA = ?, COXA_ESQUERDO_MEDIDA = ?, PANTURRILHA_DIREITO_MEDIDA = ?, PANTURRILHA_ESQUERDO_MEDIDA = ? WHERE ID_MEDIDA = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setDouble(1, medida.getCintura());
            ps.setDouble(2, medida.getTorax());
            ps.setDouble(3, medida.getBracoDireito());
            ps.setDouble(4, medida.getBracoEsquerdo());
            ps.setDouble(5, medida.getCoxaDireita());
            ps.setDouble(6, medida.getCoxaEsquerda());
            ps.setDouble(7, medida.getPanturrilhaDireita());
            ps.setDouble(8, medida.getPanturrilhaEsquerda());
            ps.setDouble(9, medida.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_MEDIDA WHERE ID_MEDIDA = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
