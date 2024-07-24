package br.unirio.jdbpn.narrativas.util;

public enum StatusProjetoEnum {

	STATUS_EM_ELABORACAO("Em Elaboração"),
	STATUS_AVALIACAO_SENTENCAS("Pré-Roteiro"),
	STATUS_ROTEIRO_ELABORADO("Roteiro Elaborado"),
	STATUS_JORNADA_DO_HEROI("Roteiro Elaborado com Jornada do Herói"),
	STATUS_CANCELADO("Cancelado");
	
	private final String status;
	StatusProjetoEnum(String statusOpcao) {
		status = statusOpcao;
	}
	
	public String getStatus() {
		return status;
	}
	
}
