package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.TipoTreino;
import org.example.models.repositories.TipoTreinoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TipoTreinoService implements IService<TipoTreino>{

    private final TipoTreinoRepository repository = new TipoTreinoRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<TipoTreino> tiposTreinos = repository.findAllRepository();

        if (tiposTreinos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum tipo de treino foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(tiposTreinos).build();
    }

    @Override
    public Response findByIdService(Long id) throws SQLException {
        TipoTreino tipoTreino = repository.findByIdRepository(id).orElse(null);

        if (tipoTreino == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O tipo de treino não foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(tipoTreino).build();
    }

    @Override
    public Response insertService(TipoTreino tipoTreino) throws SQLException {
        if (tipoTreino == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            TipoTreino tipoTreinoCriado = repository.insertRepository(tipoTreino).orElse(null);

            return Response.status(Response.Status.CREATED).entity(tipoTreinoCriado).build();
        }
    }

    @Override
    public Response updateService(Long id, TipoTreino tipoTreino) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            tipoTreino.setId(id);
            repository.updateReposiory(tipoTreino);
            Optional<TipoTreino> tipoTreinoAtualizado = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(tipoTreinoAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Tipo de treino não encontrado no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Tipo de treino não encontrado no banco de dados!").build();
    }
}
