package it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento;

public enum ArrotondamentoEnum {

    STANDARD;

    public static ArrotondamentoEnum daConfigurazione(String arrotondamento) {

	if (arrotondamento == null) {
	    return null;
	}
	if (arrotondamento.equals(STANDARD.name())) {
	    return STANDARD;
	}
	return null;
    }
}
