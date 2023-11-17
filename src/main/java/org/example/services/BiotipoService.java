package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.Biotipo;
import org.example.models.repositories.BiotipoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BiotipoService implements IService<Biotipo>{

    private final BiotipoRepository repository = new BiotipoRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<Biotipo> biotipos = repository.findAllRepository();

        if (biotipos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum biotipo foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(biotipos).build();
    }


    @Override
    public Response findByIdService(Long id) throws SQLException {
        Biotipo biotipo = repository.findByIdRepository(id).orElse(null);

        if (biotipo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O biotipo não foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(biotipo).build();
    }


    @Override
    public Response insertService(Biotipo biotipo) throws SQLException {

        if (biotipo == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            Biotipo biotipoCriado = repository.insertRepository(biotipo).orElse(null);

            return Response.status(Response.Status.CREATED).entity(biotipoCriado).build();
        }
    }


    @Override
    public Response updateService(Long id, Biotipo biotipo) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            biotipo.setId(id);
            repository.updateReposiory(biotipo);
            Optional<Biotipo> biotipoAtualizado = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(biotipoAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Biotipo não encontrado no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Biotipo não encontrado no banco de dados!").build();
    }
}
