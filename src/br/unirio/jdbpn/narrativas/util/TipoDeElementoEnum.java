package br.unirio.jdbpn.narrativas.util;

public enum TipoDeElementoEnum {

	EVENTO_INICIAL("Evento Inicial"),
	EVENTO_INTERMEDIARIO("Evento Intermediário"),
	EVENTO_FINAL("Evento Final"),
	EVENTO_BORDA("Evento de Borda"),
	ATIVIDADE("Atividade"),
	GATEWAY_EXCLUSIVO("Gateway Exclusivo"),
	GATEWAY_PARALELO("Gateway Paralelo");
	
	private final String tipo;
	TipoDeElementoEnum(String tipoDeElemento) {
		tipo = tipoDeElemento;
	}
	
	public String getTipo() {
		return tipo;
	}
	
}
