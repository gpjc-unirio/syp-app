package br.unirio.jdbpn.narrativas.util;

public enum VerboSugeridoEnum {

	VERBO_EVENTO_INICIAL("começa com"), VERBO_EVENTO_GENERICO("Então tem"), VERBO_EVENTO_FINAL("termina com"),
	VERBO_EVENTO_FINAL_PLURAL("terminam com"), VERBO_ATIVIDADE_SINGULAR("precisa"), VERBO_ATIVIDADE_PLURAL("precisam"),
	VERBO_GATEWAY_EXCLUSIVO("Decide-se entre"), VERBO_GATEWAY_PARALELO("Executa-se");

	private final String verbo;

	VerboSugeridoEnum(String verbo) {
		this.verbo = verbo;
	}

	public String getVerbo() {
		return verbo;
	}

}
