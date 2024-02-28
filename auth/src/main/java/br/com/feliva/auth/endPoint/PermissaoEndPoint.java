package br.com.feliva.auth.endPoint;

import br.com.feliva.authClass.dao.PermissaoDAO;
import br.com.feliva.authClass.models.Permissao;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/permissao")
public class PermissaoEndPoint {

    @Inject
    PermissaoDAO permissaoDAO;

    @Path("/listAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll(){
        List<Permissao> l = permissaoDAO.listAll();
        return Response.ok(l).build();
    }

}
