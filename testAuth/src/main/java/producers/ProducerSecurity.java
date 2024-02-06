package producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.wildfly.security.http.oidc.OidcPrincipal;
import org.wildfly.security.http.oidc.OidcSecurityContext;

import java.io.Serializable;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@ApplicationScoped
public class ProducerSecurity implements Serializable {

    @Inject
    private HttpServletRequest request;

//    @Inject
//    private HttpServletResponse response;


//    @Inject
//    private jakarta.security.enterprise.SecurityContext securityContext;


    @Produces
    public OidcSecurityContext getSecurityContext() {
        OidcPrincipal<?> oidcPrincipal = (OidcPrincipal<?>) request.getUserPrincipal();
        if(oidcPrincipal == null) {
            try {
                //redireciona para pagina inicial e força o login novamente
                FacesContext fc = FacesContext.getCurrentInstance();
                String url = fc.getExternalContext().getRequestContextPath();
                fc.getExternalContext().redirect(url);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return new OidcSecurityContext();
        }
        return oidcPrincipal.getOidcSecurityContext();
    }
}
