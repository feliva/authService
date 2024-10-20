package br.com.feliva.authClass.dao;

import java.util.List;

import br.com.feliva.authClass.models.Permissao;
import br.com.feliva.sharedClass.db.DAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;

@RequestScoped
public class PermissaoDAO extends DAO<Permissao>{

    public List listAll(){
        try {
            return this.em.createQuery("""
                        select p from Permissao p
                    """).getResultList();
        }catch (NoResultException ignored){}

        return null;
    }
}
