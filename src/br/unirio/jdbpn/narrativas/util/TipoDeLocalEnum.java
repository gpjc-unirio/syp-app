package br.unirio.jdbpn.narrativas.util;

import java.util.ArrayList;
import java.util.List;

public enum TipoDeLocalEnum {

	Externo("Ext."), Interno("Int.");

	private final String abreviado;

	TipoDeLocalEnum(String abreviado) {
		this.abreviado = abreviado;
	}

	public String getAbreviado() {
		return abreviado;
	}

	public static List<String> getTipos() {

		List<String> tipos = new ArrayList<String>();
		TipoDeLocalEnum[] tiposArray = TipoDeLocalEnum.values();

		for (TipoDeLocalEnum tipoDeLocalEnum : tiposArray) {
			tipos.add(tipoDeLocalEnum.toString());
		}

		return tipos;

	}

}
