package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.Cliente;
import org.example.models.repositories.ClienteRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClienteService implements IService<Cliente>{

    private final ClienteRepository repository = new ClienteRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<Cliente> clientes = repository.findAllRepository();

        if (clientes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum cliente foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(clientes).build();
    }

    @Override
    public Response findByIdService(Long id) throws SQLException {
        Cliente cliente = repository.findByIdRepository(id).orElse(null);

        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O cliente não foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(cliente).build();
    }

    public Response LoginService(Cliente credenciais) throws SQLException {
        Cliente cliente = repository.findByEmailRepository(credenciais.getEmail()).orElse(null);

        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Email não encontrado! Tente novamente.").build();
        } else {
            if (credenciais.getSenha().equals(cliente.getSenha())) {
                return Response.status(Response.Status.ACCEPTED).entity(cliente).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Senha incorreta! Tente novamente.").build();
            }
        }
    }

    @Override
    public Response insertService(Cliente cliente) throws SQLException {
        if (cliente == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            Cliente clienteCriado = repository.insertRepository(cliente).orElse(null);

            return Response.status(Response.Status.CREATED).entity(clienteCriado).build();
        }
    }

    @Override
    public Response updateService(Long id, Cliente cliente) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            cliente.setId(id);
            repository.updateReposiory(cliente);
            Optional<Cliente> usuarioAtualizado = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(usuarioAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado no banco de dados!").build();
    }
}
