package br.unirio.jdbpn.narrativas.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.Outgoing;
import org.camunda.bpm.model.bpmn.impl.instance.SourceRef;
import org.camunda.bpm.model.bpmn.impl.instance.TargetRef;
import org.camunda.bpm.model.bpmn.instance.BoundaryEvent;
import org.camunda.bpm.model.bpmn.instance.DataInputAssociation;
import org.camunda.bpm.model.bpmn.instance.DataOutputAssociation;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Lane;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;

import br.unirio.jdbpn.narrativas.dao.DAO;
import br.unirio.jdbpn.narrativas.model.ElementoBpmn;
import br.unirio.jdbpn.narrativas.model.Projeto;
import br.unirio.jdbpn.narrativas.model.RelacaoSentencas;
import br.unirio.jdbpn.narrativas.model.Sentenca;

public abstract class BpmnParaSentencas {

	private static BpmnModelInstance modelInstance;

	// Inteiro incremental para o atributo número dos objetos da classe Sentença
	private static int numero;

	// Objeto para armazenar os IDs dos elementos (oriundos do arquivo BPMN) que já
	// foram "convertidos" em sentenças, para evitar duplicação de sentenças
	private static List<String> listaDeElementosMapeados;

	// HashMap para fazer a associação entre uma sentença gerada e o seu elemento
	// correspondente no modelo BPMN
	private static Map<String, Integer> correspondenciaEntreIdDaSentencaEIdDoBpmn;

	// Inteiro incremental para contar quantas lanes estão sem nome e, assim quantas
	// sentenças não têm referência para sujeito
	private static int contagemDeLanesSemNome;

	public static void gravarSentencas(InputStream arquivoBpmn, Projeto projeto) {

		// Inicialização de todas as variáveis da classe
		modelInstance = Bpmn.readModelFromStream(arquivoBpmn);
		numero = 1;
		listaDeElementosMapeados = new ArrayList<String>();
		correspondenciaEntreIdDaSentencaEIdDoBpmn = new HashMap<String, Integer>();
		contagemDeLanesSemNome = 0;

		// Identificando o elemento inicial do modelo
		ModelElementType startEventType = modelInstance.getModel().getType(StartEvent.class);
		Collection<ModelElementInstance> startEventInstances = modelInstance.getModelElementsByType(startEventType);
		FlowNode flowNode = (FlowNode) startEventInstances.iterator().next();
		ElementoBpmn startEvent = new ElementoBpmn(flowNode);

		// Iniciando método recursivo de geração das sentenças a partir do evento
		// inicial
		registrarSentenca(startEvent, projeto);

		// Eliminando alocação de memória do HashMap
		correspondenciaEntreIdDaSentencaEIdDoBpmn.clear();

	}

	private static Sentenca registrarSentenca(ElementoBpmn elementoPai, Projeto projeto) {

		String tipoDeElemento = elementoPai.getTipo().toLowerCase();

		// Apenas os elementos do tipo evento, atividade e gateway geram as sentenças
		if (tipoDeElemento.contains("event") || tipoDeElemento.contains("task") || tipoDeElemento.contains("gateway")
				|| tipoDeElemento.contains("subprocess")) {

			Sentenca sentenca = new Sentenca();
			sentenca.setTemLoop(false);
			sentenca.setTerminoDeParalelismo(false);

			// Se o elemento ainda não foi mapeado, é preciso criar e definir os parâmetros
			// da sentença correspondente.
			if (!isElementoJaMapeado(elementoPai.getId())) {

				listaDeElementosMapeados.add(elementoPai.getId());

				String sujeito = getSujeito(elementoPai);
				String verbo = getVerbo(elementoPai, sujeito);
				String complementos = "";
				if (elementoPai.getNome() != null) {
					complementos = elementoPai.getNome();
				}
				String tipoDeElementoBpmn = getTipoDeElementoBpmn(elementoPai);

				sentenca.setNumero(numero);
				sentenca.setSujeito(sujeito);
				sentenca.setVerbo(verbo);
				sentenca.setTipoDeElementoBPMN(tipoDeElementoBpmn);
				sentenca.setProjeto(projeto);
				sentenca.setSujeitoUmaFuncao(tipoDeElemento.contains("task") && sujeito.length() > 1);
				sentenca.setNaoVaiTerCena(false);
				sentenca.setElementoBpmnId(elementoPai.getId());

				// Se for atividade, tem que ver se tem recursos associados para compor os
				// complementos
				if (tipoDeElemento.contains("task") || tipoDeElemento.contains("subprocess")) {

					String complemento02 = getRecurso(elementoPai, DataInputAssociation.class);
					String conectivo01 = "utilizando de";
					String complemento03 = getRecurso(elementoPai, DataOutputAssociation.class);
					String conectivo02 = "em";

					complementos = definirComplementos(complementos, conectivo01, complemento02, conectivo02,
							complemento03);

				}

				// Se for gateway, ele só escreve os complementos 02 e 03, se o 01 estiver vazio
				if (tipoDeElemento.contains("gateway") && complementos.length() == 0) {
					Collection<SequenceFlow> sequenceFlowCollection = elementoPai.getFlowNode().getOutgoing();
					List<SequenceFlow> sequenceFlowList = new ArrayList<SequenceFlow>(sequenceFlowCollection);

					if (sequenceFlowList.size() > 1) {
						String conectivo01 = "";
						String complemento02 = sequenceFlowList.get(0).getName();

						if (complemento02 == null || complemento02.length() == 0) {
							complemento02 = "Opção 01";
						}

						String conectivo02 = "ou";
						String complemento03 = sequenceFlowList.get(1).getName();

						if (complemento03 == null || complemento03.length() == 0) {
							complemento03 = "Opção 02";
						}

						complementos = definirComplementos(complementos, conectivo01, complemento02, conectivo02,
								complemento03);
					}

				}

				// Se for evento de borda, ele pega o texto do conector seguinte
				if (tipoDeElemento.contains("event") && tipoDeElemento.contains("boundary")
						&& complementos.length() == 0) {

					if (elementoPai.getFlowNode() != null) {

						Collection<SequenceFlow> sequenceFlowCollection = elementoPai.getFlowNode().getOutgoing();
						List<SequenceFlow> sequenceFlowList = new ArrayList<SequenceFlow>(sequenceFlowCollection);
						complementos = sequenceFlowList.get(0).getName();

					} else {

						ModelElementType outgoingType = modelInstance.getModel().getType(Outgoing.class);
						Collection<ModelElementInstance> outgoingList = elementoPai.getModelElementInstance()
								.getChildElementsByType(outgoingType);

						String sequenceFlowId = outgoingList.iterator().next().getTextContent();
						ModelElementInstance sequenceFlow = modelInstance.getModelElementById(sequenceFlowId);
						complementos = sequenceFlow.getAttributeValue("name");

					}

				}

				sentenca.setComplementos(complementos);

				new DAO<Sentenca>(Sentenca.class).adiciona(sentenca);

				numero++;

				correspondenciaEntreIdDaSentencaEIdDoBpmn.put(elementoPai.getId(), sentenca.getId());

				// A partir daqui são verificados as próximas sentenças do fluxo do modelo BPMN.
				// As próximas sentenças são se relacionam com as anteriores através da classe
				// RelacaoSentencas.

				// Se for gateway, há um tratamento diferente, porque é preciso ver as opções em
				// descrição textual e relacionar esse texto com o elemento que vem na sequência
				if (tipoDeElemento.contains("gateway")) {

					Collection<SequenceFlow> sequenceFlowList = elementoPai.getFlowNode().getOutgoing();
					int contadorDeCaminhos = 0;

					for (SequenceFlow sequenceFlow : sequenceFlowList) {

						contadorDeCaminhos++;
						String opcaoDoGateway = sequenceFlow.getName();
						ElementoBpmn sequenciaDoGateway = new ElementoBpmn(sequenceFlow.getTarget());

						if (opcaoDoGateway == null) {
							opcaoDoGateway = "Camimho " + String.valueOf(contadorDeCaminhos);
						}

						Sentenca proximaSentenca;
						if (!isElementoJaMapeado(sequenciaDoGateway.getId())) {
							proximaSentenca = registrarSentenca(sequenciaDoGateway, projeto);
						} else {
							proximaSentenca = new DAO<Sentenca>(Sentenca.class)
									.buscaPorId(getIdDaSentencaJaMapeada(sequenciaDoGateway.getId()));

							proximaSentenca.setTemLoop(true);
							new DAO<Sentenca>(Sentenca.class).atualiza(proximaSentenca);

						}

						if (proximaSentenca != null && !isRelacaoJaMapeada(sentenca, proximaSentenca)) {
							RelacaoSentencas relacaoSentencas = new RelacaoSentencas(sentenca, proximaSentenca);
							relacaoSentencas.setNomeOpcao(opcaoDoGateway);
							new DAO<RelacaoSentencas>(RelacaoSentencas.class).adiciona(relacaoSentencas);
						}

					}

				} else {

					Collection<ElementoBpmn> proximosElementos = getProximosElementoDoFluxo(elementoPai);

					if (proximosElementos.size() > 0) {
						for (ElementoBpmn elemento : proximosElementos) {

							Sentenca proximaSentenca = new Sentenca();

							// Verifica se é um elemento de fusão de fluxos, porque se for não gera sentença
							if (isElementoGatewayDeFechamento(elemento)) {
								sentenca.setTerminoDeParalelismo(true);
								new DAO<Sentenca>(Sentenca.class).atualiza(sentenca);

								Collection<ElementoBpmn> sequenciaDoGatewayDeFechamento = getProximosElementoDoFluxo(
										elemento);

								if (sequenciaDoGatewayDeFechamento.size() > 0) {
									proximaSentenca = registrarSentenca(
											sequenciaDoGatewayDeFechamento.iterator().next(), projeto);
								}

							} else {
								proximaSentenca = registrarSentenca(elemento, projeto);
							}

							if (proximaSentenca != null && sentenca.getNumero() > 0
									&& !isRelacaoJaMapeada(sentenca, proximaSentenca)) {
								RelacaoSentencas relacaoSentencas = new RelacaoSentencas(sentenca, proximaSentenca);

								if (proximosElementos.size() > 1) {
									relacaoSentencas.setNomeOpcao(proximaSentenca.getComplementos());
								}

								new DAO<RelacaoSentencas>(RelacaoSentencas.class).adiciona(relacaoSentencas);
							}

						}
					}

				}

				// Caso o elemento já tenha sido mapeado, basta "pegar" no banco de dados
			} else {

				sentenca = new DAO<Sentenca>(Sentenca.class).buscaPorId(getIdDaSentencaJaMapeada(elementoPai.getId()));
				sentenca.setTemLoop(true);
				new DAO<Sentenca>(Sentenca.class).atualiza(sentenca);

			}

			return sentenca;

		} else {

			return null;

		}

	}

	private static boolean isRelacaoJaMapeada(Sentenca sentenca, Sentenca proximaSentenca) {

		List<RelacaoSentencas> todasAsRelacoesDaSentenca = sentenca.getSentencasFilhas();

		if (todasAsRelacoesDaSentenca == null || todasAsRelacoesDaSentenca.size() == 0) {
			return false;
		}

		for (RelacaoSentencas relacaoSentencas : todasAsRelacoesDaSentenca) {
			if (relacaoSentencas.getSentencaFilha().getNumero() == proximaSentenca.getNumero()) {
				return true;
			}
		}

		return false;
	}

	private static String getSujeito(ElementoBpmn elemento) {

		String tipoDeElemento = elemento.getTipo().toLowerCase();

		String sujeito = "";

		// Se for evento, o sujeito é o nome do processo
		if (tipoDeElemento.contains("event") && (tipoDeElemento.contains("start") || tipoDeElemento.contains("end"))) {
			ModelElementType poolType = modelInstance.getModel().getType(Participant.class);
			Collection<ModelElementInstance> poolInstances = modelInstance.getModelElementsByType(poolType);

			Participant pool = (Participant) poolInstances.iterator().next();
			sujeito = pool.getName();

		}

		// Se for atividade:
		// O sujeito da sentença é definido pelo texto descritivo da "raia ou lane" onde
		// o elemento está contido.
		if (tipoDeElemento.contains("task")) {

			ModelElementType laneType = modelInstance.getModel().getType(Lane.class);
			Collection<ModelElementInstance> laneInstances = modelInstance.getModelElementsByType(laneType);

			for (ModelElementInstance instance : laneInstances) {
				Lane lane = (Lane) instance;
				for (Object elementoDaRaia : lane.getFlowNodeRefs()) {
					try {
						FlowNode flowNode = (FlowNode) elementoDaRaia;
						if (elemento.getId().equals(flowNode.getId())) {
							if (lane.getName() == null || lane.getName().length() == 0) {
								contagemDeLanesSemNome++;
								lane.setName("Personagem " + String.format("%02d", contagemDeLanesSemNome));
							}
							sujeito = lane.getName();
							break;
						}
					} catch (Exception e) {
						ModelElementInstance modelElementInstance = (ModelElementInstance) elementoDaRaia;
						if (elemento.getId().equals(modelElementInstance.getDomElement().getAttribute("id"))) {
							if (lane.getName() == null || lane.getName().length() == 0) {
								contagemDeLanesSemNome++;
								lane.setName("Personagem " + String.format("%02d", contagemDeLanesSemNome));
							}
							sujeito = lane.getName();
							break;
						}
					}
				}
			}

		}

		return sujeito;
	}

	private static String getVerbo(ElementoBpmn elemento, String sujeito) {

		// A definição do atributo "verbo" da Sentença é feita de acordo com o tipo de
		// elemento BPMN que gerou a sentença, com o apoio da clase VerboSugeridoEnum.

		String tipoDoElemento = elemento.getTipo().toLowerCase();

		if (tipoDoElemento.contains("event")) {

			if (tipoDoElemento.contains("start")) {
				return VerboSugeridoEnum.VERBO_EVENTO_INICIAL.getVerbo();
			} else if (tipoDoElemento.contains("end")) {
				return VerboSugeridoEnum.VERBO_EVENTO_FINAL.getVerbo();
			} else {
				return VerboSugeridoEnum.VERBO_EVENTO_GENERICO.getVerbo();
			}

		}

		if (tipoDoElemento.contains("gateway")) {

			if (tipoDoElemento.contains("parallel")) {
				return VerboSugeridoEnum.VERBO_GATEWAY_PARALELO.getVerbo();
			} else {
				return VerboSugeridoEnum.VERBO_GATEWAY_EXCLUSIVO.getVerbo();
			}

		}

		if (tipoDoElemento.contains("task") || tipoDoElemento.contains("subprocess")) {

			if (isPlural(sujeito)) {
				return VerboSugeridoEnum.VERBO_ATIVIDADE_PLURAL.getVerbo();
			} else {
				return VerboSugeridoEnum.VERBO_ATIVIDADE_SINGULAR.getVerbo();
			}

		}

		return "";

	}

	public static String definirComplementos(String complemento01, String conectivo01, String complemento02,
			String conectivo02, String complemento03) {

		String todosOsComplementos = "";

		try {

			if (complemento01.length() > 0) {

				todosOsComplementos = complemento01;

				if (complemento02.length() > 0) {
					todosOsComplementos = todosOsComplementos + " " + conectivo01 + " " + complemento02;
				}

				if (complemento03.length() > 0) {
					todosOsComplementos = todosOsComplementos + " " + conectivo02 + " " + complemento03;
				}

			} else if (complemento02.length() > 0) {

				todosOsComplementos = complemento02;

				if (complemento03.length() > 0) {
					todosOsComplementos = todosOsComplementos + " " + conectivo02 + " " + complemento03;
				}

			} else if (complemento03.length() > 0) {
				todosOsComplementos = complemento03;
			}

		} catch (Exception e) {
			todosOsComplementos = null;
		}

		return todosOsComplementos;

	}

	private static String getRecurso(ElementoBpmn elemento, Class<? extends ModelElementInstance> tipo) {

		// Os elementos que estão associados a um recurso podem ser conectados a ele
		// através de um objeto da classe DataInputAssociation ou DataOutpuAssociation

		ModelElementType dataType = modelInstance.getModel().getType(tipo);
		Class<? extends ModelElementInstance> classeSourceOrTarget;

		Collection<ModelElementInstance> dataTypeList;
		if (elemento.getFlowNode() != null) {
			dataTypeList = elemento.getFlowNode().getChildElementsByType(dataType);
		} else {
			dataTypeList = elemento.getModelElementInstance().getChildElementsByType(dataType);
		}

		// A depender do tipo de associação com o elemento, a classe que contém o
		// elemento vai ser uma diferente.
		if (tipo.getName().contains("Input")) {
			classeSourceOrTarget = SourceRef.class;
		} else {
			classeSourceOrTarget = TargetRef.class;
		}

		String texto = "";

		// É necessário varrer todo o modelo, olhando todas as associações com recursos,
		// para identificar aquelas que estão associadas com o elemento em questão.
		for (ModelElementInstance dataElement : dataTypeList) {
			ModelElementType refType = modelInstance.getModel().getType(classeSourceOrTarget);
			Collection<ModelElementInstance> refTypeList = dataElement.getChildElementsByType(refType);

			for (ModelElementInstance ref : refTypeList) {
				ModelElementInstance recurso = modelInstance.getModelElementById(ref.getDomElement().getTextContent());

				if (recurso != null) {
					if (texto.length() > 0) {
						texto = texto + " e " + recurso.getDomElement().getAttribute("name");
					} else {
						texto = recurso.getDomElement().getAttribute("name");
					}
				}
			}

		}

		return texto;

	}

	private static boolean isPlural(String sujeito) {
		if (sujeito.length() > 1) {
			return sujeito.substring(sujeito.length() - 1).equals("s");
		} else {
			return false;
		}
	}

	private static Collection<ElementoBpmn> getProximosElementoDoFluxo(ElementoBpmn elementoOrigem) {

		Collection<ElementoBpmn> proximosElementoDoFluxo = new ArrayList<ElementoBpmn>();

		if (elementoOrigem.getFlowNode() != null) {

			for (SequenceFlow sequenceFlow : elementoOrigem.getFlowNode().getOutgoing()) {

				try {

					FlowNode flowNode = (FlowNode) sequenceFlow.getTarget();
					ElementoBpmn elemento = new ElementoBpmn(flowNode);

					proximosElementoDoFluxo.add(elemento);

				} catch (Exception e) {

					String target = sequenceFlow.getAttributeValue("targetRef");
					ModelElementInstance modelElementInstance = modelInstance.getModelElementById(target);
					ElementoBpmn elemento = new ElementoBpmn(modelElementInstance);

					proximosElementoDoFluxo.add(elemento);

				}

			}

		} else {

			ModelElementType outgoingType = modelInstance.getModel().getType(Outgoing.class);
			Collection<ModelElementInstance> outgoingList = elementoOrigem.getModelElementInstance()
					.getChildElementsByType(outgoingType);

			for (ModelElementInstance modelElementInstance : outgoingList) {

				String sequenceFlowId = modelElementInstance.getTextContent();
				ModelElementInstance sequenceFlow = modelInstance.getModelElementById(sequenceFlowId);
				String target = sequenceFlow.getAttributeValue("targetRef");

				ElementoBpmn elemento = new ElementoBpmn(modelInstance.getModelElementById(target));

				proximosElementoDoFluxo.add(elemento);

			}

		}

		// Tratamento para eventos de borda: Verificar se há eventos de borda associados
		// com a atividade
		if (getTipoDeElementoBpmn(elementoOrigem).equals(TipoDeElementoEnum.ATIVIDADE.getTipo())) {

			ModelElementType boundaryEventType = modelInstance.getModel().getType(BoundaryEvent.class);
			Collection<ModelElementInstance> boundaryEventInstances = modelInstance
					.getModelElementsByType(boundaryEventType);

			for (ModelElementInstance boundaryEvent : boundaryEventInstances) {

				String attachedToRef = boundaryEvent.getAttributeValue("attachedToRef");
				if (attachedToRef.equals(elementoOrigem.getId())) {

					ElementoBpmn elemento = new ElementoBpmn(boundaryEvent);

					proximosElementoDoFluxo.add(elemento);
				}
			}

		}

		return proximosElementoDoFluxo;

	}

	private static boolean isElementoJaMapeado(String identificador) {
		if (listaDeElementosMapeados == null || listaDeElementosMapeados.isEmpty()) {
			return false;
		}
		for (String idElemento : listaDeElementosMapeados) {
			if (idElemento.equals(identificador)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isElementoGatewayDeFechamento(ElementoBpmn elemento) {
		return elemento.getTipo().toLowerCase().contains("gateway")
				&& elemento.getTipo().toLowerCase().contains("parallel")
				&& getProximosElementoDoFluxo(elemento).size() <= 1;
	}

	private static int getIdDaSentencaJaMapeada(String idDoElementoBpmn) {
		return correspondenciaEntreIdDaSentencaEIdDoBpmn.get(idDoElementoBpmn);
	}

	private static String getTipoDeElementoBpmn(ElementoBpmn elemento) {

		// O atributo "tipoDeElementoBPMN" da classe Sentença, é definido a partir do
		// tipo de elemento do próprio modelo, porém são considerados apenas os que são
		// do tipo Evento, Atividade ou Gateway.
		String tipoDeElemento = elemento.getTipo().toLowerCase();

		if (tipoDeElemento.contains("event")) {
			if (tipoDeElemento.contains("start")) {
				return TipoDeElementoEnum.EVENTO_INICIAL.getTipo();
			}
			if (tipoDeElemento.contains("intermediate")) {
				return TipoDeElementoEnum.EVENTO_INTERMEDIARIO.getTipo();
			}
			if (tipoDeElemento.contains("boundary")) {
				return TipoDeElementoEnum.EVENTO_BORDA.getTipo();
			}
			return TipoDeElementoEnum.EVENTO_FINAL.getTipo();
		}

		if (tipoDeElemento.contains("task") || tipoDeElemento.contains("subprocess")) {
			return TipoDeElementoEnum.ATIVIDADE.getTipo();
		}

		if (tipoDeElemento.contains("gateway")) {
			if (tipoDeElemento.contains("exclusive")) {
				return TipoDeElementoEnum.GATEWAY_EXCLUSIVO.getTipo();
			}
			if (tipoDeElemento.contains("parallel")) {
				return TipoDeElementoEnum.GATEWAY_PARALELO.getTipo();
			}
			return TipoDeElementoEnum.GATEWAY_EXCLUSIVO.getTipo();
		}
		return "";
	}

}
