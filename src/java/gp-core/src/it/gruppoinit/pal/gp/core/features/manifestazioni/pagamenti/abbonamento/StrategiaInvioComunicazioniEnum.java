package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import org.apache.commons.lang.StringUtils;

public enum StrategiaInvioComunicazioniEnum {

    CHIUSURA_GIORNATA("CHIUSURA_GIORNATA"),
    APERTURA_POS_CREDITO_INSUFFICIENTE("APERTURA_POS_CREDITO_INSUFFICIENTE"),
    NESSUNA("NESSUNA");

    private final String valore;

    StrategiaInvioComunicazioniEnum(String valore) {

	this.valore = valore;
    }

    public static StrategiaInvioComunicazioniEnum fromValue(String valore) {

	if (StringUtils.isBlank(valore)) {
	    return StrategiaInvioComunicazioniEnum.fromDefault();
	}
	for (StrategiaInvioComunicazioniEnum c : StrategiaInvioComunicazioniEnum.values()) {
	    if (c.valore.equals(valore)) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Nessun enumeratore trovato per il valore " + valore);
    }

    public static StrategiaInvioComunicazioniEnum fromDefault() {

	return StrategiaInvioComunicazioniEnum.CHIUSURA_GIORNATA;
    }
}
