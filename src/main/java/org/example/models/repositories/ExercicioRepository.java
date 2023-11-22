package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.Exercicio;
import org.example.models.TipoTreino;
import org.example.models.Treino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExercicioRepository implements IRepository<Exercicio>{

    private final TipoTreinoRepository tipoTreinoRepository = new TipoTreinoRepository();

    @Override
    public List<Exercicio> findAllRepository() throws SQLException {
        List<Exercicio> exercicios = new ArrayList<>();
        String query = "SELECT * FROM T_VB_EXERC ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Exercicio exercicio = new Exercicio(
                        rs.getLong("ID_EXERCICIO"),
                        tipoTreinoRepository.findByIdRepository(rs.getLong("ID_TP_TREINO")).orElse(null),
                        rs.getString("NM_EXERCICIO"),
                        rs.getInt("SERIES_EXERCICIO"),
                        rs.getInt("REPETICOES_EXERCICIO"),
                        rs.getInt("TEMPO_DESCANSO_EXERCICIO")
                );
                exercicios.add(exercicio);
            }
            return exercicios;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Exercicio> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_EXERC WHERE ID_EXERCICIO = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Exercicio exercicio = new Exercicio(
                            rs.getLong("ID_EXERCICIO"),
                            tipoTreinoRepository.findByIdRepository(rs.getLong("ID_TP_TREINO")).orElse(null),
                            rs.getString("NM_EXERCICIO"),
                            rs.getInt("SERIES_EXERCICIO"),
                            rs.getInt("REPETICOES_EXERCICIO"),
                            rs.getInt("TEMPO_DESCANSO_EXERCICIO")
                    );
                    return Optional.of(exercicio);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return Optional.empty();
    }


    public List<Exercicio> findByTipoTreinoRepository(Long idTipoTreino) throws SQLException {
        List<Exercicio> exercicios = new ArrayList<>();
        String query = String.format("SELECT * FROM T_VB_EXERC WHERE ID_TP_TREINO = %s", idTipoTreino);

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Exercicio exercicio = new Exercicio(
                        rs.getLong("ID_EXERCICIO"),
                        tipoTreinoRepository.findByIdRepository(rs.getLong("ID_TP_TREINO")).orElse(null),
                        rs.getString("NM_EXERCICIO"),
                        rs.getInt("SERIES_EXERCICIO"),
                        rs.getInt("REPETICOES_EXERCICIO"),
                        rs.getInt("TEMPO_DESCANSO_EXERCICIO")
                );
                exercicios.add(exercicio);
            }
            return exercicios;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    @Override
    public Optional<Exercicio> insertRepository(Exercicio exercicio) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_EXERC (ID_EXERCICIO, ID_TP_TREINO, NM_EXERCICIO, SERIES_EXERCICIO, REPETICOES_EXERCICIO, TEMPO_DESCANSO_EXERCICIO, DT_CADASTRO, NM_USUARIO) VALUES (SQ_VB_TREINO.nextval, ?, ?, ?, ?, ?, SYSDATE, USER)";
        String querySelect = "SELECT * FROM T_VB_EXERC ORDER BY ID_EXERCICIO DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setLong(1, exercicio.getTipoTreino().getId());
            statementInsert.setString(2, exercicio.getNome());
            statementInsert.setInt(3, exercicio.getSeries());
            statementInsert.setInt(4, exercicio.getRepeticoes());
            statementInsert.setInt(5, exercicio.getTempoDescanso());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    Exercicio novoExercicio = new Exercicio(
                            rs.getLong("ID_EXERCICIO"),
                            tipoTreinoRepository.findByIdRepository(rs.getLong("ID_TP_TREINO")).orElse(null),
                            rs.getString("NM_EXERCICIO"),
                            rs.getInt("SERIES_EXERCICIO"),
                            rs.getInt("REPETICOES_EXERCICIO"),
                            rs.getInt("TEMPO_DESCANSO_EXERCICIO")
                    );
                    return Optional.of(novoExercicio);
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
    public void updateReposiory(Exercicio exercicio) throws SQLException {
        String query = "UPDATE T_VB_EXERC SET ID_TP_TREINO = ?, NM_EXERCICIO = ?, SERIES_EXERCICIO = ?, REPETICOES_EXERCICIO = ?, TEMPO_DESCANSO_EXERCICIO = ? WHERE ID_EXERCICIO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, exercicio.getTipoTreino().getId());
            ps.setString(2, exercicio.getNome());
            ps.setInt(3, exercicio.getSeries());
            ps.setInt(4, exercicio.getRepeticoes());
            ps.setInt(5, exercicio.getTempoDescanso());
            ps.setLong(6, exercicio.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_EXERC WHERE ID_EXERCICIO = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
