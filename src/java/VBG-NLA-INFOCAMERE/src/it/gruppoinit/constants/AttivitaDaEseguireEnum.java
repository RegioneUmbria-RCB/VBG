package it.gruppoinit.constants;

public enum AttivitaDaEseguireEnum {
    INSERIMENTO_PRATICA("INSERIMENTO PRATICA"), INSERIMENTO_ATTIVITA("INSERIMENTO ATTIVITA"), PRATICA_REGISTRA_MA_NON_PRESENTE(
	    "PRATICA REGISTRA MA NON PRESENTE"), ATTIVITA_NON_CODIFICATA("ATTIVITA NON CODIFICATA");

    private String value;

    private AttivitaDaEseguireEnum(String s) {

	value = s;
    }

    public String getValue() {

	return value;
    }
}
