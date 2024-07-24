package br.unirio.jdbpn.narrativas.model;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

@Entity
public class Projeto implements Comparable<Projeto> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	@ManyToMany
	private List<Usuario> autores = new ArrayList<Usuario>();
	private String status;
	@Column(length = 3096)
	private String descricao;
	@Lob
	private String roteiro;
	private String local;
	private String objetos;
	@Column(length = 3096)
	private String personagensTemp;

	public List<Usuario> getAutores() {
		return autores;
	}

	public void setAutores(List<Usuario> autores) {
		this.autores = autores;
	}

	public void adicionaAutor(Usuario autor) {
		if (!this.autores.contains(autor)) {
			this.autores.add(autor);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getRoteiro() {
		return roteiro;
	}

	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getObjetos() {
		return objetos;
	}

	public void setObjetos(String objetos) {
		this.objetos = objetos;
	}

	public String getPersonagensTemp() {
		return personagensTemp;
	}

	public void setPersonagensTemp(String personagensTemp) {
		this.personagensTemp = personagensTemp;
	}

	@Override
	public int compareTo(Projeto outroProjeto) {
		return this.nome.compareTo(outroProjeto.getNome());
	}

	@Override
	public String toString() {
		return Normalizer.normalize(this.nome, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
