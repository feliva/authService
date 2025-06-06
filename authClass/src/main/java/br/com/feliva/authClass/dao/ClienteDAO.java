package br.com.feliva.authClass.dao;

import br.com.feliva.authClass.models.Cliente;
import br.com.feliva.sharedClass.db.DAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;

@RequestScoped
public class ClienteDAO extends DAO<Cliente> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cliente findClienteByName(String name){
		try {
	        return (Cliente) this.em.createQuery("""
	                    select c from Cliente c where c.nome = :name
	        """).setParameter("name",name)
	        .getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
    }

}
