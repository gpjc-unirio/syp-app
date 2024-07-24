package br.unirio.jdbpn.narrativas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RelacaoSentencas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRelacao;
	@ManyToOne
	private Sentenca sentencaPai;
	@ManyToOne
	private Sentenca sentencaFilha;
	private String nomeOpcao;

	public RelacaoSentencas() {

	}

	public RelacaoSentencas(Sentenca sentencaPai, Sentenca sentencaFilha) {
		this.sentencaPai = sentencaPai;
		this.sentencaFilha = sentencaFilha;
	}

	public Sentenca getSentencaPai() {
		return sentencaPai;
	}

	public void setSentencaPai(Sentenca sentencaPai) {
		this.sentencaPai = sentencaPai;
	}

	public Sentenca getSentencaFilha() {
		return sentencaFilha;
	}

	public void setSentencaFilha(Sentenca sentencaFilha) {
		this.sentencaFilha = sentencaFilha;
	}

	public String getNomeOpcao() {
		return nomeOpcao;
	}

	public void setNomeOpcao(String nomeOpcao) {
		this.nomeOpcao = nomeOpcao;
	}

}
