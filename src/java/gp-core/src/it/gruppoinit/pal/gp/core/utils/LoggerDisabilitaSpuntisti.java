package it.gruppoinit.pal.gp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerDisabilitaSpuntisti {

    private static final Logger log = LoggerFactory.getLogger(LoggerDisabilitaSpuntisti.class);
    public static String _DISABILITA_SPUNTISTA_FIERE = "In data ''{0}'' operatatore ''{1}'' ha disabilitato gli spuntisti per termine fiera ''{2}'' del giorno ''{3}''";
    public static String _DISABILITA_SPUNTISTA_MERCATI = "In data ''{0}'' operatatore ''{1}'' ha disabilitato gli spuntisti per assenza sul mercato ''{2}'' del giorno ''{3}''";

    public static void logInfo(String message) {

	log.info(message);
    }

    public static void logDisabilitaSpuntistiMercatiAndFiere(String messaggio, String operatore, String mercato, String giorno) {

	String message = Utilities.formatMessage(_DISABILITA_SPUNTISTA_FIERE, Utilities.getToday(true), operatore, mercato, giorno);
	logInfo(message);
    }

    public static void logDisabilitaSpuntistiMercatiAndFiereJob(String messaggio, String mercato, String giorno) {

	String message = Utilities.formatMessage(messaggio, Utilities.getToday(true), mercato, giorno);
	logInfo(message);
    }

    public static void logDisabilitaSpuntistiMercatiAndFiereJob(String messaggio) {

	String message = Utilities.formatMessage(messaggio, Utilities.getToday(true));
	logInfo(message);
    }
}
