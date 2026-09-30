package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.Map;
import java.util.TreeMap;

public enum FrequenzaRateAmmortamentoFREnum {

    MENSILE("30", "Mensile"),
    BIMESTRALE("60", "Bimestrale"),
    TRIMESTRALE("90", "Trimestrale"),
    QUADRIMESTRALE("120", "Quadrimestrale"),
    SEMESTRALE("180", "Semestrale"),
    ANNUALE("365", "Annuale");

    private final String valore;
    private final String descrizione;

    FrequenzaRateAmmortamentoFREnum(String valore, String descrizione) {

	this.valore = valore;
	this.descrizione = descrizione;
    }

    public String value() {

	return this.valore;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public static Map<String, String> toMap() {

	TreeMap<String, String> elenco = new TreeMap<String, String>();
	for (FrequenzaRateAmmortamentoFREnum c : FrequenzaRateAmmortamentoFREnum.values()) {
	    elenco.put(c.valore, c.descrizione);
	}
	return elenco;
    }

    public static FrequenzaRateAmmortamentoFREnum fromValue(String valore) {

	for (FrequenzaRateAmmortamentoFREnum c : FrequenzaRateAmmortamentoFREnum.values()) {
	    if (c.valore == valore) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Valore non ammesso: " + valore);
    }
}
