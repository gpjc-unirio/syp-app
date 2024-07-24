package br.unirio.jdbpn.narrativas.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.RelacaoSentencas;
import br.unirio.jdbpn.narrativas.model.Sentenca;

public class SentencaDao {

	public List<Sentenca> buscarPorProjeto(Projeto projeto) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select s from Sentenca s join s.projeto p where p.id = :pIdProjeto");

		query.setParameter("pIdProjeto", projeto.getId());

		@SuppressWarnings("unchecked")
		List<Sentenca> sentencas = query.getResultList();

		em.close();

		Collections.sort(sentencas);
		return sentencas;
	}

	public List<RelacaoSentencas> buscarProximasSentencas(Sentenca sentenca) {

		EntityManager em = new JPAUtil().getEntityManager();

		Query query = em.createQuery("select r from RelacaoSentencas r join r.sentencaPai s where s.id = :pIdSentenca");

		query.setParameter("pIdSentenca", sentenca.getId());

		@SuppressWarnings("unchecked")
		List<RelacaoSentencas> relacoesDeSentencas = query.getResultList();

		em.close();

		return relacoesDeSentencas;
	}

}
