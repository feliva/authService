package br.com.feliva.authClass.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

import br.com.feliva.sharedClass.db.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="pessoas", schema = "auth")
public class Pessoa extends Model<UUID> implements Serializable {
	private static final long serialVersionUID = 22021991L;

	@Id
	@Column(name="pessoa_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	protected UUID pessoaId;

	@Column(name="nome")
	@NotNull(message = "Informe o nome.")
	protected String nome;

	@Column(name = "dt_nasc")
	@NotNull(message = "Informe a data de nascimento.")
	protected LocalDate dtnasc;

	@NotEmpty(message = "Informe o cpf")
	protected String cpf;

	/**
	 * nao ativar o cascade no authUser, se for necessario, inverta o dono da relação
	 * */
	@JoinColumn(name = "auth_user_id")
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
	@NotNull(message = "Informe o authUser.")
	protected AuthUser authUser;

	protected String tenantId;

	@Transient
	protected boolean novo;

	public Pessoa() {
	}

	public UUID getUsuarioId() {
		return this.pessoaId;
	}

	@Override
	public UUID getMMId() {
		return this.pessoaId;
	}

	public String labelIniciais() {
		String[] nome = this.nome.split(" ");
		StringBuilder iniciais = new StringBuilder();
		for (String string : nome) {
			iniciais.append(string.toUpperCase().charAt(0) + ".");
		}
		return iniciais.toString();
	}

    public void setUsuarioId(UUID usuarioId) {
		this.pessoaId = usuarioId;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		StringBuilder s = new StringBuilder();
		for (String string : nome.split(" ")) {
			s.append(" " + (string.length() > 2 ? string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase() : string));
		}
		// esse trim remove o espaço colocado no for
		this.nome = s.toString().trim();;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if(cpf != null) {
			this.cpf = cpf.replaceAll("[^0-9]", "");
		} else {
			this.cpf = cpf;
		}
	}

	public LocalDate getDtnasc() {
		return dtnasc;
	}

	public void setDtnasc(LocalDate dtnasc) {
		this.dtnasc = dtnasc;
	}

	public void setEventLimit(boolean eventLimit) {
    }
		public boolean isNovo() {
		return this.novo;
	}

	@Transient
	@XmlTransient
	public String getNomeCpf() {
		return this.cpf + " - " + this.nome;
	}

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public static Pessoa createNew(){
		AuthUser au = new AuthUser();
		au.setInativo(false);
		Pessoa p = new Pessoa();
		p.setAuthUser(au);
		return p;
	}
}