package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.model.Usuario;
import br.unirio.jdbpn.narrativas.util.PerfilUsuarioEnum;

@ManagedBean
@RequestScoped
public class ModificarUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<String> perfis = PerfilUsuarioEnum.getPerfis();
	private List<Usuario> todosOsUsuarios = new DAO<Usuario>(Usuario.class).listaTodos();
	private Usuario usuario;
	private int idUsuario = 0;

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean isUsuarioSelecionado() {
		return idUsuario > 0;
	}

	public Usuario getUsuario() {
		if (idUsuario > 0) {
			usuario = new DAO<Usuario>(Usuario.class).buscaPorId(idUsuario);
		} else {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getTodosOsUsuarios() {
		return todosOsUsuarios;
	}

	public List<String> getPerfis() {
		return perfis;
	}

	public void selecionarUsuario() {
		if (usuario != null) {
			System.out.println("Usuário selecionado: " + usuario.getNomeUsuario());
		}
	}

	public String gravar() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (usuario.getId() == null) {
			context.getExternalContext().getFlash().setKeepMessages(true);

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha", "Nenhum usuário selecionado"));

			return "modificarUsuario?faces-redirect=true";

		} else {

			try {
				System.out.println("Gravando usuário [" + this.usuario.getNomeUsuario() + "]");

				new DAO<Usuario>(Usuario.class).atualiza(usuario);
				this.usuario = new Usuario();

				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário atualizado com sucesso"));

				return "modificarUsuario?faces-redirect=true";

			} catch (Exception e) {
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha no cadastro de usuário"));
				e.printStackTrace();

				return "modificarUsuario?faces-redirect=true";
			}

		}

	}

}
