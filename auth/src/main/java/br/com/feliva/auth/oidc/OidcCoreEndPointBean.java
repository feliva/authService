package br.com.feliva.auth.oidc;

import static br.com.feliva.authClass.models.OpenIdConstant.AUTHORIZATION_CODE;
import static br.com.feliva.authClass.models.OpenIdConstant.AUTHORIZATION_HEADER;
import static br.com.feliva.authClass.models.OpenIdConstant.AUTH_END_POINT_LINK;
import static br.com.feliva.authClass.models.OpenIdConstant.CERTS_END_POINT_LINK;
import static br.com.feliva.authClass.models.OpenIdConstant.CLIENT_ID;
import static br.com.feliva.authClass.models.OpenIdConstant.CLIENT_SECRET;
import static br.com.feliva.authClass.models.OpenIdConstant.CODE;
import static br.com.feliva.authClass.models.OpenIdConstant.END_SESSION_ENDPOINT;
import static br.com.feliva.authClass.models.OpenIdConstant.END_SESSION_ENDPOINT_LINK;
import static br.com.feliva.authClass.models.OpenIdConstant.ERROR_PARAM;
import static br.com.feliva.authClass.models.OpenIdConstant.GRANT_TYPE;
import static br.com.feliva.authClass.models.OpenIdConstant.LOGIN_AUTH_END_POINT_LINK;
import static br.com.feliva.authClass.models.OpenIdConstant.NONCE;
import static br.com.feliva.authClass.models.OpenIdConstant.OPENID_SCOPE;
import static br.com.feliva.authClass.models.OpenIdConstant.REDIRECT_URI;
import static br.com.feliva.authClass.models.OpenIdConstant.REFRESH_TOKEN;
import static br.com.feliva.authClass.models.OpenIdConstant.RESPONSE_TYPE;
import static br.com.feliva.authClass.models.OpenIdConstant.SCOPE;
import static br.com.feliva.authClass.models.OpenIdConstant.STATE;
import static br.com.feliva.authClass.models.OpenIdConstant.TOKEN_END_POINT_LINK;
import static br.com.feliva.authClass.models.OpenIdConstant.USERINFO_ENDPOINT;
import static br.com.feliva.authClass.models.OpenIdConstant.WELL_KNOW_END_POINT_LINK;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_HTML;
import static java.util.UUID.randomUUID;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.com.feliva.auth.util.ThymeleafUtil;
import br.com.feliva.authClass.dao.AuthLoginDAO;
import br.com.feliva.authClass.dao.AuthUserDAO;
import br.com.feliva.authClass.dao.ClienteDAO;
import br.com.feliva.authClass.dao.AuthLoginDAO;
import br.com.feliva.authClass.dao.AuthUserDAO;
import br.com.feliva.authClass.dao.ClienteDAO;
import br.com.feliva.authClass.models.AuthLogin;
import br.com.feliva.authClass.models.AuthUser;
import br.com.feliva.authClass.models.Cliente;
import br.com.feliva.authClass.models.ResponseTypeEnum;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.transaction.RollbackException;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth/realms/{realm}")
@RequestScoped
public class OidcCoreEndPointBean {

//    @Inject Template formLogin;
//    @Inject Template erroAuth;
    @Inject
    ClienteDAO clienteDAO;
    @Inject
    AuthUserDAO authUserDAO;
    @Inject
    AuthLoginDAO authLoginDAO;
    @Inject
    OidcKeysUtil oidcKeysUtil;
    @Inject
    OidcTokenUtil oidcTokenUtil;
    
    @Inject
    ThymeleafUtil thymeleafUtil;


    
    @GET
    @Path(WELL_KNOW_END_POINT_LINK)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response getConfiguration2(@PathParam("realm") String name) {
        return Response.ok(OidcKeysUtil.WELL_KNOW).build();
    }

    @GET
    @Path(CERTS_END_POINT_LINK)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response getJwkFile() {
        return Response.ok(oidcKeysUtil.getPairKey().toJSONObject()).build();
    }

    @GET
    @Path(USERINFO_ENDPOINT)
    @RolesAllowed({"user"})
    @Produces(APPLICATION_JSON)
    public Response userInfoEndpoint(){
        System.out.println("user end point");
        return Response.ok("{\"user\":TO-DO}").build();
    }

    @GET
    @Path(AUTH_END_POINT_LINK)
    @PermitAll
    public Response authEndpoint(
            @QueryParam(CLIENT_ID) String clientId, @QueryParam(SCOPE) String scope,
            @QueryParam(RESPONSE_TYPE) String responseType, @QueryParam(NONCE) String nonce,
            @QueryParam(STATE) String state, @QueryParam(REDIRECT_URI) String redirectUri) throws URISyntaxException {

//        if (!CODE.equals(responseType) || !TOKEN.equals(responseType)) {
//            return Response.ok(thymeleafUtil.processes("erroAuth"), TEXT_HTML).build();
//        }

        if (!OPENID_SCOPE.equals(scope)) {
        	return Response.ok(thymeleafUtil.processes("erroAuth"), TEXT_HTML).build();
        }

        if (state == null) {
        	return Response.ok(thymeleafUtil.processes("erroAuth"), TEXT_HTML).build();
        }

        Response retorno = retornaLoginPage(clientId,scope,state,redirectUri, new HashMap<String,Object>());

        return retorno;
    }

    public Response retornaLoginPage(String clientId,String scope,String state,String redirectUri,Map<String,Object> variable){
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setNoStore(true);
        variable.put(CLIENT_ID, clientId);
        variable.put(SCOPE, scope);
        variable.put(STATE, state);
        variable.put(REDIRECT_URI, redirectUri);


        return Response.ok(thymeleafUtil.processes("formLogin",variable), TEXT_HTML)
                .cacheControl(cc)
                .build();
    }

    public Response createJsonError(String tipo) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add(ERROR_PARAM, tipo);
        return Response.serverError().entity(jsonBuilder.build()).build();
    }

    @GET
    @Path(LOGIN_AUTH_END_POINT_LINK)
    @PermitAll
    public Response loginauth() {
        return Response.ok("<p> nada para mostrar</p>", TEXT_HTML).build();
    }

    @POST
    @Path(LOGIN_AUTH_END_POINT_LINK)
    @PermitAll
    public Response loginAuth(@FormParam(CLIENT_ID) String clientId, @FormParam(SCOPE) String scope,
                              @FormParam(RESPONSE_TYPE) String responseType, @FormParam(NONCE) String nonce,
                              @FormParam(STATE) String state, @FormParam(REDIRECT_URI) String redirectUri,
                              @FormParam("username") String username, @FormParam("password") String password
                            ) throws URISyntaxException {

        Cliente client = this.clienteDAO.findClienteByName(clientId);

        if (client == null) {
            return this.createJsonError("invalid_client_id");
        }
        AuthUser user = this.authUserDAO.findByUsername(username);
        if (user == null || !user.authenticate(password)) {
            Map<String,Object> variables = new HashMap<>();
            variables.put("msgError", "Usuário ou senha inválidos.");
            return retornaLoginPage(clientId,scope,state,redirectUri,variables);
        }

        if (user.isInativo()) {
            Map<String,Object> variables = new HashMap<>();
            variables.put("msgError", "Usuário Inativo.");
            return retornaLoginPage(clientId,scope,state,redirectUri,variables);
        }

        AuthLogin authLogin = new AuthLogin(UUID.fromString(state), randomUUID(), user, client);

        try {
			this.authLoginDAO.persistT(authLogin);
		} catch (RollbackException e) {
			e.printStackTrace();
			return Response.ok(thymeleafUtil.processes("erroAuth"), TEXT_HTML).build();
		}
        Response.ResponseBuilder rb;
        if (client.getResponseType().equals(ResponseTypeEnum.CODE)){
            rb = this.oidcTokenUtil.createUrlRedirectCodeResponse(authLogin,redirectUri);
        }else{
            rb = this.oidcTokenUtil.createTokenResponse(authLogin,redirectUri);
        }

        return rb.build();
    }

    /**
     * Como não mantenho status dos tokens, não é necessario faze nada
     *
     * @param authorizationHeader
     * @param refresh
     * @return
     */
    @POST
    @Path(END_SESSION_ENDPOINT_LINK)
    public Response logoutEntPoint(@HeaderParam(AUTHORIZATION_HEADER) String authorizationHeader,
                                   @FormParam(REFRESH_TOKEN) String refresh){
        System.out.println(END_SESSION_ENDPOINT);
        //esse endpoint deve retornar um 204 nocontent
        return Response.noContent().build();
    }

    /**
     * https://connect2id.com/products/server/docs/api/token
     *
     * @param authorizationHeader
     * @param clientId
     * @param clientSecret
     * @param grantType
     * @param code
     * @param state
     * @param redirectUri
     * @return
     * @throws URISyntaxException
     */
    @POST
    @Path(TOKEN_END_POINT_LINK)
    @Produces(APPLICATION_JSON)
    public Response tokenEndpointSigneddsadsad(
            @HeaderParam(AUTHORIZATION_HEADER) String authorizationHeader,
            @FormParam(CLIENT_ID) String clientId, @FormParam(CLIENT_SECRET) String clientSecret,
            @FormParam(GRANT_TYPE) String grantType, @FormParam(CODE) String code,
            @FormParam(STATE) String state,
            @FormParam(REDIRECT_URI) String redirectUri) throws URISyntaxException {

        if (!AUTHORIZATION_CODE.equals(grantType)) {
            this.createJsonError("invalid_grant_type");
        }

        AuthLogin authLogin = this.authLoginDAO.findAuthLoginByCode(code);

        if (authLogin == null) {
            return createJsonError("invalid_auth_code");
        } else{
            authLogin.setUsado(true);
            if (!authLogin.aindaValido()) {
                return createJsonError("invalid_auth_code");
            }
        }

        if (authorizationHeader != null) {
            if (!authLogin.verifyHeaderAuthorization(authorizationHeader)) {
                return this.createJsonError("invalid_client_id");
            }
        } else {
            if (!authLogin.verifyHeaderAuthorization(clientId, clientSecret)) {
                return this.createJsonError("invalid_client_secret");
            }
        }

        try {
			this.authLoginDAO.updateT(authLogin);
		} catch (RollbackException e) {
			e.printStackTrace();
			return Response.ok(thymeleafUtil.processes("erroAuth"), TEXT_HTML).build();
		}

//        NewCookie stateCookie = new NewCookie("OAuth_Token_Request_State",state);

        return this.oidcTokenUtil.createResponse(authLogin,redirectUri)//.cookie(stateCookie)
                .build();
    }
}