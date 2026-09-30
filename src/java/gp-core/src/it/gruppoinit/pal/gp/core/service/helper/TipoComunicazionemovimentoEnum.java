package it.gruppoinit.pal.gp.core.service.helper;

public enum TipoComunicazionemovimentoEnum {
    FIRMA_DOC("FIRMA_DOC"), INSER_MOV("INSERIMENTO_MOV");

    private String value;

    private TipoComunicazionemovimentoEnum(String s) {

	value = s;
    }

    public String getValue() {

	return value;
    }
}
