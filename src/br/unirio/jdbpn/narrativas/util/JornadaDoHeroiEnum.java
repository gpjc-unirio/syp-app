package br.unirio.jdbpn.narrativas.util;

public enum JornadaDoHeroiEnum {
	mundoComum("Mundo Comum"), chamadoAAventura("Chamado à Aventura"), recusaDoChamado("Recusa do Chamado"),
	encontroComOMentor("Encontro com o Mentor"), travessiaDoPrimeiroLimiar("Travessia do Primeiro Limiar"),
	provasAliadosEInimigos("Provas, Aliados e Inimigos"), aproximacaoDaCavernaSecreta("Aproximação da Caverna Secreta"),
	provacao("Provação"), recompensaEmpunhandoAEspada("Recompensa (Empunhando a Espada)"),
	oCaminhoDeVolta("O Caminho de Volta"), ressurreicao("Ressurreição"), retornoComOElixir("Retorno com o Elixir");

	private final String nome;

	JornadaDoHeroiEnum(String estagio) {
		nome = estagio;
	}

	public String getNome() {
		return nome;
	}
}
