package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.models.Usuario;
import org.example.services.UsuarioService;

import java.sql.SQLException;

@Path("/usuario")
public class UsuarioResource {

    private final UsuarioService service = new UsuarioService();

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
    public Response login(Usuario usuario) throws SQLException {
        return service.LoginService(usuario);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Usuario usuario) throws SQLException {
        return service.insertService(usuario);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Usuario usuario) throws SQLException {
        return service.updateService(id, usuario);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) throws SQLException {
        return service.deleteService(id);
    }
}
