package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.dao.UsuarioDao;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.Usuario;
import br.unirio.jdbpn.narrativas.util.ControleDeAcesso;

@ManagedBean
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Projeto projeto = new Projeto();
	private Usuario autorNovo = new Usuario();
	private List<Usuario> autoresDoProjeto = new ArrayList<Usuario>();

	public Projeto getProjeto() {
		return projeto;
	}

	public Usuario getAutorNovo() {
		return autorNovo;
	}

	public void setAutorNovo(Usuario autorNovo) {
		this.autorNovo = autorNovo;
	}

	public List<Usuario> getAutoresDoProjeto() {
		return new UsuarioDao().buscarPorProjeto(this.projeto);
	}

	public boolean isAutorDoProjeto() {
		return ControleDeAcesso.isAutorDoProjeto(this.projeto);
	}

	public String adicionarAutor() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			Usuario autor = new UsuarioDao().buscarPorEmail(this.autorNovo.getEmailUsuario());
			autoresDoProjeto = new UsuarioDao().buscarPorProjeto(this.projeto);
			boolean isAutorJaCadastrado = false;

			if (autor == null) {
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Autor ainda não cadastrado"));
			} else {
				for (Usuario usuario : autoresDoProjeto) {
					if (usuario.getEmailUsuario().equals(autor.getEmailUsuario())) {
						isAutorJaCadastrado = true;
					}
				}
				if (isAutorJaCadastrado) {
					context.getExternalContext().getFlash().setKeepMessages(true);
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhuma ação",
							"Autor já tinha sido adicionado"));
				} else {
					autoresDoProjeto.add(autor);
					this.projeto.setAutores(autoresDoProjeto);

					new DAO<Projeto>(Projeto.class).atualiza(this.projeto);

					this.autorNovo = new Usuario();

					context.getExternalContext().getFlash().setKeepMessages(true);
					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Autor adicionado com sucesso"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Erro ao tentar cadastrar"));
		}

		return "adicionarAutor?projetoId=" + this.projeto.getId() + "&faces-redirect=true";

	}

	public void carregarProjetoPeloId() {
		this.projeto = new DAO<Projeto>(Projeto.class).buscaPorId(this.projeto.getId());
	}

}
