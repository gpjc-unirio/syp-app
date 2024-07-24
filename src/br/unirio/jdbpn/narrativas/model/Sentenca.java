package br.unirio.jdbpn.narrativas.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Sentenca implements Comparable<Sentenca> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Projeto projeto;
	private int numero;
	private String sujeito;
	private String verbo;
	private String complementos;
	private String elementoBpmnId;
	private String tipoDeElementoBPMN;
	private String opcoesDoGateway;
	@OneToMany(mappedBy = "sentencaPai", fetch = FetchType.EAGER)
	private List<RelacaoSentencas> sentencasFilhas;
	private boolean temLoop;
	private boolean isSujeitoUmaFuncao;
	@OneToOne(mappedBy = "sentenca")
	private Cena cena;
	private boolean naoVaiTerCena;
	private boolean terminoDeParalelismo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getSujeito() {
		return sujeito;
	}

	public void setSujeito(String sujeito) {
		this.sujeito = sujeito;
	}

	public String getVerbo() {
		return verbo;
	}

	public void setVerbo(String verbo) {
		this.verbo = verbo;
	}

	public String getComplementos() {
		return complementos;
	}

	public void setComplementos(String complementos) {
		this.complementos = complementos;
	}

	public String getElementoBpmnId() {
		return elementoBpmnId;
	}

	public void setElementoBpmnId(String elementoBpmnId) {
		this.elementoBpmnId = elementoBpmnId;
	}

	public String getTipoDeElementoBPMN() {
		return tipoDeElementoBPMN;
	}

	public void setTipoDeElementoBPMN(String tipoDeElementoBPMN) {
		this.tipoDeElementoBPMN = tipoDeElementoBPMN;
	}

	public String getOpcoesDoGateway() {
		return opcoesDoGateway;
	}

	public void setOpcoesDoGateway(String opcoesDoGateway) {
		this.opcoesDoGateway = opcoesDoGateway;
	}

	public List<RelacaoSentencas> getSentencasFilhas() {
		return sentencasFilhas;
	}

	public void setSentencasFilhas(List<RelacaoSentencas> sentencasFilhas) {
		this.sentencasFilhas = sentencasFilhas;
	}

	public boolean isTemLoop() {
		return temLoop;
	}

	public void setTemLoop(boolean temLoop) {
		this.temLoop = temLoop;
	}

	public boolean isSujeitoUmaFuncao() {
		return isSujeitoUmaFuncao;
	}

	public void setSujeitoUmaFuncao(boolean isSujeitoUmaFuncao) {
		this.isSujeitoUmaFuncao = isSujeitoUmaFuncao;
	}

	public Cena getCena() {
		return cena;
	}

	public void setCena(Cena cena) {
		this.cena = cena;
	}

	public boolean isNaoVaiTerCena() {
		return naoVaiTerCena;
	}

	public void setNaoVaiTerCena(boolean naoVaiTerCena) {
		this.naoVaiTerCena = naoVaiTerCena;
	}

	public boolean isTerminoDeParalelismo() {
		return terminoDeParalelismo;
	}

	public void setTerminoDeParalelismo(boolean terminoDeParalelismo) {
		this.terminoDeParalelismo = terminoDeParalelismo;
	}

	@Override
	public int compareTo(Sentenca outraSentenca) {
		return this.numero - outraSentenca.getNumero();
	}

	public String getSentencaCompleta() {

		String sentencaCompleta = "";

		sentencaCompleta = sujeito + " " + verbo;

		if (complementos != null && complementos.length() > 0) {
			sentencaCompleta = sentencaCompleta + " " + complementos;
		}

		sentencaCompleta = sentencaCompleta + ".";

		return sentencaCompleta;

	}

	public List<String> splitOpcoesDoGateway() {
		List<String> splitText = Arrays.asList(opcoesDoGateway.split(";"));
		return splitText;
	}

	public String getTextoListaDasProximasSentencas() {
		String textoProximasSentencas = "";
		int qtdDeSentencas = 0;
		if (sentencasFilhas != null) {
			for (RelacaoSentencas sentenca : sentencasFilhas) {
				qtdDeSentencas++;
				if (qtdDeSentencas <= 1) {
					textoProximasSentencas = String.valueOf(sentenca.getSentencaFilha().getNumero());
				} else {
					textoProximasSentencas = textoProximasSentencas + ";"
							+ String.valueOf(sentenca.getSentencaFilha().getNumero());
				}
			}
		}
		return textoProximasSentencas;
	}

}
