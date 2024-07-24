package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.unirio.jdbpn.narrativas.dao.CenaDao;
import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.dao.UsuarioDao;
import br.unirio.jdbpn.narrativas.model.ControladorDeTempo;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.Usuario;
import br.unirio.jdbpn.narrativas.util.Arquivo;
import br.unirio.jdbpn.narrativas.util.ControleDeAcesso;
import br.unirio.jdbpn.narrativas.util.LogInfoEnum;
import br.unirio.jdbpn.narrativas.util.StatusProjetoEnum;

@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String viewName;
	private Projeto projeto = new Projeto();
	@SuppressWarnings("unused")
	private Arquivo arquivo;
	private Date dataHoraDeAbertura;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public Arquivo getArquivo() {
		return new Arquivo(projeto.getId());
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Date getDataHoraDeAbertura() {
		return dataHoraDeAbertura;
	}

	public void setDataHoraDeAbertura(Date dataHoraDeAbertura) {
		this.dataHoraDeAbertura = dataHoraDeAbertura;
	}

	public boolean isAutorDoProjeto() {
		return ControleDeAcesso.isAutorDoProjeto(this.projeto);
	}

	public boolean isAutorEGerenciaComSuporte() {
		FacesContext context = FacesContext.getCurrentInstance();

		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		return ControleDeAcesso.isAutorDoProjeto(this.projeto) && !usuarioLogado.isUsuarioSemSuporte();
	}

	public boolean isAutorEGerenciaSemSuporte() {
		FacesContext context = FacesContext.getCurrentInstance();

		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		return ControleDeAcesso.isAutorDoProjeto(this.projeto) && usuarioLogado.isUsuarioSemSuporte();
	}

	public String gravar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			this.projeto.setStatus(StatusProjetoEnum.STATUS_EM_ELABORACAO.getStatus());

			Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
			Usuario autor = new UsuarioDao().buscarPorEmail(usuarioLogado.getEmailUsuario());

			this.projeto.adicionaAutor(autor);
			System.out.println("Gravando projeto [" + this.projeto.getNome() + "]");
			new DAO<Projeto>(Projeto.class).adiciona(this.projeto);

			// Atualização do log / contador de tempo
			Usuario usuario = new UsuarioDao()
					.buscarPorEmail((String) context.getExternalContext().getSessionMap().get("usuarioEmail"));

			ControladorDeTempo logEntrada = new ControladorDeTempo(LogInfoEnum.ACAO_CRIAR_PROJETO.getNome(),
					LogInfoEnum.TIPO_ENTRADA.getNome(), dataHoraDeAbertura, usuario, projeto);
			new DAO<ControladorDeTempo>(ControladorDeTempo.class).adiciona(logEntrada);

			ControladorDeTempo logSaida = new ControladorDeTempo(LogInfoEnum.ACAO_CRIAR_PROJETO.getNome(),
					LogInfoEnum.TIPO_SAIDA.getNome(), new Date(), usuario, projeto);
			new DAO<ControladorDeTempo>(ControladorDeTempo.class).adiciona(logSaida);

			this.projeto = new Projeto();

			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Projeto cadastrado com sucesso"));

			return "inicio?faces-redirect=true";

		} catch (Exception e) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha no cadastro do projeto"));
			e.printStackTrace();

			return "novoProjeto?faces-redirect=true";
		}
	}

	public String gravarRoteiro() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			this.projeto.setStatus(StatusProjetoEnum.STATUS_ROTEIRO_ELABORADO.getStatus());
			new DAO<Projeto>(Projeto.class).atualiza(this.projeto);

			// Atualização do log / contador de tempo
			registrarLog(LogInfoEnum.TIPO_SAIDA);

			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Roteiro registrado com sucesso"));

			return "inicio?faces-redirect=true";

		} catch (Exception e) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha no registro do roteiro"));
			e.printStackTrace();

			return "registrarRoteiro";
		}

	}

	public void carregarProjetoPeloId() {
		this.projeto = new DAO<Projeto>(Projeto.class).buscaPorId(this.projeto.getId());

		// Atualização do log / contador de tempo
		if (this.viewName != null && this.viewName.equals("registrarRoteiro")) {
			registrarLog(LogInfoEnum.TIPO_ENTRADA);
		}
	}

	public boolean podeGerarRoteiro() {
		return !projeto.getStatus().equals(StatusProjetoEnum.STATUS_EM_ELABORACAO.getStatus());
	}

	public boolean temCenas() {
		return new CenaDao().buscarPorProjeto(projeto).size() > 0;
	}

	public void definirDataEHoraDeAbertura() {
		this.dataHoraDeAbertura = new Date();
	}

	public void registrarLog(LogInfoEnum tipo) {
		FacesContext context = FacesContext.getCurrentInstance();

		Usuario usuario = new UsuarioDao()
				.buscarPorEmail((String) context.getExternalContext().getSessionMap().get("usuarioEmail"));

		ControladorDeTempo logReg = new ControladorDeTempo(LogInfoEnum.ACAO_CRIAR_ROTEIRO.getNome(), tipo.getNome(),
				new Date(), usuario, projeto);
		new DAO<ControladorDeTempo>(ControladorDeTempo.class).adiciona(logReg);
	}

}
