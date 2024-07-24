package br.unirio.jdbpn.narrativas.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unirio.jdbpn.narrativas.model.Cena;
import br.unirio.jdbpn.narrativas.model.Dialogo;

public class DialogoDao {

	public List<Dialogo> buscarPorCena(Cena cena) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select d from Dialogo d join d.cena c where c.id = :pIdCena");

		query.setParameter("pIdCena", cena.getId());

		@SuppressWarnings("unchecked")
		List<Dialogo> dialogos = query.getResultList();

		em.close();

		Collections.sort(dialogos);
		return dialogos;
	}

	public Dialogo buscarPorCena(Cena cena, int ordem) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em
				.createQuery("select d from Dialogo d join d.cena c where c.id = :pIdCena and d.ordem = :pOrdem");

		query.setParameter("pIdCena", cena.getId());
		query.setParameter("pOrdem", ordem);

		Dialogo dialogo = (Dialogo) query.getSingleResult();

		em.close();

		return dialogo;

	}

}
