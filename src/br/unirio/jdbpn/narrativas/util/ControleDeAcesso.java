package br.unirio.jdbpn.narrativas.util;

import java.util.List;

import javax.faces.context.FacesContext;

import br.unirio.jdbpn.narrativas.dao.UsuarioDao;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.Usuario;

public abstract class ControleDeAcesso {

	public static boolean isAutorDoProjeto(Projeto projeto) {
		boolean result = false;
		FacesContext context = FacesContext.getCurrentInstance();
		List<Usuario> autoresDoProjeto = new UsuarioDao().buscarPorProjeto(projeto);

		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		autoresDoProjeto = new UsuarioDao().buscarPorProjeto(projeto);

		for (Usuario autor : autoresDoProjeto) {
			if (autor.getEmailUsuario().equals(usuarioLogado.getEmailUsuario())) {
				result = true;
			}
		}

		return result;
	}

}
