package br.unirio.jdbpn.narrativas.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

import br.unirio.jdbpn.narrativas.dao.CenaDao;
import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.dao.PersonagemDao;
import br.unirio.jdbpn.narrativas.dao.SentencaDao;
import br.unirio.jdbpn.narrativas.dao.UsuarioDao;
import br.unirio.jdbpn.narrativas.model.Cena;
import br.unirio.jdbpn.narrativas.model.ControladorDeTempo;
import br.unirio.jdbpn.narrativas.model.Personagem;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.RelacaoSentencas;
import br.unirio.jdbpn.narrativas.model.Sentenca;
import br.unirio.jdbpn.narrativas.model.Usuario;
import br.unirio.jdbpn.narrativas.util.Arquivo;
import br.unirio.jdbpn.narrativas.util.BpmnParaSentencas;
import br.unirio.jdbpn.narrativas.util.ControleDeAcesso;
import br.unirio.jdbpn.narrativas.util.LogInfoEnum;
import br.unirio.jdbpn.narrativas.util.SentencasParaCenas;
import br.unirio.jdbpn.narrativas.util.StatusProjetoEnum;
import br.unirio.jdbpn.narrativas.util.TipoDeElementoEnum;

@ManagedBean
@ViewScoped
public class SentencaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String viewName;
	private Projeto projeto = new Projeto();
	private List<Sentenca> listaDeSentencasDoProjeto = new ArrayList<Sentenca>();
	private List<Cena> listaDeCenasDoProjeto = new ArrayList<Cena>();
	private UploadedFile arquivoBpmn;
	private Sentenca sentenca = new Sentenca();
	private boolean todasAsSentencasJaGeraramCenas;
	private List<Personagem> personagens = new ArrayList<Personagem>();

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	private Arquivo arquivo;

	public Arquivo getArquivo() {
		return new Arquivo(projeto.getId());
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Sentenca getSentenca() {
		return sentenca;
	}

	public void setSentenca(Sentenca sentenca) {
		this.sentenca = sentenca;
	}

	public List<Sentenca> getListaDeSentencasDoProjeto() {
		return listaDeSentencasDoProjeto;
	}

	public void setListaDeSentencasDoProjeto(List<Sentenca> listaDeSentencasDoProjeto) {
		this.listaDeSentencasDoProjeto = listaDeSentencasDoProjeto;
	}

	public List<Cena> getListaDeCenasDoProjeto() {
		return listaDeCenasDoProjeto;
	}

	public void setListaDeCenasDoProjeto(List<Cena> listaDeCenasDoProjeto) {
		this.listaDeCenasDoProjeto = listaDeCenasDoProjeto;
	}

	public UploadedFile getArquivoBpmn() {
		return arquivoBpmn;
	}

	public void setArquivoBpmn(UploadedFile file) {
		this.arquivoBpmn = file;
	}

	public boolean isTodasAsSentencasJaGeraramCenas() {
		return todasAsSentencasJaGeraramCenas;
	}

	public void setTodasAsSentencasJaGeraramCenas(boolean todasAsSentencasJaGeraramCenas) {
		this.todasAsSentencasJaGeraramCenas = todasAsSentencasJaGeraramCenas;
	}

	public List<Personagem> getPersonagens() {
		return personagens;
	}

	public void setPersonagens(List<Personagem> personagens) {
		this.personagens = personagens;
	}

	public boolean isAutorDoProjeto() {
		return ControleDeAcesso.isAutorDoProjeto(this.projeto);
	}

	public void carregarProjetoPeloId() {
		this.projeto = new DAO<Projeto>(Projeto.class).buscaPorId(this.projeto.getId());
		this.listaDeSentencasDoProjeto = new SentencaDao().buscarPorProjeto(this.projeto);

		verificarStatusDasCenas();

		if (todasAsSentencasJaGeraramCenas) {
			this.listaDeCenasDoProjeto = new CenaDao().buscarPorProjeto(this.projeto);
		} else {
			this.listaDeCenasDoProjeto = SentencasParaCenas.converterLista(listaDeSentencasDoProjeto);
			
			this.listaDeCenasDoProjeto.sort(new Comparator<Cena>() {

				@Override
				public int compare(Cena cena1, Cena cena2) {
					if(cena1.equals(cena2)) {
						return 0;
					}
					return Integer.compare(cena1.getAto(), cena2.getAto());
				}
				
			});;
		}

		// Atualização do log / contador de tempo
		if (this.viewName.equals("importarBPMN")) {
			registrarLog(LogInfoEnum.ACAO_IMPORTAR_DIAGRAMA_BPMN, LogInfoEnum.TIPO_ENTRADA);
		}
		if (this.viewName.equals("ajustarSentencas")) {
			registrarLog(LogInfoEnum.ACAO_AJUSTAR_SENTENCAS, LogInfoEnum.TIPO_ENTRADA);
		}
		if (this.viewName.equals("criarCena")) {
			registrarLog(LogInfoEnum.ACAO_CRIAR_CENA, LogInfoEnum.TIPO_ENTRADA);
		}

	}

	public void carregarSentenca(Sentenca sentenca) {
		this.sentenca = sentenca;
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalAjusteDeSentenca').show();");
	}

	public void carregarSentencaPeloElementoBpmn() {

		boolean sentencaLocalizada = false;

		// Identificar a sentença a partir do Id do elemento BPMN
		for (Sentenca sentencaDoProjeto : listaDeSentencasDoProjeto) {
			if (sentencaDoProjeto.getElementoBpmnId().equals(sentenca.getElementoBpmnId())) {
				this.sentenca = sentencaDoProjeto;
				if (sentencaDoProjeto.getCena() != null) {
					this.personagens = new PersonagemDao().buscarPorCena(sentencaDoProjeto.getCena());
				}
				sentencaLocalizada = true;
			}
		}

		if (sentencaLocalizada) {
			PrimeFaces current = PrimeFaces.current();
			current.ajax().update("form");
			current.executeScript("PF('modalDetalhesElementoBpmn').show();");
		}

		this.listaDeSentencasDoProjeto = new SentencaDao().buscarPorProjeto(this.projeto);

	}

	public String uploadBPMN() throws IOException {

		FacesContext context = FacesContext.getCurrentInstance();

		if (arquivoBpmn.getFileName() != null) {

			try {

				arquivo = new Arquivo(projeto.getId());

				List<Sentenca> listaDeSentencasDoBanco = getListaDeSentencasDoProjeto();

				// Se as sentenças já foram cadastradas anteriormente, tem que apagá-las antes
				if (listaDeSentencasDoBanco != null) {
					for (Sentenca sentenca : listaDeSentencasDoBanco) {

						// Tem que remover todas as relações antes
						List<RelacaoSentencas> todasAsRelacoesDaSentenca = new SentencaDao()
								.buscarProximasSentencas(sentenca);

						for (RelacaoSentencas relacaoDaSentenca : todasAsRelacoesDaSentenca) {
							new DAO<RelacaoSentencas>(RelacaoSentencas.class).remove(relacaoDaSentenca);
						}

						sentenca.setSentencasFilhas(null);
						new DAO<Sentenca>(Sentenca.class).atualiza(sentenca);
					}
					for (Sentenca sentenca : listaDeSentencasDoBanco) {
						new DAO<Sentenca>(Sentenca.class).remove(sentenca);
					}
				}

				// Gerar as sentenças
				BpmnParaSentencas.gravarSentencas(arquivoBpmn.getInputStream(), projeto);

				// Salvar o arquivo BPMN
				arquivo.upload(String.valueOf(projeto.getId()) + ".bpmn", arquivoBpmn.getInputStream());

				context.getExternalContext().getFlash().setKeepMessages(true);
				FacesMessage message = new FacesMessage("Arquivo enviado",
						"O arquivo " + arquivoBpmn.getFileName() + " foi importado com sucesso.");
				context.addMessage(null, message);

				projeto.setStatus(StatusProjetoEnum.STATUS_AVALIACAO_SENTENCAS.getStatus());
				new DAO<Projeto>(Projeto.class).atualiza(projeto);

				// Atualização do log / contador de tempo
				registrarLog(LogInfoEnum.ACAO_IMPORTAR_DIAGRAMA_BPMN, LogInfoEnum.TIPO_SAIDA);

			} catch (Exception e) {

				context.getExternalContext().getFlash().setKeepMessages(true);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Inconsistência em diagrama",
						"O diagrama BPMN apresenta alguma inconsistência e não pode ser carregado. Causa: "
								+ e.getLocalizedMessage());
				context.addMessage(null, message);
				e.printStackTrace();

			}

		} else {

			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Nenhum arquivo foi selecionado.");
			context.addMessage(null, message);

		}

		return "importarBPMN?projetoId=" + this.projeto.getId() + "&faces-redirect=true";

	}

	public boolean podeGerarRoteiro() {
		return !projeto.getStatus().equals(StatusProjetoEnum.STATUS_EM_ELABORACAO.getStatus());
	}

	public void atualizarSentenca() {

		new DAO<Sentenca>(Sentenca.class).atualiza(sentenca);

		// Atualização do log / contador de tempo
		registrarLog(LogInfoEnum.ACAO_AJUSTAR_SENTENCAS, LogInfoEnum.TIPO_SAIDA);

	}

	public String descartarSentenca(int sentencaId, int cenaOrdem) {

		Sentenca sentencaDescartada = new DAO<Sentenca>(Sentenca.class).buscaPorId(sentencaId);
		FacesContext context = FacesContext.getCurrentInstance();

		// Verificar se é possível descartar
		if (SentencasParaCenas.isPossivelDescartarSentenca(sentencaDescartada)) {
			// Se for possível, atualizar a sentença e ajustar as ordens das cenas
			// posteriores já criadas

			sentencaDescartada.setNaoVaiTerCena(true);
			new DAO<Sentenca>(Sentenca.class).atualiza(sentencaDescartada);

			SentencasParaCenas.atualizarOrdemDasProximasCenas(cenaOrdem, sentencaDescartada.getProjeto());

			// Atualização do log / contador de tempo
			registrarLog(LogInfoEnum.ACAO_CRIAR_CENA, LogInfoEnum.TIPO_SAIDA);

			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sentença descartada",
					"Sentença descartada com sucesso!");
			context.addMessage(null, message);

			return "escolherSentencaParaCena?projetoId=" + this.projeto.getId() + "&faces-redirect=true";

		} else {
			// Se não for possível, retornar mensagem de erro.

			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sentença não descartada",
					"Não é possível descartar esta sentença!");
			context.addMessage(null, message);

			return "escolherSentencaParaCena?projetoId=" + this.projeto.getId() + "&faces-redirect=true";

		}

	}

	public String criarCenaAdicional(boolean vaiSerDepois, int cenaId) {

		FacesContext context = FacesContext.getCurrentInstance();
		Cena cena = new DAO<Cena>(Cena.class).buscaPorId(cenaId);

		String tipoDeElementoBpmn = "";
		if (cena.getSentenca() != null) {
			tipoDeElementoBpmn = cena.getSentenca().getTipoDeElementoBPMN();
		}

		// Verificar se a cena adicional vai ser depois do gateway
		if (vaiSerDepois && (tipoDeElementoBpmn.equals(TipoDeElementoEnum.GATEWAY_EXCLUSIVO.getTipo())
				|| tipoDeElementoBpmn.equals(TipoDeElementoEnum.GATEWAY_PARALELO.getTipo()))) {
			// Se for depois de gateway, retornar para mesma página com erro.

			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não é possível adicionar cena",
					"Não é possível adicionar cena após elemento do tipo gateway!");
			context.addMessage(null, message);

			return "escolherSentencaParaCena?projetoId=" + this.projeto.getId() + "&faces-redirect=true";

		} else {
			// Se não for depois de gateway, ir para a tela de criação de cena.

			if (vaiSerDepois) {
				// Se for depois, mandar como parâmetro a cenaPosterior

				return "comporCena?cenaAnteriorId=" + cenaId + "&faces-redirect=true";

			} else {
				// Se for antes, mandar como parâmetro a cenaAnterior

				return "comporCena?cenaPosteriorId=" + cenaId + "&faces-redirect=true";

			}
		}

	}

	public void verificarStatusDasCenas() {

		this.todasAsSentencasJaGeraramCenas = projeto.getStatus()
				.equals(StatusProjetoEnum.STATUS_ROTEIRO_ELABORADO.getStatus())
				|| projeto.getStatus().equals(StatusProjetoEnum.STATUS_JORNADA_DO_HEROI.getStatus());

	}

	public void registrarLog(LogInfoEnum acao, LogInfoEnum tipo) {
		FacesContext context = FacesContext.getCurrentInstance();

		Usuario usuario = new UsuarioDao()
				.buscarPorEmail((String) context.getExternalContext().getSessionMap().get("usuarioEmail"));

		ControladorDeTempo logReg = new ControladorDeTempo(acao.getNome(), tipo.getNome(), new Date(), usuario,
				projeto);
		new DAO<ControladorDeTempo>(ControladorDeTempo.class).adiciona(logReg);
	}

}
