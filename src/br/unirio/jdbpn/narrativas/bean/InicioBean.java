package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.unirio.jdbpn.narrativas.dao.ProjetoDao;
import br.unirio.jdbpn.narrativas.dao.UsuarioDao;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.Usuario;

@ManagedBean
@ViewScoped
public class InicioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Projeto> getProjetos() {
		// return new DAO<Projeto>(Projeto.class).listaTodos();
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		Usuario autor = new UsuarioDao().buscarPorEmail(usuarioLogado.getEmailUsuario());

		return new ProjetoDao().buscarPorAutor(autor);
	}

}
