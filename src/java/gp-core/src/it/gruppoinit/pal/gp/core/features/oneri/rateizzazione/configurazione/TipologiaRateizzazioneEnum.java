package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.Map;
import java.util.TreeMap;

public enum TipologiaRateizzazioneEnum {

    DEFAULT("Default"),
    AMMORTAMENTO_FRANCESE("Ammortamento alla francese");

    private final String descrizione;

    TipologiaRateizzazioneEnum(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getDescrizione() {

	return this.descrizione;
    }

    public static Map<String, String> toMap() {

	TreeMap<String, String> elenco = new TreeMap<String, String>();
	for (TipologiaRateizzazioneEnum c : TipologiaRateizzazioneEnum.values()) {
	    elenco.put(c.toString(), c.descrizione);
	}
	return elenco;
    }

    public static TipologiaRateizzazioneEnum fromValue(String v) {

	for (TipologiaRateizzazioneEnum c : TipologiaRateizzazioneEnum.values()) {
	    if (c.toString().equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
