package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import org.apache.commons.lang.StringUtils;

public enum TipoRicaricaEnum {

    LIBERO("Importo libero", "Massimale"), //abbonamento.configurazione.form.ricariche.importo_massimo
    FISSO("Importo fisso", "Importo"); //abbonamento.configurazione.form.ricariche.importo_fisso

    private final String valore;
    private final String etichetta;

    public String getValore() {

	return valore;
    }

    public String getEtichetta() {

	return etichetta;
    }

    private TipoRicaricaEnum(String valore, String etichetta) {

	this.valore = valore;
	this.etichetta = etichetta;
    }

    public static TipoRicaricaEnum fromValue(String valore) {

	if (StringUtils.isBlank(valore)) {
	    return TipoRicaricaEnum.fromDefault();
	}
	for (TipoRicaricaEnum c : TipoRicaricaEnum.values()) {
	    if (c.valore.equals(valore)) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Nessun enumeratore trovato per il valore " + valore);
    }

    public static TipoRicaricaEnum fromDefault() {

	return TipoRicaricaEnum.LIBERO;
    }
}
