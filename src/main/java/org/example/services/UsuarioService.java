package org.example.services;

import jakarta.ws.rs.core.Response;
import org.example.models.Usuario;
import org.example.models.repositories.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioService implements IService<Usuario>{

    private final UsuarioRepository repository = new UsuarioRepository();

    @Override
    public Response findAllService() throws SQLException {
        List<Usuario> usuarios = repository.findAllRepository();

        if (usuarios.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum usuário foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(usuarios).build();
    }

    @Override
    public Response findByIdService(Long id) throws SQLException {
        Usuario usuario = repository.findByIdRepository(id).orElse(null);

        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O usuário não foi encontrado no banco de dados!").build();
        }

        return Response.status(Response.Status.OK).entity(usuario).build();
    }

    public Response LoginService(Usuario credenciais) throws SQLException {
        Usuario usuario = repository.findByEmailRepository(credenciais.getEmail()).orElse(null);

        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Email não encontrado! Tente novamente.").build();
        } else {
            if (credenciais.getSenha().equals(usuario.getSenha())) {
                return Response.status(Response.Status.ACCEPTED).entity(usuario).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Senha incorreta! Tente novamente.").build();
            }
        }
    }

    @Override
    public Response insertService(Usuario usuario) throws SQLException {
        if (usuario == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Requisição inválida! Reveja os dados da sua solicitação.").build();
        } else {
            Usuario usuarioCriado = repository.insertRepository(usuario).orElse(null);

            return Response.status(Response.Status.CREATED).entity(usuarioCriado).build();
        }
    }

    @Override
    public Response updateService(Long id, Usuario usuario) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            usuario.setId(id);
            repository.updateReposiory(usuario);
            Optional<Usuario> usuarioAtualizado = repository.findByIdRepository(id);
            return Response.status(Response.Status.OK).entity(usuarioAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado no banco de dados!").build();
    }

    @Override
    public Response deleteService(Long id) throws SQLException {
        if (repository.findByIdRepository(id).isPresent()) {
            repository.deleteRepository(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado no banco de dados!").build();
    }
}
