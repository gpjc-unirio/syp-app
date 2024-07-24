package br.unirio.jdbpn.narrativas.util;

import java.util.ArrayList;
import java.util.List;

public enum PerfilUsuarioEnum {

	PERFIL_CADASTRO_COMPLETO("Cadastro Completo"), PERFIL_SEM_SUPORTE("Sem Suporte"),
	PERFIL_ADMINISTRADOR("Administrador");

	private final String perfil;

	PerfilUsuarioEnum(String perfilUsuario) {
		perfil = perfilUsuario;
	}

	public String getPerfil() {
		return perfil;
	}

	public static List<String> getPerfis() {
		List<String> perfis = new ArrayList<String>();
		PerfilUsuarioEnum[] perfilUsuarioEnumArray = PerfilUsuarioEnum.values();
		for (PerfilUsuarioEnum perfil : perfilUsuarioEnumArray) {
			perfis.add(perfil.getPerfil());
		}
		return perfis;
	}

}
