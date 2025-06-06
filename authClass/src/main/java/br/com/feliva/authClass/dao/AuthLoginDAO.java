package br.com.feliva.authClass.dao;

import java.util.UUID;

import br.com.feliva.authClass.models.AuthLogin;
import br.com.feliva.sharedClass.db.DAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;

@RequestScoped
public class AuthLoginDAO extends DAO<AuthLogin> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthLogin findAuthLoginByCode(String code){
        try {
            return (AuthLogin) this.em.createQuery("""
                    select a from AuthLogin a 
                    join fetch a.cliente c
                    join fetch a.authUser u
                    left join fetch u.setPermissao sp 
                    where a.usado = false and a.code = :code
                """).setParameter("code",UUID.fromString(code))
                    .getSingleResult();
        }catch (NoResultException e){}

        return null;
    }

}
