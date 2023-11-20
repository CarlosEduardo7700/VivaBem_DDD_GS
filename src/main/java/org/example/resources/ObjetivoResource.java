package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.models.Objetivo;
import org.example.services.ObjetivoService;

import java.sql.SQLException;

@Path("/objetivo")
public class ObjetivoResource {

    private final ObjetivoService service = new ObjetivoService();

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(Objetivo objetivo) throws SQLException {
        return service.insertService(objetivo);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Objetivo objetivo) throws SQLException {
        return service.updateService(id, objetivo);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) throws SQLException {
        return service.deleteService(id);
    }
}
