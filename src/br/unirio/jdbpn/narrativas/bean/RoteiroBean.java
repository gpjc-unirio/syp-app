package br.unirio.jdbpn.narrativas.bean;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.util.ControleDeAcesso;
import br.unirio.jdbpn.narrativas.util.EscopoEnum;
import br.unirio.jdbpn.narrativas.util.GeradorDeRoteiroHtml;
import br.unirio.jdbpn.narrativas.util.InkConverter;
import br.unirio.jdbpn.narrativas.util.StatusProjetoEnum;

@ManagedBean
@ViewScoped
public class RoteiroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Projeto projeto = new Projeto();
	private String roteiro = "";
	private String escopo = "";

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public String getRoteiro() {
		return roteiro;
	}

	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}

	public String getEscopo() {
		return escopo;
	}

	public void setEscopo(String escopo) {
		this.escopo = escopo;
	}

	public void carregarProjetoPeloId() {
		this.projeto = new DAO<Projeto>(Projeto.class).buscaPorId(this.projeto.getId());
		if (this.escopo.length() <= 1) {
			if (isCenasGeradas()) {
				this.escopo = EscopoEnum.Cenas.getDescricao();
			} else {
				this.escopo = EscopoEnum.Sentencas.getDescricao();
			}
		}
	}

	public void carregarEscopo() {
		this.roteiro = GeradorDeRoteiroHtml.gerarRoteiro(projeto, EscopoEnum.getEscopo(escopo));
	}

	public boolean isAutorDoProjeto() {
		return ControleDeAcesso.isAutorDoProjeto(this.projeto);
	}

	public boolean isCenasGeradas() {

		return projeto.getStatus().equals(StatusProjetoEnum.STATUS_ROTEIRO_ELABORADO.getStatus())
				|| projeto.getStatus().equals(StatusProjetoEnum.STATUS_JORNADA_DO_HEROI.getStatus());

	}

	public String gerarTexto() {

		return "visualizarRoteiro?projetoId=" + this.projeto.getId() + "&escopo="
				+ EscopoEnum.getEscopo(escopo).toString() + "&faces-redirect=true";
	}

	public void gerarArquivoInk() throws IOException {

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		try {

			ec.responseReset();
			ec.setResponseContentType("text/plain");
			ec.setResponseCharacterEncoding("UTF-8");
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + projeto.toString() + ".ink" + "\"");

			OutputStream output = ec.getResponseOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(output, StandardCharsets.UTF_8);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			InkConverter.gerarArquivo(projeto, bufferedWriter, EscopoEnum.getEscopo(escopo));

			output.flush();
			output.close();

			fc.responseComplete();

		} catch (IOException e) {

			ec.getFlash().setKeepMessages(true);
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Ocorreu algum erro na geração do arquivo ink. Causa: " + e.getLocalizedMessage()));
			e.printStackTrace();

		}
	}

}
