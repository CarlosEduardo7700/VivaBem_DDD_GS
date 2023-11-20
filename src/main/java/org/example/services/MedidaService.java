package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.Dieta;
import org.example.models.Medida;
import org.example.models.Objetivo;
import org.example.models.repositories.MedidaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MedidaService implements IService<Medida>{

    private final MedidaRepository repository = new MedidaRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<Medida> medidas = repository.findAllRepository();

        if (medidas.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhuma medida foi encontrada no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(medidas).build();
    }

    @Override
    public Response findByIdService(Long id) throws SQLException {
        Medida medida = repository.findByIdRepository(id).orElse(null);

        if (medida == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("A medida não foi encontrada no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(medida).build();
    }

    @Override
    public Response insertService(Medida medida) throws SQLException {
        if (medida == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            Medida novaMedida = repository.insertRepository(medida).orElse(null);

            return Response.status(Response.Status.CREATED).entity(novaMedida).build();
        }
    }

    @Override
    public Response updateService(Long id, Medida medida) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            medida.setId(id);
            repository.updateReposiory(medida);
            Optional<Medida> medidaAtualizada = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(medidaAtualizada).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Medida não encontrada no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Medida não encontrada no banco de dados!").build();
    }
}
