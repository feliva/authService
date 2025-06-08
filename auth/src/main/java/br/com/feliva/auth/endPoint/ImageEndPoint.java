package br.com.feliva.auth.endPoint;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import br.com.feliva.sharedClass.constantes.InitConstantes2;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.mail.Session;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.Response;

@Path("/files")
@RequestScoped
public class ImageEndPoint implements Serializable{
	
	private static final long serialVersionUID = 22021991L;
	@Resource(mappedName="java:jboss/mail/Default")
    private Session mailSession;
		
	@GET                                                             
    @Path("/img/{img}.png")
	@Produces({"image/png"})
    public Response  getImgb(@PathParam("img") String img) throws IOException {

		java.nio.file.Path path = Paths.get(InitConstantes2.IMAGEM_PATH + File.separator + img + InitConstantes2.IMAGEM_EXTENSAO);

		if(!Files.exists(path)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge((int) TimeUnit.DAYS.toSeconds(7));
		return Response.ok(Files.newInputStream(path),"image/png")
				.cacheControl(cacheControl)
				.build();		
	}
	@GET
	@Path("/static/{img}.png")
	@Produces({"image/png"})
	public Response  getStatic(@PathParam("img") String img) throws IOException {
		//so funciona em war file
		InputStream imgStream = getClass().getClassLoader().getResourceAsStream("imagen/" + img+ ".png");
		if(imgStream == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge((int) TimeUnit.DAYS.toSeconds(7));
		return Response.ok(imgStream,"image/png")
				.cacheControl(cacheControl)
				.build();
	}
}
