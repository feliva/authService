package producers;


import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;


@RequestScoped
public class ProducerConnect implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "authUnit")
	@Produces
	@Default
	private static EntityManager em;
	
	@Resource(mappedName = "java:jboss/datasources/authDS") // same JNDI used by Hibernate Persistence Unit
	private static DataSource dss;
	

	public void close(@Disposes Connection com) {
		try {
			if(!com.isClosed()) {
				com.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Produces
	public static Connection getConnection() throws SQLException {
		if(dss == null) {   
	        try {
	            Context ctx = new InitialContext();
	            dss = (DataSource) ctx.lookup("java:jboss/datasources/authDS");
	        }catch (NamingException e) {
	            e.printStackTrace();
	        }
	    }
		
		
		return dss.getConnection();
	}
}