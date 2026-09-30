package it.gruppoinit.pal.gp.core.service.helper;

public enum TipoComunicazioniTEnum {
    CONCESSIONI("CONCESSIONI");

    private String value;

    private TipoComunicazioniTEnum(String v) {

	value = v;
    }

    public String value() {

	return value;
    }
}
