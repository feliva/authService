package br.com.feliva.authClass.dao;

import br.com.feliva.authClass.models.Pessoa;
import br.com.feliva.sharedClass.db.DAO;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class PessoaDAO extends DAO<Pessoa>{
}
