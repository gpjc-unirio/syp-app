package br.unirio.jdbpn.narrativas.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.Usuario;

public class ProjetoDao {

	public List<Projeto> buscarPorAutor(Usuario autor) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select p from Projeto p join p.autores u where u.id = :pIdUsuario");

		query.setParameter("pIdUsuario", autor.getId());

		@SuppressWarnings("unchecked")
		List<Projeto> projetos = query.getResultList();

		em.close();

		Collections.sort(projetos);
		return projetos;
	}

}
