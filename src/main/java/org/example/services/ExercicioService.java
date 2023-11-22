package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.Exercicio;
import org.example.models.TipoTreino;
import org.example.models.Treino;
import org.example.models.repositories.ExercicioRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ExercicioService implements IService<Exercicio>{

    private final ExercicioRepository repository = new ExercicioRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<Exercicio> exercicios = repository.findAllRepository();

        if (exercicios.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum exercício foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(exercicios).build();
    }

    @Override
    public Response findByIdService(Long id) throws SQLException {
        Exercicio exercicio = repository.findByIdRepository(id).orElse(null);

        if (exercicio == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O exercício não foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(exercicio).build();
    }

    public Response findByTipoTreinoService(Long idTipoTreino) throws SQLException {
        List<Exercicio> exercicios = repository.findByTipoTreinoRepository(idTipoTreino);

        if (exercicios.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum exercício foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(exercicios).build();
    }

    @Override
    public Response insertService(Exercicio exercicio) throws SQLException {
        if (exercicio == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            Exercicio novoExercicio = repository.insertRepository(exercicio).orElse(null);

            return Response.status(Response.Status.CREATED).entity(novoExercicio).build();
        }
    }

    @Override
    public Response updateService(Long id, Exercicio exercicio) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            exercicio.setId(id);
            repository.updateReposiory(exercicio);
            Optional<Exercicio> exercicioAtualizado = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(exercicioAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Exercício não encontrado no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Exercício não encontrado no banco de dados!").build();
    }
}
