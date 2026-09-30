package it.gruppoinit.pal.gp.core.service.helper;

public enum ComunicazioniDStatoEnum {
    NON_INIZIALIZZATA(0), INIZIALIZZATA(1), ELABORATA_CON_ERRORI(-1), ELABORATA_TERMINATA(2);

    private Integer value;

    private ComunicazioniDStatoEnum(Integer v) {

	value = v;
    }

    public Integer value() {

	return value;
    }
}
