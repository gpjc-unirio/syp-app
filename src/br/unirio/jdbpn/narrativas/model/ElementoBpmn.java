package br.unirio.jdbpn.narrativas.model;

import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

public class ElementoBpmn {

	private FlowNode flowNode;
	private ModelElementInstance modelElementInstance;
	private String tipo;
	private String nome;
	private String id;

	public ElementoBpmn() {

	}

	public ElementoBpmn(ModelElementInstance elemento) {
		this.modelElementInstance = elemento;
		this.tipo = elemento.getElementType().getTypeName();
		this.nome = elemento.getDomElement().getAttribute("name");
		this.id = elemento.getDomElement().getAttribute("id");
	}

	public ElementoBpmn(FlowNode elemento) {
		this.flowNode = elemento;
		this.tipo = elemento.getElementType().getTypeName();
		this.nome = elemento.getName();
		this.id = elemento.getId();
	}

	public FlowNode getFlowNode() {
		return flowNode;
	}

	public void setFlowNode(FlowNode flowNode) {
		this.flowNode = flowNode;
	}

	public ModelElementInstance getModelElementInstance() {
		return modelElementInstance;
	}

	public void setModelElementInstance(ModelElementInstance modelElementInstance) {
		this.modelElementInstance = modelElementInstance;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
