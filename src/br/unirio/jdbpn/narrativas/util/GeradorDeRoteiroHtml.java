package br.unirio.jdbpn.narrativas.util;

import java.util.List;

import br.unirio.jdbpn.narrativas.dao.CenaDao;
import br.unirio.jdbpn.narrativas.dao.DialogoDao;
import br.unirio.jdbpn.narrativas.dao.SentencaDao;
import br.unirio.jdbpn.narrativas.dao.UsuarioDao;
import br.unirio.jdbpn.narrativas.model.Cena;
import br.unirio.jdbpn.narrativas.model.Dialogo;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.RelacaoSentencas;
import br.unirio.jdbpn.narrativas.model.Sentenca;
import br.unirio.jdbpn.narrativas.model.Usuario;

public abstract class GeradorDeRoteiroHtml {

	public static String gerarRoteiro(Projeto projeto, EscopoEnum escopo) {

		String roteiro = "";

		if (escopo == EscopoEnum.Sentencas
				&& !projeto.getStatus().equals(StatusProjetoEnum.STATUS_EM_ELABORACAO.getStatus())) {

			List<Sentenca> sentencas = new SentencaDao().buscarPorProjeto(projeto);

			roteiro = roteiro + incluirCapa(projeto);

			// Primeiro Ato
			roteiro = roteiro + "<div class=\"ato\">Primeiro Ato</div>";
			for (Sentenca sentenca : sentencas) {
				if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_INICIAL.getTipo())) {
					roteiro = roteiro + incluirCenaNoRoteiro(sentenca);
				}
			}

			// Segundo Ato
			roteiro = roteiro + "<div class=\"ato\">Segundo Ato</div>";
			for (Sentenca sentenca : sentencas) {
				if (!sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_INICIAL.getTipo())
						&& !sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {
					roteiro = roteiro + incluirCenaNoRoteiro(sentenca);
				}
			}

			// Terceiro Ato
			roteiro = roteiro + "<div class=\"ato\">Terceiro Ato</div>";
			for (Sentenca sentenca : sentencas) {
				if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {
					roteiro = roteiro + incluirCenaNoRoteiro(sentenca);
				}
			}

			return roteiro;

		} else if (escopo == EscopoEnum.Cenas
				&& (projeto.getStatus().equals(StatusProjetoEnum.STATUS_ROTEIRO_ELABORADO.getStatus())
						|| projeto.getStatus().equals(StatusProjetoEnum.STATUS_JORNADA_DO_HEROI.getStatus()))) {

			List<Cena> cenas = new CenaDao().buscarPorProjeto(projeto);

			roteiro = roteiro + incluirCapa(projeto);

			// Primeiro ato
			roteiro = roteiro + "<div class=\"ato\">Primeiro Ato</div>";
			for (Cena cena : cenas) {
				if (cena.getAto() == 1) {
					roteiro = roteiro + incluirCenaNoRoteiro(cena);
				}
			}

			// Segundo ato
			roteiro = roteiro + "<div class=\"ato\">Segundo Ato</div>";
			for (Cena cena : cenas) {
				if (cena.getAto() == 2) {
					roteiro = roteiro + incluirCenaNoRoteiro(cena);
				}
			}

			// Terceiro ato
			roteiro = roteiro + "<div class=\"ato\">Terceiro Ato</div>";
			for (Cena cena : cenas) {
				if (cena.getAto() == 3) {
					roteiro = roteiro + incluirCenaNoRoteiro(cena);
				}
			}

			return roteiro;

		} else {
			return "<div style=\"color: red;\">Erro</div>";
		}

	}

	private static String incluirCenaNoRoteiro(Sentenca sentenca) {
		// Cabeçalho da cena
		String texto = "<div class=\"cena-titulo\"><span class=\"cena-numero\">" + sentenca.getNumero() + "</span> "
				+ sentenca.getSentencaCompleta() + "</div>";
		List<RelacaoSentencas> relacoes = sentenca.getSentencasFilhas();

		// Transições da cena
		if (relacoes.size() > 1) {
			texto = texto + "<div class=\"transicao\">";
			for (RelacaoSentencas relacao : relacoes) {
				texto = texto + "se for " + relacao.getNomeOpcao() + ", vai para a cena nº "
						+ relacao.getSentencaFilha().getNumero() + ". ";
			}
			texto = texto + "</div>";
		} else if (relacoes.size() > 0) {
			texto = texto + "<div class=\"transicao\">vai para a cena nº "
					+ relacoes.iterator().next().getSentencaFilha().getNumero() + "</div>";
		}
		return texto;
	}

	private static String incluirCenaNoRoteiro(Cena cena) {
		// Cabeçalho da cena
		String local = cena.getLocal();
		String tempo = cena.getTempo();	
			
		String texto = "<div class=\"cena-titulo\"><span class=\"cena-numero\">" + cena.getOrdem() + "</span> " + cena.getTitulo();
		if (tempo.length() > 1) {
			texto = texto + " - " + tempo + "</div>";
		} else {
			texto = texto + "</div>";
		}
		
		if (local.length() <= 1) {
			local = "Local indeterminado";
		} else {
			local = TipoDeLocalEnum.valueOf(cena.getTipoDeLocal()).getAbreviado() + " " + local;
		}
		texto = texto + "<div class=\"acao\">[LOCAL]: " + local + "</div>";
		

		// Ação, personagens e diálogos da cena
		if (cena.getDescricaoBreve() != null && cena.getDescricaoBreve().length() > 1) {
			texto = texto + "<div class=\"acao\">[DESCRIÇÃO DA CENA]: " + cena.getDescricaoBreve() + "</div>";
		}
		
		List<Dialogo> dialogos = new DialogoDao().buscarPorCena(cena);
		if (dialogos != null && dialogos.size() > 0) {
			for (Dialogo dialogo : dialogos) {
				if (dialogo.getIntroducao() != null && dialogo.getIntroducao().length() > 1) {
					texto = texto + "<div class=\"acao\">" + dialogo.getIntroducao() + "</div>";
				}
				texto = texto + "<div class=\"personagem\">" + dialogo.getPersonagem().getNome() + "</div>";
				if (dialogo.getObservacoes() != null && dialogo.getObservacoes().length() > 1) {
					texto = texto + "<div class=\"parenteses\">(" + dialogo.getObservacoes() + ")</div>";
				}
				texto = texto + "<div class=\"dialogo\">" + dialogo.getDiscurso() + "</div>";
			}
		}
		if (cena.getResultados() != null && cena.getResultados().length() > 1) {
			texto = texto + "<div class=\"acao\">" + cena.getResultados() + "</div>";
		}

		// Transições da cena
		if (cena.getGatilhos() != null && cena.getGatilhos().length() > 1) {
			texto = texto + "<div class=\"transicao\">" + cena.getGatilhos() + "</div>";
		}

		return texto;
	}

	private static String incluirCapa(Projeto projeto) {

		String texto = "<div class=\"capa\">";
		texto = texto + "<div class=\"acao\">" + projeto.getNome() + "</div>";
		texto = texto + "<div class=\"acao\">por</div>";

		List<Usuario> autores = new UsuarioDao().buscarPorProjeto(projeto);
		for (Usuario autor : autores) {
			texto = texto + "<div class=\"acao\">" + autor.getNomeUsuario() + "</div>";
		}

		texto = texto + "<div class=\"acao\"> </div>";
		texto = texto + "<div class=\"acao\"> </div>";
		texto = texto + "<div class=\"acao\">" + projeto.getDescricao() + "</div>";
		texto = texto + "</div>";

		return texto;

	}

}
