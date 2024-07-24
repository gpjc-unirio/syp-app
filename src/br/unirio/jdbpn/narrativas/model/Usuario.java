package br.unirio.jdbpn.narrativas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.unirio.jdbpn.narrativas.util.PerfilUsuarioEnum;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeUsuario;
	@Column(unique = true)
	private String emailUsuario;
	private String senhaUsuario;
	private String perfilUsuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public String getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public boolean isUsuarioAdministrador() {
		return this.perfilUsuario == null
				|| this.perfilUsuario.equals(PerfilUsuarioEnum.PERFIL_ADMINISTRADOR.getPerfil());
	}

	public boolean isUsuarioSemSuporte() {
		return this.perfilUsuario != null
				&& this.perfilUsuario.equals(PerfilUsuarioEnum.PERFIL_SEM_SUPORTE.getPerfil());
	}

}
