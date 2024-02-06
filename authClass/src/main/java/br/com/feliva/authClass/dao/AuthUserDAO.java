package br.com.feliva.authClass.dao;

import br.com.feliva.authClass.models.AuthUser;
import br.com.feliva.authClass.models.Usuario;
import br.com.feliva.sharedClass.db.DAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;

@RequestScoped
public class AuthUserDAO extends DAO<AuthUser>{

    public AuthUser findByUsername(String username){
        try {
            return (AuthUser) this.em.createQuery("""
                    select au from AuthUser au
                    left join fetch au.usuario us 
                    where au.username = :username
                """).setParameter("username",username)
                    .getSingleResult();
        }catch (NoResultException e){}

        return null;
    }

    public AuthUser findAuthUser(Usuario usuario){
        try {
            return (AuthUser) this.em.createQuery("""
                select a from AuthUser a
                left join fetch a.usuario u
                left join fetch a.setPermissao p
                where u = :usuario
            """).setParameter("usuario",usuario)
                        .getSingleResult();
        }catch (NoResultException e){}
            return null;
    }
}
