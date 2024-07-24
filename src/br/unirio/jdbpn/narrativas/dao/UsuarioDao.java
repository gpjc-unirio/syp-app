package br.unirio.jdbpn.narrativas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.Usuario;

public class UsuarioDao {

	public boolean existe(Usuario usuario) {

		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.emailUsuario = :pEmail",
				Usuario.class);

		query.setParameter("pEmail", usuario.getEmailUsuario());

		try {
			@SuppressWarnings("unused")
			Usuario resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}

		em.close();

		return true;
	}

	public Usuario buscarPorEmail(String emailUsuario) {

		Usuario usuario = new Usuario();

		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.emailUsuario = :pEmail",
				Usuario.class);

		query.setParameter("pEmail", emailUsuario);

		try {
			usuario = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

		em.close();
		return usuario;

	}

	public List<Usuario> buscarPorProjeto(Projeto projeto) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select u from Projeto p join p.autores u where p.id = :pIdProjeto");

		query.setParameter("pIdProjeto", projeto.getId());

		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = query.getResultList();

		em.close();

		return usuarios;
	}

}
