package br.com.feliva.authClass.dao;

import java.util.List;

import br.com.feliva.authClass.models.Permissao;
import br.com.feliva.sharedClass.db.DAO;
import br.com.feliva.sharedClass.db.NoContext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

@RequestScoped
public class PermissaoDAO extends DAO<Permissao> implements NoContext<PermissaoDAO> {

    @SuppressWarnings("unchecked")
    public List<Permissao> listAll(){
        try {
            return (List<Permissao>) this.em.createQuery("select p from Permissao p")
                    .getResultList();
        }catch (NoResultException ignored){}

        return null;
    }

    @Override
    public PermissaoDAO noContext(EntityManager em) {
        this.em = em;
        return this;
    }
}
