package br.unirio.jdbpn.narrativas.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Cena implements Comparable<Cena> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	@OneToOne
	private Sentenca sentenca;
	@ManyToOne
	private Projeto projeto;
	private String estagioDaJornadaDoHeroi;
	@Column(length = 3096)
	private String descricaoBreve;
	private String local;
	private String tipoDeLocal;
	private String tempo;
	@ManyToMany
	private List<Personagem> personagens;
	@Lob
	private String detalhamentoDaCena;
	@Lob	
	private String gatilhos;
	@Lob
	private String resultados;
	@OneToOne
	private Cena cenaAnterior;
	private int ordem;
	private int ato;
	@Transient
	private String observacaoInk;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Sentenca getSentenca() {
		return sentenca;
	}

	public void setSentenca(Sentenca sentenca) {
		this.sentenca = sentenca;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public String getEstagioDaJornadaDoHeroi() {
		return estagioDaJornadaDoHeroi;
	}

	public void setEstagioDaJornadaDoHeroi(String estagioDaJornadaDoHeroi) {
		this.estagioDaJornadaDoHeroi = estagioDaJornadaDoHeroi;
	}

	public String getDescricaoBreve() {
		return descricaoBreve;
	}

	public void setDescricaoBreve(String descricaoBreve) {
		this.descricaoBreve = descricaoBreve;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getTipoDeLocal() {
		return tipoDeLocal;
	}

	public void setTipoDeLocal(String tipoDeLocal) {
		this.tipoDeLocal = tipoDeLocal;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public List<Personagem> getPersonagens() {
		return personagens;
	}

	public void setPersonagens(List<Personagem> personagens) {
		this.personagens = personagens;
	}

	public String getDetalhamentoDaCena() {
		return detalhamentoDaCena;
	}

	public void setDetalhamentoDaCena(String detalhamentoDaCena) {
		this.detalhamentoDaCena = detalhamentoDaCena;
	}

	public String getGatilhos() {
		return gatilhos;
	}

	public void setGatilhos(String gatilhos) {
		this.gatilhos = gatilhos;
	}

	public String getResultados() {
		return resultados;
	}

	public void setResultados(String resultados) {
		this.resultados = resultados;
	}

	public Cena getCenaAnterior() {
		return cenaAnterior;
	}

	public void setCenaAnterior(Cena cenaAnterior) {
		this.cenaAnterior = cenaAnterior;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public int getAto() {
		return ato;
	}

	public void setAto(int ato) {
		this.ato = ato;
	}
	
	public String getObservacaoInk() {
		return observacaoInk;
	}

	public void setObservacaoInk(String observacaoInk) {
		this.observacaoInk = observacaoInk;
	}

	@Override
	public int compareTo(Cena outraCena) {
		return this.ordem - outraCena.getOrdem();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Cena other = (Cena) obj;
		if ((this.titulo == null) ? (other.titulo != null) : !this.titulo.equals(other.titulo)) {
			return false;
		}

		if (this.id != other.id) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
		hash = 53 * hash + this.id;
		return hash;
	}

}
