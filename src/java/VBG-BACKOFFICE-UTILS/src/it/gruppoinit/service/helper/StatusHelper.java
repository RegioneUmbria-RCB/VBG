package it.gruppoinit.service.helper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusHelper {

    private static final Logger activityLog = LoggerFactory.getLogger("registrazione_attivita");
    public static Map<String, String> mapAttivita = new HashMap<String, String>();

    public static boolean checkOperazioneInserita(String idOperazione) {

	return mapAttivita.get(StringUtils.defaultString(idOperazione)) != null;
    }

    public static String attivita(String idOperazione) {

	String att = mapAttivita.get(StringUtils.defaultString(idOperazione));
	if (att == null) {
	    att = new String();
	}
	return att;
    }

    public static void eliminaOperazione(String idOperazione) {

	mapAttivita.remove(idOperazione);
    }

    public static void aggiungiMessaggio(String idOperazione, String messaggio) {

	mapAttivita.put(idOperazione, messaggio);
	activityLog.info(messaggio);
    }
}
