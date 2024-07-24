package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.mindrot.jbcrypt.BCrypt;

import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.model.Usuario;
import br.unirio.jdbpn.narrativas.util.PerfilUsuarioEnum;

@ManagedBean
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	private List<String> perfis = PerfilUsuarioEnum.getPerfis();

	public Usuario getUsuario() {
		return usuario;
	}

	public List<String> getPerfis() {
		return perfis;
	}

	public String gravar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			System.out.println("Gravando usuário [" + this.usuario.getNomeUsuario() + "]");

			String criptografia = BCrypt.gensalt();
			String senhaCriptografada = BCrypt.hashpw(this.usuario.getSenhaUsuario(), criptografia);
			this.usuario.setSenhaUsuario(senhaCriptografada);

			new DAO<Usuario>(Usuario.class).adiciona(this.usuario);
			this.usuario = new Usuario();

			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário cadastrado com sucesso"));

			return "login?faces-redirect=true";

		} catch (Exception e) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha no cadastro de usuário"));
			e.printStackTrace();

			return "novoUsuario?faces-redirect=true";
		}

	}

	public boolean podeCadastrarUsuario(Usuario usuarioLogado) {
		boolean nenhumUsuario = new DAO<Usuario>(Usuario.class).listaTodos().size() == 0;
		boolean administrador = false;
		if(usuarioLogado != null)
			administrador = usuarioLogado.isUsuarioAdministrador();

		return nenhumUsuario || administrador;
	}

}
