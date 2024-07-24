package br.unirio.jdbpn.narrativas.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unirio.jdbpn.narrativas.model.Cena;
import br.unirio.jdbpn.narrativas.model.Projeto;

public class CenaDao {

	public List<Cena> buscarPorProjeto(Projeto projeto) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select c from Cena c join c.projeto p where p.id = :pIdProjeto");

		query.setParameter("pIdProjeto", projeto.getId());

		@SuppressWarnings("unchecked")
		List<Cena> cenas = query.getResultList();

		em.close();

		Collections.sort(cenas);
		return cenas;
	}

	public List<Cena> buscarPorProjeto(Projeto projeto, boolean orderToOrdem) {

		EntityManager em = new JPAUtil().getEntityManager();
		
		if(!orderToOrdem) {
			return this.buscarPorProjeto(projeto);
		}

		Query query = em.createQuery("select c from Cena c join c.projeto p where p.id = :pIdProjeto order by c.ordem");
		query.setParameter("pIdProjeto", projeto.getId());

		@SuppressWarnings("unchecked")
		List<Cena> cenas = query.getResultList();

		em.close();

		Collections.sort(cenas);
		return cenas;
	}
	
	public List<Cena> buscarPorProjetoCenasAnexas(Projeto projeto, Cena cena, boolean isAntes) {

		EntityManager em = new JPAUtil().getEntityManager();

		String logicCommand = "<";
		String orderBy = "DESC";
		if(!isAntes) {
			logicCommand = ">";
			orderBy = "ASC";
		}
		
		String sql = "select c from Cena c join c.projeto p where p.id = :pIdProjeto and c.ordem "+ logicCommand +" :pOrdemCena order by c.ordem ";
		Query query = em.createQuery(sql);
		query.setParameter("pIdProjeto", projeto.getId());
		query.setParameter("pOrdemCena", cena.getOrdem());		

		@SuppressWarnings("unchecked")
		List<Cena> cenas = query.getResultList();

		em.close();

		//Collections.sort(cenas);
		return cenas;
	}
	
	public Cena buscarPorCenaAntesOuDepois(Projeto projeto, Cena cena, boolean isAntes) {

		EntityManager em = new JPAUtil().getEntityManager();

		String sql = "select c from Cena c join c.projeto p where p.id = :pIdProjeto and c.ordem = :pOrdemCena order by c.ordem ";
		
		Query query = em.createQuery(sql);
		query.setParameter("pIdProjeto", projeto.getId());
		
		if(isAntes) {
			query.setParameter("pOrdemCena", cena.getOrdem() - 1);
			if((cena.getOrdem() - 1) <= 0) {
				return null;
			}
		}else {
			query.setParameter("pOrdemCena", cena.getOrdem() + 1);
		}				
		
		Cena cenaResult = null;
		Object cenaObj = query.getSingleResult();		
		if(cenaObj != null) {
			cenaResult = (Cena)cenaObj;
		}

		em.close();

		//Collections.sort(cenas);
		return cenaResult;
	}
	
	public List<Cena> buscarPorProjeto(Projeto projeto, String jornadaDoHeroi) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery(
				"select c from Cena c join c.projeto p where p.id = :pIdProjeto and c.estagioDaJornadaDoHeroi = :pJornadaDoHeroi");

		query.setParameter("pIdProjeto", projeto.getId());
		query.setParameter("pJornadaDoHeroi", jornadaDoHeroi);

		@SuppressWarnings("unchecked")
		List<Cena> cenas = query.getResultList();

		em.close();

		return cenas;
	}

	public Cena buscarPorProjeto(Projeto projeto, int ordem) {
		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em
				.createQuery("select c from Cena c join c.projeto p where p.id = :pIdProjeto and c.ordem = :pOrdem");

		query.setParameter("pIdProjeto", projeto.getId());
		query.setParameter("pOrdem", ordem);

		Cena cena = (Cena) query.getSingleResult();

		em.close();

		return cena;
	}

}
