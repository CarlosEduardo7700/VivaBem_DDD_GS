package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.models.Exercicio;
import org.example.services.ExercicioService;

import java.sql.SQLException;

@Path("/exercicio")
public class ExercicioResource {

    private final ExercicioService service = new ExercicioService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() throws SQLException {
        return service.findAllService();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) throws SQLException {
        return service.findByIdService(id);
    }

    @GET
    @Path("/tipo-treino/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByTipoTreino(@PathParam("id") Long idTipoTreino) throws SQLException {
        return service.findByTipoTreinoService(idTipoTreino);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(Exercicio exercicio) throws SQLException {
        return service.insertService(exercicio);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Exercicio exercicio) throws SQLException {
        return service.updateService(id, exercicio);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) throws SQLException {
        return service.deleteService(id);
    }
}
