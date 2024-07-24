package br.unirio.jdbpn.narrativas.util;

import java.util.ArrayList;
import java.util.List;

import br.unirio.jdbpn.narrativas.dao.CenaDao;
import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.model.Cena;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.RelacaoSentencas;
import br.unirio.jdbpn.narrativas.model.Sentenca;

public abstract class SentencasParaCenas {
	
	public static List<Cena> converterLista(List<Sentenca> sentencas) {

		List<Cena> cenas = new ArrayList<Cena>();
		
		int ordem = 0;
		
		for (Sentenca sentenca : sentencas) {
			Cena cena = new Cena();

			if (!sentenca.isNaoVaiTerCena()) {
				ordem++;
				if (sentenca.getCena() != null) {
					cena = sentenca.getCena();
				} else {
					cena.setTitulo(sentenca.getSentencaCompleta());
					cena.setProjeto(sentenca.getProjeto());
					cena.setSentenca(sentenca);
					cena.setOrdem(ordem);
					
					if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_INICIAL.getTipo())) {
						cena.setAto(1);
					} else if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {
						cena.setAto(3);
					} else {
						cena.setAto(2);
					}
				}
				cenas.add(cena);
			}
		}
		return cenas;

	}

	public static boolean isPossivelDescartarSentenca(Sentenca sentenca) {

		if (!(sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_EXCLUSIVO.getTipo())
				|| sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_PARALELO.getTipo()))) {
			return true;
		} else {

			List<RelacaoSentencas> proximasSentencas = sentenca.getSentencasFilhas();
			List<Sentenca> sentencasFilhas = new ArrayList<Sentenca>();

			for (RelacaoSentencas relacaoSentencas : proximasSentencas) {
				sentencasFilhas.add(relacaoSentencas.getSentencaFilha());
			}

			int contagemDeCaminhosExistentes = 0;
			for (Sentenca sentencaFilha : sentencasFilhas) {
				if (!caminhoDescartado(sentencaFilha)) {
					contagemDeCaminhosExistentes++;
				}
			}

			return contagemDeCaminhosExistentes <= 1;

		}

	}

	private static boolean caminhoDescartado(Sentenca sentencaFilha) {

		if (!sentencaFilha.isNaoVaiTerCena()) {
			return false;
		} else {
			List<RelacaoSentencas> proximasSentencas = sentencaFilha.getSentencasFilhas();
			for (RelacaoSentencas relacaoSentencas : proximasSentencas) {
				if (relacaoSentencas.getSentencaFilha() != null) {
					caminhoDescartado(relacaoSentencas.getSentencaFilha());
				}
			}
		}

		return true;
	}

	public static void atualizarOrdemDasProximasCenas(int cenaOrdem, Projeto projeto) {

		List<Cena> listaDeCenasDoProjeto = new CenaDao().buscarPorProjeto(projeto);

		for (Cena cena : listaDeCenasDoProjeto) {
			if (cena.getOrdem() > cenaOrdem) {
				cena.setOrdem(cena.getOrdem() - 1);
				new DAO<Cena>(Cena.class).atualiza(cena);
			}
		}

	}

	public static boolean todasAsSentencasJaGeraramCenas(List<Sentenca> listaDeSentencasDoProjeto) {

		boolean status = true;
		for (Sentenca sentenca : listaDeSentencasDoProjeto) {
			if (sentenca.getCena() == null && !sentenca.isNaoVaiTerCena()) {
				status = false;
			}
		}

		return status;

	}

}
