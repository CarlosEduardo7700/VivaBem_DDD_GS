package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.Dieta;
import org.example.models.repositories.DietaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DietaService implements IService<Dieta>{

    private final DietaRepository repository = new DietaRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<Dieta> dietas = repository.findAllRepository();

        if (dietas.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhuma dieta foi encontrada no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(dietas).build();
    }

    @Override
    public Response findByIdService(Long id) throws SQLException {
        Dieta dieta = repository.findByIdRepository(id).orElse(null);

        if (dieta == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("A dieta não foi encontrada no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(dieta).build();
    }

    @Override
    public Response insertService(Dieta dieta) throws SQLException {
        if (dieta == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            Dieta novaDieta = repository.insertRepository(dieta).orElse(null);

            return Response.status(Response.Status.CREATED).entity(novaDieta).build();
        }
    }

    @Override
    public Response updateService(Long id, Dieta dieta) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            dieta.setId(id);
            repository.updateReposiory(dieta);
            Optional<Dieta> dietaAtualizada = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(dietaAtualizada).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Dieta não encontrada no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Dieta não encontrada no banco de dados!").build();
    }
}
