package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.Map;
import java.util.TreeMap;

public enum DataInizioRateizzazioneEnum {

    DATA_VALIDITA_ISTANZA(1, "Data validità istanza"),
    DATA_ODIERNA(2, "Data odierna"),
    DATA_MOVIMENTO(3, "Data del movimento"),
    DATA_ISTANZA(4, "Data dell'istanza"),
    DATA_PROTOCOLLO(5, "Data del protocollo");

    private final int valore;
    private final String descrizione;

    DataInizioRateizzazioneEnum(int valore, String descrizione) {

	this.valore = valore;
	this.descrizione = descrizione;
    }

    public int getValore() {

	return this.valore;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public static Map<Integer, String> toMap() {

	TreeMap<Integer, String> elenco = new TreeMap<Integer, String>();
	for (DataInizioRateizzazioneEnum c : DataInizioRateizzazioneEnum.values()) {
	    elenco.put(c.valore, c.descrizione);
	}
	return elenco;
    }

    public static DataInizioRateizzazioneEnum fromValue(Integer valore) {

	for (DataInizioRateizzazioneEnum c : DataInizioRateizzazioneEnum.values()) {
	    if (c.valore == valore) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Valore non ammesso: " + valore);
    }
}
