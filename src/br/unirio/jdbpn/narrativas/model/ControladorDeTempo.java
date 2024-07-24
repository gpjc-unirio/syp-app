package br.unirio.jdbpn.narrativas.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ControladorDeTempo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String acao;
	private String tipo;
	private Date dataHora;
	@ManyToOne
	private Projeto projeto;
	@ManyToOne
	private Usuario usuario;

	public ControladorDeTempo() {
	}

	public ControladorDeTempo(String acao, String tipo, Date dataHora, Usuario usuario) {
		this.acao = acao;
		this.tipo = tipo;
		this.dataHora = dataHora;
		this.usuario = usuario;
	}

	public ControladorDeTempo(String acao, String tipo, Date dataHora, Usuario usuario, Projeto projeto) {
		this.acao = acao;
		this.tipo = tipo;
		this.dataHora = dataHora;
		this.usuario = usuario;
		this.projeto = projeto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
