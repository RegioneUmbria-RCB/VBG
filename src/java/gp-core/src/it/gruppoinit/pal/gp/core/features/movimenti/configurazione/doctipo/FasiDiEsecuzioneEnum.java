package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public enum FasiDiEsecuzioneEnum {

    PRIMA_DELLA_PROTOCOLLAZIONE("Prima della protocollazione"),
    DOPO_LA_PROTOCOLLAZIONE("Dopo la protocollazione");

    private final String descrizione;

    FasiDiEsecuzioneEnum(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public static Map<String, String> toMap() {

	TreeMap<String, String> elenco = new TreeMap<String, String>();
	for (FasiDiEsecuzioneEnum c : FasiDiEsecuzioneEnum.values()) {
	    elenco.put(c.toString(), c.descrizione);
	}
	return elenco;
    }

    public static FasiDiEsecuzioneEnum fromValue(String value) {

	if (StringUtils.isBlank(value)) {
	    return null;
	}
	for (FasiDiEsecuzioneEnum c : FasiDiEsecuzioneEnum.values()) {
	    if (c.toString().equalsIgnoreCase(value)) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Valore non ammesso: " + value);
    }
}
