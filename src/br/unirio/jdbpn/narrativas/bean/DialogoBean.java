package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.dao.DialogoDao;
import br.unirio.jdbpn.narrativas.dao.PersonagemDao;
import br.unirio.jdbpn.narrativas.model.Cena;
import br.unirio.jdbpn.narrativas.model.Dialogo;
import br.unirio.jdbpn.narrativas.model.Personagem;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.util.ControleDeAcesso;

@ManagedBean
@ViewScoped
public class DialogoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cena cena = new Cena();
	private Projeto projeto = new Projeto();
	private List<Personagem> personagensDaCena = new ArrayList<Personagem>();
	private List<Dialogo> dialogosDaCena = new ArrayList<Dialogo>();
	private Dialogo novoDialogo = new Dialogo();
	private int idPersonagemNovoDialogo = 0;

	public Cena getCena() {
		return cena;
	}

	public void setCena(Cena cena) {
		this.cena = cena;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Personagem> getPersonagensDaCena() {
		return personagensDaCena;
	}

	public void setPersonagensDaCena(List<Personagem> personagensDaCena) {
		this.personagensDaCena = personagensDaCena;
	}

	public List<Dialogo> getDialogosDaCena() {
		return dialogosDaCena;
	}

	public void setDialogosDaCena(List<Dialogo> dialogosDaCena) {
		this.dialogosDaCena = dialogosDaCena;
	}

	public Dialogo getNovoDialogo() {
		return novoDialogo;
	}

	public void setNovoDialogo(Dialogo novoDialogo) {
		this.novoDialogo = novoDialogo;
	}

	public int getIdPersonagemNovoDialogo() {
		return idPersonagemNovoDialogo;
	}

	public void setIdPersonagemNovoDialogo(int idPersonagemNovoDialogo) {
		this.idPersonagemNovoDialogo = idPersonagemNovoDialogo;
	}

	public boolean isAutorDoProjeto() {
		return ControleDeAcesso.isAutorDoProjeto(this.projeto);
	}

	public void carregarCenaPeloId() {
		this.cena = new DAO<Cena>(Cena.class).buscaPorId(this.cena.getId());
		this.personagensDaCena = new PersonagemDao().buscarPorCena(cena);
		this.dialogosDaCena = new DialogoDao().buscarPorCena(cena);
		this.projeto = new DAO<Projeto>(Projeto.class).buscaPorId(cena.getProjeto().getId());
	}

	public void subirOrdem(int dialogoId) {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			Dialogo dialogo = new DAO<Dialogo>(Dialogo.class).buscaPorId(dialogoId);
			Dialogo dialogoAnterior = new DialogoDao().buscarPorCena(cena, dialogo.getOrdem() - 1);

			System.out.println("Alterando a ordem de #" + dialogo.getOrdem() + " para #" + (dialogo.getOrdem() - 1));

			dialogoAnterior.setOrdem(dialogo.getOrdem());
			dialogo.setOrdem(dialogo.getOrdem() - 1);

			new DAO<Dialogo>(Dialogo.class).atualiza(dialogoAnterior);
			new DAO<Dialogo>(Dialogo.class).atualiza(dialogo);

			this.dialogosDaCena = new DialogoDao().buscarPorCena(cena);
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha na alteração da ordem das falas."));
			e.printStackTrace();
		}

	}

	public void descerOrdem(int dialogoId) {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			Dialogo dialogo = new DAO<Dialogo>(Dialogo.class).buscaPorId(dialogoId);
			Dialogo dialogoPosterior = new DialogoDao().buscarPorCena(cena, dialogo.getOrdem() + 1);

			System.out.println("Alterando a ordem de #" + dialogo.getOrdem() + " para #" + (dialogo.getOrdem() + 1));

			dialogoPosterior.setOrdem(dialogo.getOrdem());
			dialogo.setOrdem(dialogo.getOrdem() + 1);

			new DAO<Dialogo>(Dialogo.class).atualiza(dialogoPosterior);
			new DAO<Dialogo>(Dialogo.class).atualiza(dialogo);

			this.dialogosDaCena = new DialogoDao().buscarPorCena(cena);
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha na alteração da ordem das falas."));
			e.printStackTrace();
		}

	}

	public void excluirDialogo(int dialogoId) {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			Dialogo dialogo = new DAO<Dialogo>(Dialogo.class).buscaPorId(dialogoId);

			int ordem = dialogo.getOrdem();

			// Atualizar ordem das cenas posteriores
			for (Dialogo dialogoDaCena : dialogosDaCena) {
				if (dialogoDaCena.getOrdem() > ordem) {
					dialogoDaCena.setOrdem(ordem);
					new DAO<Dialogo>(Dialogo.class).atualiza(dialogoDaCena);
					ordem++;
				}
			}

			new DAO<Dialogo>(Dialogo.class).remove(dialogo);

			this.dialogosDaCena = new DialogoDao().buscarPorCena(cena);
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha na alteração da ordem das falas."));
			e.printStackTrace();
		}

	}

	public void editarDialogo(int dialogoId) {
		if (dialogoId == 0) {
			this.novoDialogo = new Dialogo();
			this.idPersonagemNovoDialogo = 0;
		} else {
			this.novoDialogo = new DAO<Dialogo>(Dialogo.class).buscaPorId(dialogoId);
			this.idPersonagemNovoDialogo = novoDialogo.getPersonagem().getId();
		}

		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalNovoDialogo').show();");
	}

	public void inserirDialogo() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			Personagem personagem = new DAO<Personagem>(Personagem.class).buscaPorId(idPersonagemNovoDialogo);

			novoDialogo.setPersonagem(personagem);
			novoDialogo.setCena(cena);

			// Edição de diálogo já cadastrado
			if (novoDialogo.getId() > 0) {
				new DAO<Dialogo>(Dialogo.class).atualiza(novoDialogo);
			} else {
				int ordem = dialogosDaCena.size() + 1;
				novoDialogo.setOrdem(ordem);
				new DAO<Dialogo>(Dialogo.class).adiciona(novoDialogo);
			}

			this.dialogosDaCena = new DialogoDao().buscarPorCena(cena);
			this.novoDialogo = new Dialogo();
			this.idPersonagemNovoDialogo = 0;

		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha no registro do diálogo."));
			e.printStackTrace();
		}

		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalNovoDialogo').hide();");

	}

}
