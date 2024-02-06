package br.com.feliva.authClass.models;

import java.util.UUID;

import br.com.feliva.sharedClass.db.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissao", schema = "auth")
public class Permissao extends Model<UUID> {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "permissao_id")
    private UUID permissaoId;

    private String nome;

    public Permissao(){}

    public UUID getMMId() {
        return this.permissaoId;
    }

    public UUID getPermissaoId() {
        return permissaoId;
    }

    public void setPermissaoId(UUID permissaoId) {
        this.permissaoId = permissaoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
