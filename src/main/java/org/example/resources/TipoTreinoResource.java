package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.models.TipoTreino;
import org.example.services.TipoTreinoService;

import java.sql.SQLException;

@Path("/tipo-treino")
public class TipoTreinoResource {

    private final TipoTreinoService service = new TipoTreinoService();

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
    @Path("/treino/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByTreino(@PathParam("id") Long idTreino) throws SQLException {
        return service.findByTreinoService(idTreino);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(TipoTreino tipoTreino) throws SQLException {
        return service.insertService(tipoTreino);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, TipoTreino tipoTreino) throws SQLException {
        return service.updateService(id, tipoTreino);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) throws SQLException {
        return service.deleteService(id);
    }
}
