package it.gruppoinit.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerErroricomunicazioni {

    private static final Logger log = LoggerFactory.getLogger(LoggerErroricomunicazioni.class);

    public static void log(String message) {

	log.info(message);
    }

    public static void error(String message) {

	log.error(message);
    }

    public static void logComunicazioneEnteSuap(String tipocomunicazione, String codicepratica, String s) {

	String _ERRORE_COMUNICAZIONI_ENTE_SUAP = "In Data ''{0}'' errore comunicazione ENTE->SUAP tipo : ''{1}'' ,  Codicepratica : ''{2}'' , Errore: ''{3}''";
	String message = Utilities.formatMessage(_ERRORE_COMUNICAZIONI_ENTE_SUAP, Utilities.getToday(true), tipocomunicazione, codicepratica, s);
	log(message);
    }

    public static void logComunicazioneSuapEnte(String tipocomunicazione, String codicepratica, String s) {

	String _ERRORE_COMUNICAZIONI_ENTE_SUAP = "In Data ''{0}'' errore comunicazione SUAP->ENTE tipo : ''{1}'' ,  Codicepratica : ''{2}'' , Errore: ''{3}''";
	String message = Utilities.formatMessage(_ERRORE_COMUNICAZIONI_ENTE_SUAP, Utilities.getToday(true), tipocomunicazione, codicepratica, s);
	log(message);
    }
}
