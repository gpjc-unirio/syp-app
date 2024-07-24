package br.unirio.jdbpn.narrativas.util;

public enum LogInfoEnum {

	ACAO_LOGIN("Login"), ACAO_CRIAR_PROJETO("Criar projeto"), ACAO_IMPORTAR_DIAGRAMA_BPMN("Importar diagrama BPMN"),
	ACAO_AJUSTAR_SENTENCAS("Ajustar sentenças"), ACAO_CRIAR_CENA("Criar / editar cena"),
	ACAO_CRIAR_PERSONAGEM("Criar / editar personagem"), ACAO_CRIAR_ROTEIRO("Criar roteiro sem suporte"),

	TIPO_ENTRADA("Entrada"), TIPO_SAIDA("Saída");

	private final String nome;

	LogInfoEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
