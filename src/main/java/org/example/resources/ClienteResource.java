package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.models.Cliente;
import org.example.services.ClienteService;

import java.sql.SQLException;

@Path("/cliente")
public class ClienteResource {

    private final ClienteService service = new ClienteService();

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
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Cliente cliente) throws SQLException {
        return service.LoginService(cliente);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Cliente cliente) throws SQLException {
        return service.insertService(cliente);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Cliente cliente) throws SQLException {
        return service.updateService(id, cliente);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) throws SQLException {
        return service.deleteService(id);
    }
}
