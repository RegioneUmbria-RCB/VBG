package it.gruppoinit.pal.gp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerModificheIstanze {

    protected static final Logger log = LoggerFactory.getLogger("it.gruppoinit.auditing.dml_activity");

    public static void log(String message) {

	log.error(message);
    }

    public static void log(String message, Object[] params) {

	log.error(message, params);
    }

    public static void logMofificaIstruttoreAssegnato(String responsabile, String istruttoreSostituito, String nuovoIstruttore,
	    String descrizioneIstanza) {

	String MODIFICA_ISTRUTTOREISTANZA_ASSEGNATO = "In data ''{0}'' l'' operatore ''{1}'' ha sostituito il responsabile istruttore ''{2}'' con l''istruttore ''{3}'' per l''istanza ''{4}''";
	String message = Utilities.formatMessage(MODIFICA_ISTRUTTOREISTANZA_ASSEGNATO, Utilities.getToday(true), responsabile, istruttoreSostituito,
		nuovoIstruttore, descrizioneIstanza);
	log(message);
    }
    //private static final String MODIFICA_ISTRUTTOREISTANZA_ASSEGNATO = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato il {2} ''{3}'' dell''istanza ''{4}''";
}
