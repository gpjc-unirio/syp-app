package br.unirio.jdbpn.narrativas.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.mindrot.jbcrypt.BCrypt;

import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.dao.UsuarioDao;
import br.unirio.jdbpn.narrativas.model.ControladorDeTempo;
import br.unirio.jdbpn.narrativas.model.Usuario;
import br.unirio.jdbpn.narrativas.util.LogInfoEnum;

@ManagedBean
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public String efetuarLogin() {
		System.out.println("Fazendo login do usuário: " + this.usuario.getEmailUsuario());

		FacesContext context = FacesContext.getCurrentInstance();

		boolean existe;
		try {
			existe = new UsuarioDao().existe(this.usuario);

			if (existe) {

				String senhaDoBanco = new UsuarioDao().buscarPorEmail(this.usuario.getEmailUsuario()).getSenhaUsuario();

				boolean autenticacao = BCrypt.checkpw(this.usuario.getSenhaUsuario(), senhaDoBanco);

				if (autenticacao) {

					usuario = new UsuarioDao().buscarPorEmail(this.usuario.getEmailUsuario());

					context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
					context.getExternalContext().getSessionMap().put("usuarioEmail", this.usuario.getEmailUsuario());

					context.getExternalContext().getFlash().setKeepMessages(true);
					context.addMessage(null, new FacesMessage("Login realizado com sucesso"));

					ControladorDeTempo logReg = new ControladorDeTempo(LogInfoEnum.ACAO_LOGIN.getNome(),
							LogInfoEnum.TIPO_ENTRADA.getNome(), new Date(), usuario);
					new DAO<ControladorDeTempo>(ControladorDeTempo.class).adiciona(logReg);

					return "inicio?faces-redirect=true";
				}

			}

			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Login inválido"));

			return "login?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Falha no login"));
			return "login?faces-redirect=true";
		}

	}

	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();

		usuario = new UsuarioDao()
				.buscarPorEmail((String) context.getExternalContext().getSessionMap().get("usuarioEmail"));

		ControladorDeTempo logReg = new ControladorDeTempo(LogInfoEnum.ACAO_LOGIN.getNome(),
				LogInfoEnum.TIPO_SAIDA.getNome(), new Date(), usuario);
		new DAO<ControladorDeTempo>(ControladorDeTempo.class).adiciona(logReg);

		context.getExternalContext().getSessionMap().remove("usuarioLogado");

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Logout realizado"));

		return "login?faces-redirect=true";
	}

	public boolean isNenhumUsuario() {		
		return new DAO<Usuario>(Usuario.class).listaTodos().size() == 0;
	}
	
	

}
