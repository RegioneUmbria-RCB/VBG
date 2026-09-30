package it.gruppoinit.pal.gp.core.service.helper;

public enum ComunicazioniTStatoEnum {
    PRE_ELABORAZIONE(0), DA_ELABORARE(1), ELABORATA_CON_ERRORI(-1), ELABORATA_OK(2);

    private Integer value;

    private ComunicazioniTStatoEnum(Integer v) {

	value = v;
    }

    public Integer value() {

	return value;
    }
}
