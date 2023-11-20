package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.Objetivo;
import org.example.models.repositories.ObjetivoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ObjetivoService implements IService<Objetivo>{
    private final ObjetivoRepository repository = new ObjetivoRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<Objetivo> objetivos = repository.findAllRepository();

        if (objetivos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum objetivo foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(objetivos).build();
    }

    @Override
    public Response findByIdService(Long id) throws SQLException {
        Objetivo objetivo = repository.findByIdRepository(id).orElse(null);

        if (objetivo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O objetivo não foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(objetivo).build();
    }

    @Override
    public Response insertService(Objetivo objetivo) throws SQLException {
        if (objetivo == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            Objetivo novoObjetivo = repository.insertRepository(objetivo).orElse(null);

            return Response.status(Response.Status.CREATED).entity(novoObjetivo).build();
        }
    }

    @Override
    public Response updateService(Long id, Objetivo objetivo) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            objetivo.setId(id);
            repository.updateReposiory(objetivo);
            Optional<Objetivo> objetivoAtualizado = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(objetivoAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Objetivo não encontrado no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Objetivo não encontrado no banco de dados!").build();
    }
}
