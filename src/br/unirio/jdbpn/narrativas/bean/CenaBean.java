package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

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
import br.unirio.jdbpn.narrativas.util.ControleDeAcesso;
import br.unirio.jdbpn.narrativas.util.LogInfoEnum;
import br.unirio.jdbpn.narrativas.util.SentencasParaCenas;
import br.unirio.jdbpn.narrativas.util.StatusProjetoEnum;
import br.unirio.jdbpn.narrativas.util.TipoDeElementoEnum;
import br.unirio.jdbpn.narrativas.util.TipoDeLocalEnum;

@ManagedBean
@ViewScoped
public class CenaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Projeto projeto = new Projeto();

	private Sentenca sentencaDaCena = new Sentenca();
	private Cena cena = new Cena();
	private Cena cenaAnterior = new Cena();
	private Cena cenaPosterior = new Cena();
	private DualListModel<Personagem> personagensDoProjeto;
	private List<String> tiposDeLocal = TipoDeLocalEnum.getTipos();

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Sentenca getSentencaDaCena() {
		return sentencaDaCena;
	}

	public void setSentencaDaCena(Sentenca sentencaDaCena) {
		this.sentencaDaCena = sentencaDaCena;
	}

	public Cena getCena() {
		String gatilhos = "";

		if(sentencaDaCena != null) {
			List<RelacaoSentencas> relacoes = sentencaDaCena.getSentencasFilhas();
	
			// Gatilhos da cena
			if (cena.getGatilhos() == null && sentencaDaCena.getId() > 0
					&& sentencaDaCena.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_EXCLUSIVO.getTipo())) {
				for (RelacaoSentencas relacao : relacoes) {
					if (!relacao.getSentencaFilha().isNaoVaiTerCena()) {
						gatilhos = gatilhos + "Se for " + relacao.getNomeOpcao() + ", vai para a cena ["
								+ relacao.getSentencaFilha().getSentencaCompleta() + "]. ";
					}
				}
				cena.setGatilhos(gatilhos);
			}
	
			if (cena.getTitulo() == null && sentencaDaCena.getId() > 0) {
				cena.setTitulo(sentencaDaCena.getSentencaCompleta());
			}
		}
		
		return cena;
	}

	public void setCena(Cena cena) {
		this.cena = cena;
	}

	public Cena getCenaAnterior() {
		return cenaAnterior;
	}

	public void setCenaAnterior(Cena cenaAnterior) {
		this.cenaAnterior = cenaAnterior;
	}

	public Cena getCenaPosterior() {
		return cenaPosterior;
	}

	public void setCenaPosterior(Cena cenaPosterior) {
		this.cenaPosterior = cenaPosterior;
	}

	public DualListModel<Personagem> getPersonagensDoProjeto() {
		List<Personagem> personagensSource = new ArrayList<Personagem>();
		List<Personagem> personagensTarget = new ArrayList<Personagem>();

		if (cena.getId() > 0) {
			personagensSource = new PersonagemDao().buscarPorProjeto(projeto);
			personagensTarget = new PersonagemDao().buscarPorCena(cena);

			Iterator<Personagem> iteratorSource = personagensSource.iterator();

			while (iteratorSource.hasNext()) {
				Personagem personagemSource = iteratorSource.next();
				for (Personagem personagemTarget : personagensTarget) {
					if (personagemSource.getId() == personagemTarget.getId()) {
						iteratorSource.remove();
					}
				}
			}

			for (Personagem personagemTarget : personagensTarget) {
				for (Personagem personagemSource : personagensSource) {
					if (personagemSource.getId() == personagemTarget.getId()) {
						personagensSource.remove(personagemSource);
					}
				}
			}
		} else {
			personagensSource = new PersonagemDao().buscarPorProjeto(projeto);
			personagensTarget = new ArrayList<Personagem>();
		}

		return new DualListModel<Personagem>(personagensSource, personagensTarget);
	}

	public void setPersonagensDoProjeto(DualListModel<Personagem> personagensDoProjeto) {
		this.personagensDoProjeto = personagensDoProjeto;
	}

	public List<String> getTiposDeLocal() {
		return tiposDeLocal;
	}

	public void setTiposDeLocal(List<String> tiposDeLocal) {
		this.tiposDeLocal = tiposDeLocal;
	}

	public boolean isAutorDoProjeto() {
		return ControleDeAcesso.isAutorDoProjeto(this.projeto);
	}

	public void carregarSentencaPeloId() {
		this.sentencaDaCena = new DAO<Sentenca>(Sentenca.class).buscaPorId(this.sentencaDaCena.getId());
		this.projeto = sentencaDaCena.getProjeto();
		this.cena.setTipoDeLocal(TipoDeLocalEnum.Interno.toString());
	}

	public void carregarCenaPeloId() {
		this.cena = new DAO<Cena>(Cena.class).buscaPorId(this.cena.getId());
		this.sentencaDaCena = cena.getSentenca();
		this.projeto = new DAO<Projeto>(Projeto.class).buscaPorId(cena.getProjeto().getId());
	}

	public void carregarCenaAnteriorPeloId() {
		this.cenaAnterior = new DAO<Cena>(Cena.class).buscaPorId(this.cenaAnterior.getId());
		this.projeto = cenaAnterior.getProjeto();
		this.cena.setTipoDeLocal(TipoDeLocalEnum.Interno.toString());
	}

	public void carregarCenaPosteriorPeloId() {
		this.cenaPosterior = new DAO<Cena>(Cena.class).buscaPorId(this.cenaPosterior.getId());
		this.projeto = cenaPosterior.getProjeto();
		this.cena.setTipoDeLocal(TipoDeLocalEnum.Interno.toString());
	}

	private int buscarOrdemCena(List<Cena> cenas, Cena cena) {
		for (Cena c : cenas) {
			if(c.getSentenca().getId() == cena.getSentenca().getId()) {
				return c.getOrdem();
			}
		}
		
		return -1;
	}
	
	public String cadastrar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			cena.setProjeto(projeto);
			

			List<Personagem> personagensEscolhidos = personagensDoProjeto.getTarget();
			if (personagensEscolhidos.size() > 0) {
				List<Personagem> personagensBD = new ArrayList<Personagem>();
				for (Personagem personagem : personagensEscolhidos) {
					personagensBD.add(new DAO<Personagem>(Personagem.class).buscaPorId(personagem.getId()));
				}
				cena.setPersonagens(personagensBD);
			}

			if (cena.getId() > 0) {
				new DAO<Cena>(Cena.class).atualiza(cena);
			} else {
				if (sentencaDaCena.getId() > 0) {

					Sentenca sentencaBD = new DAO<Sentenca>(Sentenca.class).buscaPorId(sentencaDaCena.getId());
					cena.setSentenca(sentencaBD);
					
					cena.setOrdem(sentencaBD.getNumero() - obterQuantidadeDeSentencasSemCena());

					if (sentencaBD.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_INICIAL.getTipo())) {
						cena.setAto(1);
					} else if (sentencaBD.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {
						cena.setAto(3);
					} else {
						cena.setAto(2);
					}

				} else if (cenaAnterior.getId() > 0) {

					cena.setOrdem(cenaAnterior.getOrdem() + 1);

					if (cenaAnterior.getSentenca() != null && cenaAnterior.getSentenca().getId() > 0
							&& cenaAnterior.getAto() == 1) {
						cena.setAto(2);
					} else {
						cena.setAto(cenaAnterior.getAto());
					}

					// Atualizar ordem das cenas posteriores
					atualizarOrdemDasCenasPosteriores(cena.getOrdem());

				} else if (cenaPosterior.getId() > 0) {

					cena.setOrdem(cenaPosterior.getOrdem());

					if (cenaPosterior.getSentenca() != null && cenaPosterior.getSentenca().getId() > 0
							&& cenaPosterior.getAto() == 3) {
						cena.setAto(2);
					} else {
						cena.setAto(cenaPosterior.getAto());
					}

					// Atualizar ordem das cenas posteriores
					atualizarOrdemDasCenasPosteriores(cena.getOrdem());

				}

				new DAO<Cena>(Cena.class).adiciona(cena);

				if (!projeto.getStatus().equals(StatusProjetoEnum.STATUS_ROTEIRO_ELABORADO.getStatus())
						&& SentencasParaCenas
								.todasAsSentencasJaGeraramCenas(new SentencaDao().buscarPorProjeto(projeto))) {
					projeto.setStatus(StatusProjetoEnum.STATUS_ROTEIRO_ELABORADO.getStatus());
					new DAO<Projeto>(Projeto.class).atualiza(projeto);
				}

			}

			Usuario usuario = new UsuarioDao()
					.buscarPorEmail((String) context.getExternalContext().getSessionMap().get("usuarioEmail"));

			ControladorDeTempo logReg = new ControladorDeTempo(LogInfoEnum.ACAO_CRIAR_CENA.getNome(),
					LogInfoEnum.TIPO_SAIDA.getNome(), new Date(), usuario, projeto);
			new DAO<ControladorDeTempo>(ControladorDeTempo.class).adiciona(logReg);

			cena = new Cena();

			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cena cadastrada com sucesso"));

			return "escolherSentencaParaCena.xhtml?projetoId=" + this.projeto.getId() + "&faces-redirect=true";

		} catch (Exception e) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha no cadastro da cena"));
			e.printStackTrace();

			cena = new Cena();

			return "comporCena?projetoId=" + this.projeto.getId() + "&sentencaId=" + this.sentencaDaCena.getId()
					+ "&faces-redirect=true";
		}
	}

	private void atualizarOrdemDasCenasPosteriores(int ordem) {

		List<Cena> listaDeCenasDoProjeto = new CenaDao().buscarPorProjeto(projeto);

		for (Cena cena : listaDeCenasDoProjeto) {
			if (cena.getOrdem() >= ordem) {
				cena.setOrdem(cena.getOrdem() + 1);

				new DAO<Cena>(Cena.class).atualiza(cena);
			}
		}

	}

	private int obterQuantidadeDeSentencasSemCena() {

		List<Sentenca> listaDeSentencasDoProjeto = new SentencaDao().buscarPorProjeto(projeto);
		int quantidadeDeSentencasSemCena = 0;

		for (Sentenca sentenca : listaDeSentencasDoProjeto) {
			if (sentenca.isNaoVaiTerCena()) {
				quantidadeDeSentencasSemCena++;
			}
		}

		System.out.println("Quantidade de sentenças sem cena: " + quantidadeDeSentencasSemCena);

		return quantidadeDeSentencasSemCena;
	}

}
