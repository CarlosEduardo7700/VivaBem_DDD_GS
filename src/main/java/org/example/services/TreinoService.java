package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.Treino;
import org.example.models.repositories.TreinoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TreinoService implements IService<Treino> {

    private final TreinoRepository repository = new TreinoRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<Treino> treinos = repository.findAllRepository();

        if (treinos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum treino foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(treinos).build();
    }

    @Override
    public Response findByIdService(Long id) throws SQLException {
        Treino treino = repository.findByIdRepository(id).orElse(null);

        if (treino == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O treino não foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(treino).build();
    }

    @Override
    public Response insertService(Treino treino) throws SQLException {
        if (treino == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            Treino novoTreino = repository.insertRepository(treino).orElse(null);

            return Response.status(Response.Status.CREATED).entity(novoTreino).build();
        }
    }

    @Override
    public Response updateService(Long id, Treino treino) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            treino.setId(id);
            repository.updateReposiory(treino);
            Optional<Treino> treinoAtualizado = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(treinoAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Treino não encontrado no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Treino não encontrado no banco de dados!").build();
    }
}
