package org.example.models.repositories;

import org.example.infrastructure.database.DataBaseFactory;
import org.example.models.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository implements  IRepository<Cliente>{

    private final MedidaRepository medidaRepository = new MedidaRepository();
    private final ObjetivoRepository objetivoRepository = new ObjetivoRepository();
    private final BiotipoRepository biotipoRepository = new BiotipoRepository();
    private final TreinoRepository treinoRepository = new TreinoRepository();
    private final DietaRepository dietaRepository = new DietaRepository();


    @Override
    public List<Cliente> findAllRepository() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM T_VB_CLIENTE ORDER BY 1 ASC";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Cliente cliente = new Cliente(
                        rs.getLong("ID_CLIENTE"),
                        medidaRepository.findByIdRepository(rs.getLong("ID_MEDIDA")).orElse(null),
                        objetivoRepository.findByIdRepository(rs.getLong("ID_OBJETIVO")).orElse(null),
                        biotipoRepository.findByIdRepository(rs.getLong("ID_BIOTIPO")).orElse(null),
                        dietaRepository.findByIdRepository(rs.getLong("ID_DIETA")).orElse(null),
                        treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                        rs.getString("EMAIL_CLIENTE"),
                        rs.getString("SENHA_CLIENTE"),
                        rs.getString("NM_CLIENTE"),
                        rs.getString("GENERO_CLIENTE"),
                        rs.getInt("IDADE_CLIENTE"),
                        rs.getDouble("METABOLISMO_CLIENTE")
                );
                clientes.add(cliente);
            }
            return clientes;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Cliente> findByIdRepository(Long id) throws SQLException {
        String query = "SELECT * FROM T_VB_CLIENTE WHERE ID_CLIENTE = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getLong("ID_CLIENTE"),
                            medidaRepository.findByIdRepository(rs.getLong("ID_MEDIDA")).orElse(null),
                            objetivoRepository.findByIdRepository(rs.getLong("ID_OBJETIVO")).orElse(null),
                            biotipoRepository.findByIdRepository(rs.getLong("ID_BIOTIPO")).orElse(null),
                            dietaRepository.findByIdRepository(rs.getLong("ID_DIETA")).orElse(null),
                            treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                            rs.getString("EMAIL_CLIENTE"),
                            rs.getString("SENHA_CLIENTE"),
                            rs.getString("NM_CLIENTE"),
                            rs.getString("GENERO_CLIENTE"),
                            rs.getInt("IDADE_CLIENTE"),
                            rs.getDouble("METABOLISMO_CLIENTE")
                    );
                    return Optional.of(cliente);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return Optional.empty();
    }

    public Optional<Cliente> findByEmailRepository(String email) throws SQLException {
        String query = "SELECT * FROM T_VB_CLIENTE WHERE EMAIL_CLIENTE = ?";

        try(Connection connection = DataBaseFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getLong("ID_CLIENTE"),
                            medidaRepository.findByIdRepository(rs.getLong("ID_MEDIDA")).orElse(null),
                            objetivoRepository.findByIdRepository(rs.getLong("ID_OBJETIVO")).orElse(null),
                            biotipoRepository.findByIdRepository(rs.getLong("ID_BIOTIPO")).orElse(null),
                            dietaRepository.findByIdRepository(rs.getLong("ID_DIETA")).orElse(null),
                            treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                            rs.getString("EMAIL_CLIENTE"),
                            rs.getString("SENHA_CLIENTE"),
                            rs.getString("NM_CLIENTE"),
                            rs.getString("GENERO_CLIENTE"),
                            rs.getInt("IDADE_CLIENTE"),
                            rs.getDouble("METABOLISMO_CLIENTE")
                    );
                    return Optional.of(cliente);
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
    public Optional<Cliente> insertRepository(Cliente cliente) throws SQLException {
        String queryInsert = "INSERT INTO T_VB_CLIENTE (ID_CLIENTE, ID_MEDIDA, ID_OBJETIVO, ID_BIOTIPO, ID_DIETA, ID_TREINO, EMAIL_CLIENTE, SENHA_CLIENTE, NM_CLIENTE, GENERO_CLIENTE, IDADE_CLIENTE, METABOLISMO_CLIENTE, DT_CADASTRO, NM_USUARIO) VALUES (SQ_VB_USUARIO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, USER)";
        String querySelect = "SELECT * FROM T_VB_CLIENTE ORDER BY ID_CLIENTE DESC FETCH FIRST 1 ROW ONLY";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(queryInsert);
             PreparedStatement statementSelect = connection.prepareStatement(querySelect)) {

            statementInsert.setLong(1, cliente.getMedida().getId());
            statementInsert.setLong(2, cliente.getObjetivo().getId());
            statementInsert.setLong(3, cliente.getBiotipo().getId());
            statementInsert.setLong(4, cliente.getDieta().getId());
            statementInsert.setLong(5, cliente.getTreino().getId());
            statementInsert.setString(6, cliente.getEmail());
            statementInsert.setString(7, cliente.getSenha());
            statementInsert.setString(8, cliente.getNome());
            statementInsert.setString(9, cliente.getGenero());
            statementInsert.setInt(10, cliente.getIdade());
            statementInsert.setDouble(11, cliente.getMetabolismo());

            statementInsert.executeUpdate();

            try(ResultSet rs = statementSelect.executeQuery()){
                if(rs.next()) {
                    Cliente novoCliente = new Cliente(
                            rs.getLong("ID_CLIENTE"),
                            medidaRepository.findByIdRepository(rs.getLong("ID_MEDIDA")).orElse(null),
                            objetivoRepository.findByIdRepository(rs.getLong("ID_OBJETIVO")).orElse(null),
                            biotipoRepository.findByIdRepository(rs.getLong("ID_BIOTIPO")).orElse(null),
                            dietaRepository.findByIdRepository(rs.getLong("ID_DIETA")).orElse(null),
                            treinoRepository.findByIdRepository(rs.getLong("ID_TREINO")).orElse(null),
                            rs.getString("EMAIL_CLIENTE"),
                            rs.getString("SENHA_CLIENTE"),
                            rs.getString("NM_CLIENTE"),
                            rs.getString("GENERO_CLIENTE"),
                            rs.getInt("IDADE_CLIENTE"),
                            rs.getDouble("METABOLISMO_CLIENTE")
                    );
                    return Optional.of(novoCliente);
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
    public void updateReposiory(Cliente cliente) throws SQLException {
        String query = "UPDATE T_VB_CLIENTE SET ID_MEDIDA = ?, ID_OBJETIVO = ?, ID_BIOTIPO = ?, ID_DIETA = ?, ID_TREINO = ?, EMAIL_CLIENTE = ?, SENHA_CLIENTE = ?, NM_CLIENTE = ?, GENERO_CLIENTE = ?, IDADE_CLIENTE = ?, METABOLISMO_CLIENTE = ? WHERE ID_CLIENTE = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, cliente.getMedida().getId());
            ps.setLong(2, cliente.getObjetivo().getId());
            ps.setLong(3, cliente.getBiotipo().getId());
            ps.setLong(4, cliente.getDieta().getId());
            ps.setLong(5, cliente.getTreino().getId());
            ps.setString(6, cliente.getEmail());
            ps.setString(7, cliente.getSenha());
            ps.setString(8, cliente.getNome());
            ps.setString(9, cliente.getGenero());
            ps.setInt(10, cliente.getIdade());
            ps.setDouble(11, cliente.getMetabolismo());
            ps.setLong(12, cliente.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteRepository(Long id) throws SQLException {
        String query = "DELETE FROM T_VB_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection connection = DataBaseFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
