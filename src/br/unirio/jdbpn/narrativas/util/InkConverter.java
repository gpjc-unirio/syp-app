package br.unirio.jdbpn.narrativas.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import br.unirio.jdbpn.narrativas.dao.CenaDao;
import br.unirio.jdbpn.narrativas.dao.DialogoDao;
import br.unirio.jdbpn.narrativas.dao.SentencaDao;
import br.unirio.jdbpn.narrativas.model.Cena;
import br.unirio.jdbpn.narrativas.model.Dialogo;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.RelacaoSentencas;
import br.unirio.jdbpn.narrativas.model.Sentenca;

public abstract class InkConverter {

	// TODO: Alterar os nomes dos nós para prevenir repetições de complementos

	private static List<Sentenca> listaDeSentencasDoProjeto;
	private static List<Integer> listaDesentencasJaEscritas;
	private static List<Cena> listaDeCenasObsInk;
	private static int quantidadeDeGatewaysParalelos;
	private static List<String> textoFinal;
	private static int gatewaysParalelosEscritos;
	private static Cena proximaCena;
	private static Cena loopScene;

	public static void gerarArquivo(Projeto projeto, BufferedWriter bufferedWriter, EscopoEnum escopo)
			throws IOException {

		if (escopo == EscopoEnum.Cenas
				&& (projeto.getStatus().equals(StatusProjetoEnum.STATUS_ROTEIRO_ELABORADO.getStatus())
						|| projeto.getStatus().equals(StatusProjetoEnum.STATUS_JORNADA_DO_HEROI.getStatus()))) {

			listaDeSentencasDoProjeto = new SentencaDao().buscarPorProjeto(projeto);
			listaDesentencasJaEscritas = new ArrayList<Integer>();
			listaDeCenasObsInk = new ArrayList<Cena>();

			Sentenca primeiraSentenca = new Sentenca();

			quantidadeDeGatewaysParalelos = 0;

			for (Sentenca sentenca : listaDeSentencasDoProjeto) {
				if (primeiraSentenca.getId() == 0 && sentenca.getNumero() == 1) {
					primeiraSentenca = sentenca;
				}
				if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_PARALELO.getTipo())) {
					quantidadeDeGatewaysParalelos++;
				}
			}

			// Tratamento para os gateways paralelos (criação das variáveis)
			if (quantidadeDeGatewaysParalelos > 0) {
				textoFinal = new ArrayList<String>();
				for (int i = 1; i <= quantidadeDeGatewaysParalelos; i++) {
					bufferedWriter.write("VAR gtPar" + String.valueOf(i) + " = 0");
					bufferedWriter.newLine();
					bufferedWriter.newLine();
				}
			}

			int qtdDeIndentacao = 0;
			int rota = 0;
			gatewaysParalelosEscritos = 0;
			
			escreverCenasDaListaB(primeiraSentenca, bufferedWriter, qtdDeIndentacao, rota, true);

			bufferedWriter.close();
			
		} else {

			listaDeSentencasDoProjeto = new SentencaDao().buscarPorProjeto(projeto);

			listaDesentencasJaEscritas = new ArrayList<Integer>();

			Sentenca primeiraSentenca = new Sentenca();

			quantidadeDeGatewaysParalelos = 0;

			for (Sentenca sentenca : listaDeSentencasDoProjeto) {
				if (primeiraSentenca.getId() == 0 && sentenca.getNumero() == 1) {
					primeiraSentenca = sentenca;
				}
				if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_PARALELO.getTipo())) {
					quantidadeDeGatewaysParalelos++;
				}
			}

			// Tratamento para os gateways paralelos (criação das variáveis)
			if (quantidadeDeGatewaysParalelos > 0) {
				textoFinal = new ArrayList<String>();
				for (int i = 1; i <= quantidadeDeGatewaysParalelos; i++) {
					bufferedWriter.write("VAR gtPar" + String.valueOf(i) + " = 0");
					bufferedWriter.newLine();
					bufferedWriter.newLine();
				}
			}

			int qtdDeIndentacao = 0;
			int rota = 0;
			gatewaysParalelosEscritos = 0;

			escreverSentencaDaListaB(primeiraSentenca, bufferedWriter, qtdDeIndentacao, rota);

			bufferedWriter.close();
		}

	}
	
	private static void processarCena(Cena cena, BufferedWriter bufferedWriter, int qtdDeIndentacao, boolean isPrimeiraCena, boolean makeEnd) {
		try {
			String textoSentenca = "";
			
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			
			textoSentenca = indentacao(qtdDeIndentacao) + "-> " + removerAcentos(cena.getTitulo());
			bufferedWriter.write(textoSentenca);
			bufferedWriter.newLine();

			bufferedWriter.write(indentacao(qtdDeIndentacao) + "=== " + removerAcentos(cena.getTitulo()));
			bufferedWriter.newLine();
			
			bufferedWriter.write(indentacao(qtdDeIndentacao) + "Cena: " + cena.getTitulo());
			bufferedWriter.newLine();
			
			qtdDeIndentacao++;

			// Local da cena
			if (cena.getLocal() != null && cena.getLocal().length() > 1) {
				bufferedWriter.write(indentacao(qtdDeIndentacao) + "Local: " + cena.getLocal());
				bufferedWriter.newLine();
			}

			// Tempo da cena
			if (cena.getTempo() != null && cena.getTempo().length() > 1) {
				bufferedWriter.write(indentacao(qtdDeIndentacao) + "Tempo: " + cena.getTempo());
				bufferedWriter.newLine();
			}

			// Descrição breve da cena
			if (cena.getDescricaoBreve() != null && cena.getDescricaoBreve().length() > 1) {
				bufferedWriter.write(indentacao(qtdDeIndentacao) + "Descrição da Cena: " + cena.getDescricaoBreve());
				bufferedWriter.newLine();
			}

			// Diálogos da cena
			List<Dialogo> dialogos = new DialogoDao().buscarPorCena(cena);
			if (dialogos != null && dialogos.size() > 0) {
				for (Dialogo dialogo : dialogos) {
					if (dialogo.getIntroducao() != null && dialogo.getIntroducao().length() > 1) {
						bufferedWriter.write(indentacao(qtdDeIndentacao) + dialogo.getIntroducao());
						bufferedWriter.newLine();
					}

					bufferedWriter.write(indentacao(qtdDeIndentacao) + "[" + dialogo.getPersonagem().getNome() + "]");
					bufferedWriter.newLine();

					if (dialogo.getObservacoes() != null && dialogo.getObservacoes().length() > 1) {
						bufferedWriter.write(indentacao(qtdDeIndentacao) + "(" + dialogo.getObservacoes() + ")");
						bufferedWriter.newLine();
					}

					bufferedWriter.write(indentacao(qtdDeIndentacao) + "_ " + dialogo.getDiscurso());
					bufferedWriter.newLine();
				}
			}
			
			if(cena.getSentenca() != null && (!cena.getSentenca().getTipoDeElementoBPMN().contains("Gateway"))){
				if (cena.getSentenca() != null && (cena.getAto() == 3)) {
					
					ArrayList<Cena> cenasEncontradas = processarCenasExtras(cena.getSentenca(), bufferedWriter, qtdDeIndentacao, false);
					
					if(cenasEncontradas.size() > 0) {
						bufferedWriter.write(indentacao(qtdDeIndentacao) + "* [Próxima Cena]");
						bufferedWriter.newLine();
						qtdDeIndentacao--;
						
						int countCena = 0;
						while(countCena < cenasEncontradas.size()) {
							listaDesentencasJaEscritas.add(cenasEncontradas.get(countCena).getId());	
							processarCena(cenasEncontradas.get(countCena), bufferedWriter, qtdDeIndentacao, false, (countCena == (cenasEncontradas.size() -1)));
							countCena++;
						}
					}else {
						bufferedWriter.write(indentacao(qtdDeIndentacao) + "-> END");
						bufferedWriter.newLine();
						qtdDeIndentacao--;
					}
					
				}else {
					ArrayList<Sentenca> percorridos = new ArrayList<Sentenca>(); 
					
					if(cena.getSentenca() != null && cena.getSentenca().isTemLoop() && 
							!cena.getSentenca().getTipoDeElementoBPMN().contains("Event")) {
						loopScene = cena;						
						
						if(cena.getSentenca().getSentencasFilhas().size() > 0) {							
							findMySelfAgainInFlow(cena.getSentenca(), 
									cena.getSentenca().getSentencasFilhas().get(0).getSentencaFilha(), 
									percorridos);
							if(!percorridos.contains(cena.getSentenca())) {
								loopScene = null;
							}else {
								Cena cenaLoop = percorridos.get(percorridos.size() - 2).getCena();
								cenaLoop.setObservacaoInk(textoSentenca);
								if(!listaDeCenasObsInk.contains(cenaLoop)) {
									listaDeCenasObsInk.add(cenaLoop);
								}
							}
						}
					}
					
					if(cena.getObservacaoInk() != null) {
						if(!cena.getObservacaoInk().trim().equals("")) {
							bufferedWriter.write(indentacao(qtdDeIndentacao) + cena.getObservacaoInk());
							bufferedWriter.newLine();
							qtdDeIndentacao--;
						}else {
							if(loopScene == null) {
								bufferedWriter.write(indentacao(qtdDeIndentacao) + "* [Próxima Cena]");
								bufferedWriter.newLine();
								qtdDeIndentacao--;
							}
						}
					}else{
						
						String txtFinalSentenca = "";
						if(loopScene == null) {
							txtFinalSentenca = indentacao(qtdDeIndentacao) + "* [Próxima Cena]";
							bufferedWriter.write(txtFinalSentenca);
							bufferedWriter.newLine();
							qtdDeIndentacao--;
						}
						
						if(txtFinalSentenca.equals("")) {
							int cenaLoopIndex = -1;
							for(int i = 0; i < listaDeCenasObsInk.size(); i++) {
								if(listaDeCenasObsInk.get(i).getId() == cena.getId()) {
									cenaLoopIndex = i;
									break;
								}
							}
							
							if(cenaLoopIndex != -1) {
								txtFinalSentenca = indentacao(qtdDeIndentacao) + listaDeCenasObsInk.get(cenaLoopIndex).getObservacaoInk();
								bufferedWriter.write(txtFinalSentenca);
								bufferedWriter.newLine();
								qtdDeIndentacao--;
							}
						}
					}
				}
			}else {
				
				String txtFinalSentenca = "";
				
				if(cena.getObservacaoInk() != null) {
					if(!cena.getObservacaoInk().trim().equals("")) {
						txtFinalSentenca = indentacao(qtdDeIndentacao) + cena.getObservacaoInk();
						bufferedWriter.write(txtFinalSentenca);
						bufferedWriter.newLine();
						qtdDeIndentacao--;
					}
				}else{
				
					if(loopScene != null) {
						if(cena.getSentenca() != null) {					
							for(RelacaoSentencas cenaLoop : cena.getSentenca().getSentencasFilhas()) {
								if(cenaLoop.getSentencaFilha().getId() == loopScene.getSentenca().getId()) {
									loopScene = null;
								}
							}
						}
					}
											
					if(cena.getSentenca() == null) {
						if(makeEnd) {
							qtdDeIndentacao--;
							txtFinalSentenca = indentacao(qtdDeIndentacao) + "-> END";
							bufferedWriter.write(txtFinalSentenca);
							bufferedWriter.newLine();
						}else {
							txtFinalSentenca = indentacao(qtdDeIndentacao) + "* [Próxima Cena]";
							bufferedWriter.write(txtFinalSentenca);
							bufferedWriter.newLine();
							qtdDeIndentacao--;
						}					
					}
				}
				
				if(txtFinalSentenca.equals("")) {
					int cenaLoopIndex = -1;
					for(int i = 0; i < listaDeCenasObsInk.size(); i++) {
						if(listaDeCenasObsInk.get(i).getId() == cena.getId()) {
							cenaLoopIndex = i;
							break;
						}
					}
					
					if(cenaLoopIndex != -1) {
						txtFinalSentenca = indentacao(qtdDeIndentacao) + listaDeCenasObsInk.get(cenaLoopIndex).getObservacaoInk();
						bufferedWriter.write(txtFinalSentenca);
						bufferedWriter.newLine();
						qtdDeIndentacao--;
					}
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean findMySelfAgainInFlow(Sentenca origem, Sentenca proxima, ArrayList<Sentenca> percorridos) {
		
		boolean isFound = false;
				
		if(percorridos.contains(proxima)) {
			return true;
		}
		
		if(proxima != null) {
			percorridos.add(proxima);
			for (RelacaoSentencas relacao : proxima.getSentencasFilhas()) {					
				isFound = findMySelfAgainInFlow(origem, relacao.getSentencaFilha(), percorridos);
			}
		}
		
		return isFound;		
	}

	private static void buscarCena(Projeto projeto, Cena cena, ArrayList<Cena> cenasEncontradas, boolean isAntes) {
		try {
			Cena cenaEncontrada = new CenaDao().buscarPorCenaAntesOuDepois(projeto, cena, isAntes);
			if(cenaEncontrada != null) {
				if(cenaEncontrada.getSentenca() == null) {
					cenasEncontradas.add(cenaEncontrada);
					buscarCena(projeto, cenaEncontrada, cenasEncontradas, isAntes);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	private static ArrayList<Cena> processarCenasExtras(Sentenca sentenca, BufferedWriter bufferedWriter, int qtdDeIndentacao, boolean isAntes) {
		ArrayList<Cena> cenasEncontradas = new ArrayList<Cena>();
		try {
	
			buscarCena(sentenca.getProjeto(), sentenca.getCena(), cenasEncontradas, isAntes);
			
			if(isAntes) {
				int countCena = cenasEncontradas.size() - 1;
				while (countCena >= 0) {
					Cena cena = cenasEncontradas.get(countCena);
					listaDesentencasJaEscritas.add(cena.getId());
					processarCena(cena, bufferedWriter, qtdDeIndentacao, false, false);
					countCena--;
				}
			}else {
				if(sentenca.getCena().getAto() != 3) {
					int countCena = 0;
					while (countCena < cenasEncontradas.size()) {
						Cena cena = cenasEncontradas.get(countCena);
						listaDesentencasJaEscritas.add(cena.getId());					
						processarCena(cena, bufferedWriter, qtdDeIndentacao, false, false);				
						countCena++;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			return cenasEncontradas;
		}		
	}
	
	private static void escreverCenasDaListaB(Sentenca sentenca, BufferedWriter bufferedWriter, int qtdDeIndentacao,
			int rota, boolean isPrimeiraSentenca) {

		try {
			String textoSentenca = "";			
			
			Cena cena = sentenca.getCena();
			
			// Verificar primeiro se a sentença já foi escrita
			if (sentencaJaFoiEscrita(cena.getId())) {

				//textoSentenca = indentacao(qtdDeIndentacao) + "-> " + removerAcentos(cena.getTitulo());
				//bufferedWriter.write(textoSentenca);
				//bufferedWriter.newLine();

			} else {
				listaDesentencasJaEscritas.add(cena.getId());
				
				List<RelacaoSentencas> mapeamentoDasProximasSentencas = new SentencaDao()
						.buscarProximasSentencas(sentenca);

				if (!sentenca.getTipoDeElementoBPMN().contains("Gateway")
						&& mapeamentoDasProximasSentencas.size() <= 1) {
					
					if(isPrimeiraSentenca) {
						if(cena.getSentenca() != null) {
							processarCenasExtras(cena.getSentenca(), bufferedWriter, qtdDeIndentacao, true);
						}				
					}
					
					processarCena(cena, bufferedWriter, qtdDeIndentacao, isPrimeiraSentenca, false);

					if(cena.getSentenca() != null && cena.getAto() != 3) {
						processarCenasExtras(cena.getSentenca(), bufferedWriter, qtdDeIndentacao, false);
					}
					
					if (!sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {

						if (sentenca.isTerminoDeParalelismo()) {

							for (String texto : textoFinal) {
								bufferedWriter.write(texto);
								bufferedWriter.newLine();
							}
							textoSentenca = 
									"-> " + removerAcentos(indentacao(qtdDeIndentacao) + mapeamentoDasProximasSentencas
											.iterator().next().getSentencaFilha().getCena().getTitulo());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();

						} else {

							escreverCenasDaListaB(
									mapeamentoDasProximasSentencas.iterator().next().getSentencaFilha(), bufferedWriter,
									qtdDeIndentacao, rota, false);

						}
					}

				} else {

					if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_EXCLUSIVO.getTipo())
							|| (!sentenca.getTipoDeElementoBPMN().contains("Gateway")
									&& mapeamentoDasProximasSentencas.size() > 1)) {
						
						processarCena(sentenca.getCena(), bufferedWriter, qtdDeIndentacao, false, false);

						qtdDeIndentacao++;

						for (RelacaoSentencas relacaoSentencas : mapeamentoDasProximasSentencas) {
							
							textoSentenca = indentacao(qtdDeIndentacao) + "+ [";
							bufferedWriter.write(textoSentenca);
							if (relacaoSentencas.getNomeOpcao() == null || relacaoSentencas.getNomeOpcao().trim().equals("")) {
								rota++;
								textoSentenca = "Rota" + String.valueOf(rota);
								bufferedWriter.write(textoSentenca);
							} else {
								textoSentenca = relacaoSentencas.getNomeOpcao();
								bufferedWriter.write(textoSentenca);
							}
							bufferedWriter.write("]");
							bufferedWriter.newLine();
							qtdDeIndentacao++;

							Sentenca proximaSentenca = relacaoSentencas.getSentencaFilha();

							textoSentenca = indentacao(qtdDeIndentacao) + "-> "
									+ removerAcentos(proximaSentenca.getCena().getTitulo());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();

							qtdDeIndentacao--;
						}
						qtdDeIndentacao--;

						for (RelacaoSentencas relacaoSentencas : mapeamentoDasProximasSentencas) {
							bufferedWriter.newLine();

							Sentenca proximaSentenca = relacaoSentencas.getSentencaFilha();

							escreverProximaCena(bufferedWriter, qtdDeIndentacao, rota, proximaSentenca);
						}

					}

					if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_PARALELO.getTipo())) {

						rota++;
						gatewaysParalelosEscritos++;

						textoSentenca = indentacao(qtdDeIndentacao) + "=== " + removerAcentos(sentenca.getCena().getTitulo());
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();
						
						textoSentenca =
								indentacao(qtdDeIndentacao) + "~ gtPar" + String.valueOf(gatewaysParalelosEscritos)
								+ " = gtPar" + String.valueOf(gatewaysParalelosEscritos) + " + 1";
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						String rotaIniciada = "Rota" + String.valueOf(rota) + "Iniciada";
						String rotaFinalizada = "Rota" + String.valueOf(rota) + "Finalizada";

						textoSentenca = indentacao(qtdDeIndentacao) + "-> " + rotaIniciada;
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();
						
						textoSentenca = indentacao(qtdDeIndentacao) + "=== " + rotaIniciada;
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						qtdDeIndentacao++;

						Sentenca proximaSentenca = new Sentenca();

						for (RelacaoSentencas relacaoSentencas : mapeamentoDasProximasSentencas) {
							textoSentenca = indentacao(qtdDeIndentacao) + "* [";
							bufferedWriter.write(textoSentenca);
							textoSentenca = relacaoSentencas.getNomeOpcao();
							bufferedWriter.write(textoSentenca);
							bufferedWriter.write("]");
							bufferedWriter.newLine();
							qtdDeIndentacao++;

							proximaSentenca = relacaoSentencas.getSentencaFilha();

							textoSentenca = indentacao(qtdDeIndentacao) + "-> "
									+ removerAcentos(proximaSentenca.getCena().getTitulo());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();

							qtdDeIndentacao--;
						}
						qtdDeIndentacao--;

						textoSentenca = indentacao(qtdDeIndentacao) + "* -> " + rotaFinalizada;
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						textoSentenca = indentacao(qtdDeIndentacao) + "=== " + rotaFinalizada;
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						textoSentenca = 
								indentacao(qtdDeIndentacao) + "~ gtPar" + String.valueOf(gatewaysParalelosEscritos)
								+ " = gtPar" + String.valueOf(gatewaysParalelosEscritos) + " - 1";
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						textoFinal.add("{ gtPar" + String.valueOf(gatewaysParalelosEscritos) + " > 0:");
						textoFinal.add(indentacao(1) + "-> " + rotaIniciada);
						textoFinal.add("}");

						Sentenca sequenciaDoParalelismo = getSequenciaDoParalelismo(proximaSentenca);

						if (sequenciaDoParalelismo != null && sequenciaDoParalelismo.getId() > 0) {

							textoSentenca = indentacao(qtdDeIndentacao) + "-> "
									+ removerAcentos(sequenciaDoParalelismo.getCena().getTitulo());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();

							escreverProximaCena(bufferedWriter, qtdDeIndentacao, rota, sequenciaDoParalelismo);

						} else {
							textoSentenca = indentacao(qtdDeIndentacao) + "-> Fim";
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();
						}

						for (RelacaoSentencas relacaoSentencas : mapeamentoDasProximasSentencas) {

							bufferedWriter.newLine();

							proximaSentenca = relacaoSentencas.getSentencaFilha();

							escreverProximaCena(bufferedWriter, qtdDeIndentacao, rota, proximaSentenca);

						}

					}

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void escreverSentencaDaListaB(Sentenca sentenca, BufferedWriter bufferedWriter, int qtdDeIndentacao,
			int rota) {

		try {
			String textoSentenca = "";
			
			// Verificar primeiro se a sentença já foi escrita
			if (sentencaJaFoiEscrita(sentenca.getId())) {

				textoSentenca = indentacao(qtdDeIndentacao) + "-> " + removerAcentos(sentenca.getComplementos());
				bufferedWriter.write(textoSentenca);
				bufferedWriter.newLine();

			} else {

				listaDesentencasJaEscritas.add(sentenca.getId());

				List<RelacaoSentencas> mapeamentoDasProximasSentencas = new SentencaDao()
						.buscarProximasSentencas(sentenca);

				if (!sentenca.getTipoDeElementoBPMN().contains("Gateway")
						&& mapeamentoDasProximasSentencas.size() <= 1) {

					if (sentenca.isTemLoop()) {
						bufferedWriter.newLine();
						textoSentenca = indentacao(qtdDeIndentacao) + "-> " + removerAcentos(sentenca.getComplementos());
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();
						
						textoSentenca = indentacao(qtdDeIndentacao) + "=== " + removerAcentos(sentenca.getComplementos());
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();
					}

					textoSentenca = indentacao(qtdDeIndentacao) + sentenca.getSentencaCompleta();					
					bufferedWriter.write(textoSentenca);
					bufferedWriter.newLine();

					if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {

						textoSentenca = indentacao(qtdDeIndentacao) + "-> END";
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

					} else {

						if (sentenca.isTerminoDeParalelismo()) {

							for (String texto : textoFinal) {
								bufferedWriter.write(texto);
								bufferedWriter.newLine();
							}
							textoSentenca = 
									"-> " + removerAcentos(indentacao(qtdDeIndentacao) + mapeamentoDasProximasSentencas
											.iterator().next().getSentencaFilha().getComplementos());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();

						} else {

							escreverSentencaDaListaB(
									mapeamentoDasProximasSentencas.iterator().next().getSentencaFilha(), bufferedWriter,
									qtdDeIndentacao, rota);

						}

					}

				} else {

					if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_EXCLUSIVO.getTipo())
							|| (!sentenca.getTipoDeElementoBPMN().contains("Gateway")
									&& mapeamentoDasProximasSentencas.size() > 1)) {

						if (sentenca.isTemLoop()) {
							bufferedWriter.newLine();
							textoSentenca = indentacao(qtdDeIndentacao) + "-> " + removerAcentos(sentenca.getComplementos());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();
							
							textoSentenca = indentacao(qtdDeIndentacao) + "=== " + removerAcentos(sentenca.getComplementos());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();
						}

						textoSentenca = indentacao(qtdDeIndentacao) + sentenca.getSentencaCompleta();					
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						qtdDeIndentacao++;

						for (RelacaoSentencas relacaoSentencas : mapeamentoDasProximasSentencas) {
							
							textoSentenca = indentacao(qtdDeIndentacao) + "+ [";
							bufferedWriter.write(textoSentenca);
							if (relacaoSentencas.getNomeOpcao() == null || relacaoSentencas.getNomeOpcao().trim().equals("")) {
								rota++;
								textoSentenca = "Rota" + String.valueOf(rota);
								bufferedWriter.write(textoSentenca);
							} else {
								textoSentenca = relacaoSentencas.getNomeOpcao();
								bufferedWriter.write(textoSentenca);
							}
							bufferedWriter.write("]");
							bufferedWriter.newLine();
							qtdDeIndentacao++;

							Sentenca proximaSentenca = relacaoSentencas.getSentencaFilha();

							textoSentenca = indentacao(qtdDeIndentacao) + "-> "
									+ removerAcentos(proximaSentenca.getComplementos());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();

							qtdDeIndentacao--;
						}
						qtdDeIndentacao--;

						for (RelacaoSentencas relacaoSentencas : mapeamentoDasProximasSentencas) {
							bufferedWriter.newLine();

							Sentenca proximaSentenca = relacaoSentencas.getSentencaFilha();

							escreverProximaSentenca(bufferedWriter, qtdDeIndentacao, rota, proximaSentenca);
						}

					}

					if (sentenca.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.GATEWAY_PARALELO.getTipo())) {

						rota++;
						gatewaysParalelosEscritos++;

						textoSentenca =
								indentacao(qtdDeIndentacao) + "~ gtPar" + String.valueOf(gatewaysParalelosEscritos)
								+ " = gtPar" + String.valueOf(gatewaysParalelosEscritos) + " + 1";
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						String rotaIniciada = "Rota" + String.valueOf(rota) + "Iniciada";
						String rotaFinalizada = "Rota" + String.valueOf(rota) + "Finalizada";

						textoSentenca = indentacao(qtdDeIndentacao) + "-> " + rotaIniciada;
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();
						
						textoSentenca = indentacao(qtdDeIndentacao) + "=== " + rotaIniciada;
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						qtdDeIndentacao++;

						Sentenca proximaSentenca = new Sentenca();

						for (RelacaoSentencas relacaoSentencas : mapeamentoDasProximasSentencas) {
							textoSentenca = indentacao(qtdDeIndentacao) + "* [";
							bufferedWriter.write(textoSentenca);
							textoSentenca = relacaoSentencas.getNomeOpcao();
							bufferedWriter.write(textoSentenca);
							bufferedWriter.write("]");
							bufferedWriter.newLine();
							qtdDeIndentacao++;

							proximaSentenca = relacaoSentencas.getSentencaFilha();

							textoSentenca = indentacao(qtdDeIndentacao) + "-> "
									+ removerAcentos(proximaSentenca.getComplementos());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();

							qtdDeIndentacao--;
						}
						qtdDeIndentacao--;

						textoSentenca = indentacao(qtdDeIndentacao) + "* -> " + rotaFinalizada;
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						textoSentenca = indentacao(qtdDeIndentacao) + "=== " + rotaFinalizada;
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						textoSentenca = 
								indentacao(qtdDeIndentacao) + "~ gtPar" + String.valueOf(gatewaysParalelosEscritos)
								+ " = gtPar" + String.valueOf(gatewaysParalelosEscritos) + " - 1";
						bufferedWriter.write(textoSentenca);
						bufferedWriter.newLine();

						textoFinal.add("{ gtPar" + String.valueOf(gatewaysParalelosEscritos) + " > 0:");
						textoFinal.add(indentacao(1) + "-> " + rotaIniciada);
						textoFinal.add("}");

						Sentenca sequenciaDoParalelismo = getSequenciaDoParalelismo(proximaSentenca);

						if (sequenciaDoParalelismo != null && sequenciaDoParalelismo.getId() > 0) {

							textoSentenca = indentacao(qtdDeIndentacao) + "-> "
									+ removerAcentos(sequenciaDoParalelismo.getComplementos());
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();

							escreverProximaSentenca(bufferedWriter, qtdDeIndentacao, rota, sequenciaDoParalelismo);

						} else {
							textoSentenca = indentacao(qtdDeIndentacao) + "-> Fim";
							bufferedWriter.write(textoSentenca);
							bufferedWriter.newLine();
						}

						for (RelacaoSentencas relacaoSentencas : mapeamentoDasProximasSentencas) {

							bufferedWriter.newLine();

							proximaSentenca = relacaoSentencas.getSentencaFilha();

							escreverProximaSentenca(bufferedWriter, qtdDeIndentacao, rota, proximaSentenca);

						}

					}

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Sentenca getSequenciaDoParalelismo(Sentenca ultimaSentencaDoRamo) {
		boolean chegouNoTerminoDoParalelismo = false;
		Sentenca sequenciaDoParalelismo = new Sentenca();

		while (!chegouNoTerminoDoParalelismo) {

			if (ultimaSentencaDoRamo.isTerminoDeParalelismo()) {
				chegouNoTerminoDoParalelismo = true;
				sequenciaDoParalelismo = ultimaSentencaDoRamo.getSentencasFilhas().iterator().next().getSentencaFilha();
			} else {
				if (ultimaSentencaDoRamo.getTipoDeElementoBPMN().equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {
					chegouNoTerminoDoParalelismo = true;
				} else {

					List<RelacaoSentencas> proximasSentencas = ultimaSentencaDoRamo.getSentencasFilhas();

					if (proximasSentencas.size() > 1) {
						for (RelacaoSentencas relacaoSentencas : proximasSentencas) {
							ultimaSentencaDoRamo = getSequenciaDoParalelismo(relacaoSentencas.getSentencaFilha());
						}
					} else {
						ultimaSentencaDoRamo = ultimaSentencaDoRamo.getSentencasFilhas().iterator().next()
								.getSentencaFilha();
					}

				}
			}

		}

		return sequenciaDoParalelismo;
	}

	public static void escreverProximaCena(BufferedWriter bufferedWriter, int qtdDeIndentacao, int rota,
			Sentenca proximaSentenca) throws IOException {
		if (!sentencaJaFoiEscrita(proximaSentenca.getCena().getId())) {
			escreverCenasDaListaB(proximaSentenca, bufferedWriter, qtdDeIndentacao, rota, false);
		}
	}
	
	public static void escreverProximaSentenca(BufferedWriter bufferedWriter, int qtdDeIndentacao, int rota,
			Sentenca proximaSentenca) throws IOException {
		if (!sentencaJaFoiEscrita(proximaSentenca.getId())) {

			if (!proximaSentenca.isTemLoop()) {
				bufferedWriter.newLine();
				bufferedWriter.write(
						indentacao(qtdDeIndentacao) + "=== " + removerAcentos(proximaSentenca.getComplementos()));
				bufferedWriter.newLine();
			}
			escreverSentencaDaListaB(proximaSentenca, bufferedWriter, qtdDeIndentacao, rota);

		}
	}

	private static void escreverCenaDaLista(Cena cena, BufferedWriter bufferedWriter, int qtdDeIndentacao,
			boolean ultimaCena) {
		try {
			String textoCena = "";
			
			// Se for evento final significa que é uma ultima cena
			if (cena.getSentenca() != null && cena.getSentenca().getTipoDeElementoBPMN()
			.equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {
				ultimaCena = true;
			}
			
			if (cena.getSentenca() != null && (!cena.getSentenca().getTipoDeElementoBPMN()
											.equals(TipoDeElementoEnum.ATIVIDADE.getTipo()))) {
				if(loopScene != null) {
					loopScene = null;
				}
			}
									
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			
			if(loopScene == null) {
				textoCena = indentacao(qtdDeIndentacao) + "-> " + removerAcentos(cena.getTitulo());
				bufferedWriter.write(textoCena);
				bufferedWriter.newLine();
	
				bufferedWriter.write(indentacao(qtdDeIndentacao) + "=== " + removerAcentos(cena.getTitulo()));
				bufferedWriter.newLine();
			}
			
			if((cena.getSentenca() != null) && (cena.getSentenca().isTemLoop())) {
				if(!cena.getSentenca().getTipoDeElementoBPMN()
						.equals(TipoDeElementoEnum.EVENTO_FINAL.getTipo())) {
					loopScene = cena;
				}				
			}
			
			bufferedWriter.write(indentacao(qtdDeIndentacao) + "Cena: " + cena.getTitulo());
			bufferedWriter.newLine();
			
			qtdDeIndentacao++;

			// Local da cena
			if (cena.getLocal() != null && cena.getLocal().length() > 1) {
				bufferedWriter.write(indentacao(qtdDeIndentacao) + "Local: " + cena.getLocal());
				bufferedWriter.newLine();
			}

			// Tempo da cena
			if (cena.getTempo() != null && cena.getTempo().length() > 1) {
				bufferedWriter.write(indentacao(qtdDeIndentacao) + "Tempo: " + cena.getTempo());
				bufferedWriter.newLine();
			}

			// Descrição breve da cena
			if (cena.getDescricaoBreve() != null && cena.getDescricaoBreve().length() > 1) {
				bufferedWriter.write(indentacao(qtdDeIndentacao) + "Descrição da Cena: " + cena.getDescricaoBreve());
				bufferedWriter.newLine();
			}

			// Diálogos da cena
			List<Dialogo> dialogos = new DialogoDao().buscarPorCena(cena);
			if (dialogos != null && dialogos.size() > 0) {
				for (Dialogo dialogo : dialogos) {
					if (dialogo.getIntroducao() != null && dialogo.getIntroducao().length() > 1) {
						bufferedWriter.write(indentacao(qtdDeIndentacao) + dialogo.getIntroducao());
						bufferedWriter.newLine();
					}

					bufferedWriter.write(indentacao(qtdDeIndentacao) + "[" + dialogo.getPersonagem().getNome() + "]");
					bufferedWriter.newLine();

					if (dialogo.getObservacoes() != null && dialogo.getObservacoes().length() > 1) {
						bufferedWriter.write(indentacao(qtdDeIndentacao) + "(" + dialogo.getObservacoes() + ")");
						bufferedWriter.newLine();
					}

					bufferedWriter.write(indentacao(qtdDeIndentacao) + "_ " + dialogo.getDiscurso());
					bufferedWriter.newLine();
				}
			}

			// Se for gateway exclusivo
			if (cena.getSentenca() != null && cena.getSentenca().getTipoDeElementoBPMN()
					.equals(TipoDeElementoEnum.GATEWAY_EXCLUSIVO.getTipo())) {
				
				List<RelacaoSentencas> relacoes = cena.getSentenca().getSentencasFilhas();

				for (RelacaoSentencas relacao : relacoes) {
					bufferedWriter.write(indentacao(qtdDeIndentacao) + "+ [" + relacao.getNomeOpcao() + "]");
					bufferedWriter.newLine();

					qtdDeIndentacao++;
					bufferedWriter.write(indentacao(qtdDeIndentacao) + "-> "
							+ removerAcentos(relacao.getSentencaFilha().getCena().getTitulo()));
					bufferedWriter.newLine();
					qtdDeIndentacao--;
				}

			} else

			// Se for gateway paralelo
			if (cena.getSentenca() != null && cena.getSentenca().getTipoDeElementoBPMN()
					.equals(TipoDeElementoEnum.GATEWAY_PARALELO.getTipo())) {

				gatewaysParalelosEscritos++;

				textoCena =
						indentacao(qtdDeIndentacao) + "~ gtPar" + String.valueOf(gatewaysParalelosEscritos)
						+ " = gtPar" + String.valueOf(gatewaysParalelosEscritos) + " + 1";
				bufferedWriter.write(textoCena);
				bufferedWriter.newLine();
				
				List<RelacaoSentencas> relacoes = cena.getSentenca().getSentencasFilhas();

				String rotaIniciada = "RotaCena" + String.valueOf(cena.getOrdem()) + "Iniciada";
				String rotaFinalizada = "RotaCena" + String.valueOf(cena.getOrdem()) + "Finalizada";

				bufferedWriter.write(indentacao(qtdDeIndentacao) + "-> " + rotaIniciada);
				bufferedWriter.newLine();
				bufferedWriter.write(indentacao(qtdDeIndentacao) + "=== " + rotaIniciada);
				bufferedWriter.newLine();

				qtdDeIndentacao++;

				for (RelacaoSentencas relacao : relacoes) {
					bufferedWriter.write(indentacao(qtdDeIndentacao) + "* [" + relacao.getNomeOpcao() + "]");
					bufferedWriter.newLine();

					qtdDeIndentacao++;
					bufferedWriter.write(indentacao(qtdDeIndentacao) + "-> "
							+ removerAcentos(relacao.getSentencaFilha().getCena().getTitulo()));
					bufferedWriter.newLine();
					qtdDeIndentacao--;

					bufferedWriter.write(indentacao(qtdDeIndentacao) + "-> " + rotaIniciada);
					bufferedWriter.newLine();
					
					textoCena = "{ gtPar" + String.valueOf(gatewaysParalelosEscritos) + " > 0:";
					textoCena = textoCena + "\n" + indentacao(1) + "-> " + rotaIniciada;
					textoCena = textoCena + "\n" +"}";
					
					if(relacao.getSentencaFilha() != null && relacao.getSentencaFilha().getSentencasFilhas().size() > 0) {
						Sentenca sentencaFila = relacao.getSentencaFilha().getSentencasFilhas().get(0).getSentencaFilha();
						if(sentencaFila != null) {
							textoCena = textoCena + "\n" +
									indentacao(qtdDeIndentacao) + "-> " + 
									removerAcentos(sentencaFila.getCena().getTitulo());
						}
					}
					
					if(proximaCena != null) {
						
					}
					
					relacao.getSentencaFilha().getCena().setObservacaoInk(textoCena);
				}

				qtdDeIndentacao--;

				bufferedWriter.write(indentacao(qtdDeIndentacao) + "* -> " + rotaFinalizada);
				bufferedWriter.newLine();
				qtdDeIndentacao++;

				bufferedWriter.write(indentacao(qtdDeIndentacao) + "=== " + rotaFinalizada);
				bufferedWriter.newLine();
				qtdDeIndentacao--;
				
				textoCena = 
						indentacao(qtdDeIndentacao) + "~ gtPar" + String.valueOf(gatewaysParalelosEscritos)
						+ " = gtPar" + String.valueOf(gatewaysParalelosEscritos) + " - 1";
				bufferedWriter.write(textoCena);
				bufferedWriter.newLine();

			} else 

				// Próxima cena, se houver
				if (!ultimaCena) {
					
					if(loopScene == null) {
						String obs = cena.getObservacaoInk();
						if(obs == null) {
							bufferedWriter.write(indentacao(qtdDeIndentacao) + "* [Próxima Cena]");
						}else {
							if(obs.trim().equals("")) {
								bufferedWriter.write(indentacao(qtdDeIndentacao) + "* [Próxima Cena]");
							}else {
								bufferedWriter.write(indentacao(qtdDeIndentacao) + cena.getObservacaoInk());
							}							
						}
						
					}
					bufferedWriter.newLine();
					qtdDeIndentacao--;
				}
	
				// Se não houver
				else {
					qtdDeIndentacao--;
					bufferedWriter.write(indentacao(qtdDeIndentacao) + "-> END");
					bufferedWriter.newLine();
				}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean sentencaJaFoiEscrita(Integer idDaSentenca) {
		return listaDesentencasJaEscritas.contains(idDaSentenca);
	}

	private static String indentacao(int n) {
		String textoDeRetorno = "";

		if (n > 0) {
			for (int i = 0; i < n; i++) {
				textoDeRetorno = textoDeRetorno + "    ";
			}
		}

		return textoDeRetorno;
	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str.replace(".", ""), Normalizer.Form.NFD).replaceAll("[^a-zA-Z1-9 ]", "")
				.replace(" ", "_");
	}

}
