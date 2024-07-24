package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.dao.PersonagemDao;
import br.unirio.jdbpn.narrativas.dao.SentencaDao;
import br.unirio.jdbpn.narrativas.dao.UsuarioDao;
import br.unirio.jdbpn.narrativas.model.ControladorDeTempo;
import br.unirio.jdbpn.narrativas.model.Personagem;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.Sentenca;
import br.unirio.jdbpn.narrativas.model.Usuario;
import br.unirio.jdbpn.narrativas.util.ArquetipoPersonagemEnum;
import br.unirio.jdbpn.narrativas.util.ControleDeAcesso;
import br.unirio.jdbpn.narrativas.util.LogInfoEnum;

@ManagedBean
@ViewScoped
public class PersonagemBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Projeto projeto = new Projeto();
	private List<String> sujeitos = new ArrayList<String>();
	private Personagem personagem = new Personagem();
	@SuppressWarnings("unused")
	private List<String> arquetipos = new ArrayList<String>();
	@SuppressWarnings("unused")
	private List<Personagem> personagensDoProjeto = new ArrayList<Personagem>();

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<String> getSujeitos() {
		List<Sentenca> sentencasDoProjeto = new SentencaDao().buscarPorProjeto(this.projeto);

		for (Sentenca sentenca : sentencasDoProjeto) {
			if (sentenca.isSujeitoUmaFuncao()) {
				if (!sujeitos.contains(sentenca.getSujeito())) {
					sujeitos.add(sentenca.getSujeito());
				}
			}
		}

		return sujeitos;
	}

	public void setSujeitos(List<String> sujeitos) {
		this.sujeitos = sujeitos;
	}

	public Personagem getPersonagem() {
		return personagem;
	}

	public void setPersonagem(Personagem personagem) {
		this.personagem = personagem;
	}

	public List<String> getArquetipos() {
		return ArquetipoPersonagemEnum.getArquetipos();
	}

	public void setArquetipos(List<String> arquetipos) {
		this.arquetipos = arquetipos;
	}

	public List<Personagem> getPersonagensDoProjeto() {
		return new PersonagemDao().buscarPorProjeto(this.projeto);
	}

	public boolean isAutorDoProjeto() {
		return ControleDeAcesso.isAutorDoProjeto(this.projeto);
	}

	public void carregarProjetoPeloId() {
		this.projeto = new DAO<Projeto>(Projeto.class).buscaPorId(this.projeto.getId());

		// Atualização do log / contador de tempo
		registrarLog(LogInfoEnum.TIPO_ENTRADA);
	}

	public void carregarPersonagemPeloId() {
		this.personagem = new DAO<Personagem>(Personagem.class).buscaPorId(this.personagem.getId());
		this.projeto = personagem.getProjeto();

		// Atualização do log / contador de tempo
		registrarLog(LogInfoEnum.TIPO_ENTRADA);
	}

	public void registrarLog(LogInfoEnum tipo) {
		FacesContext context = FacesContext.getCurrentInstance();

		Usuario usuario = new UsuarioDao()
				.buscarPorEmail((String) context.getExternalContext().getSessionMap().get("usuarioEmail"));

		ControladorDeTempo logReg = new ControladorDeTempo(LogInfoEnum.ACAO_CRIAR_PERSONAGEM.getNome(), tipo.getNome(),
				new Date(), usuario, projeto);
		new DAO<ControladorDeTempo>(ControladorDeTempo.class).adiciona(logReg);
	}

	public String registrar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			if (personagem.getId() > 0) {
				new DAO<Personagem>(Personagem.class).atualiza(personagem);
			} else {
				personagem.setProjeto(projeto);
				new DAO<Personagem>(Personagem.class).adiciona(personagem);
			}

			this.personagem = new Personagem();

			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Personagem registrado com sucesso"));

			// Atualização do log / contador de tempo
			registrarLog(LogInfoEnum.TIPO_SAIDA);

		} catch (Exception e) {

			e.printStackTrace();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Erro ao tentar registrar"));

		}

		return "cadastrarPersonagem?projetoId=" + this.projeto.getId() + "&faces-redirect=true";

	}

}
