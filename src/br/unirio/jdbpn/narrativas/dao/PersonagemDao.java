package br.unirio.jdbpn.narrativas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unirio.jdbpn.narrativas.model.Cena;
import br.unirio.jdbpn.narrativas.model.Personagem;
import br.unirio.jdbpn.narrativas.model.Projeto;

public class PersonagemDao {

	public List<Personagem> buscarPorProjeto(Projeto projeto) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select s from Personagem s join s.projeto p where p.id = :pIdProjeto");

		query.setParameter("pIdProjeto", projeto.getId());

		@SuppressWarnings("unchecked")
		List<Personagem> personagens = query.getResultList();

		em.close();

		return personagens;
	}

	public List<Personagem> buscarPorCena(Cena cena) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select p from Cena c join c.personagens p where c.id = :pIdCena");

		query.setParameter("pIdCena", cena.getId());

		@SuppressWarnings("unchecked")
		List<Personagem> personagens = query.getResultList();

		em.close();

		return personagens;
	}

}
