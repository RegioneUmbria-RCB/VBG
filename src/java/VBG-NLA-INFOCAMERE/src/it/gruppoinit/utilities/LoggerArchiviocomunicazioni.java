package it.gruppoinit.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerArchiviocomunicazioni {

    private static final Logger log = LoggerFactory.getLogger(LoggerArchiviocomunicazioni.class);

    public static void log(String message) {

	log.info(message);
    }

    public static void _log(String message, Object[] objects) {

	log.info(message, objects);
    }

    public static void error(String message) {

	log.error(message);
    }

    public static void logComunicazioneGenerico(String message, Object... objects) {

	//	String _ARCHIVIAZIONE_COMUNICAZIONI_ENTE_SUAP = "In Data ''{0}'': ''{1}''";
	//	String _message = Utilities.formatMessage(_ARCHIVIAZIONE_COMUNICAZIONI_ENTE_SUAP, Utilities.getToday(true), message);
	String _message = "In Data " + Utilities.getToday(true) + " " + message;
	_log(_message, objects);
    }

    public static void logComunicazioneEnteSuap(String tipocomunicazione, String codicepratica, String s) {

	String _ARCHIVIAZIONE_COMUNICAZIONI_ENTE_SUAP = "In Data ''{0}'' comunicazione ENTE->SUAP tipo: ''{1}'' , Codicepratica : ''{2}'' , Comunicazione : ''{3}''";
	String message = Utilities.formatMessage(_ARCHIVIAZIONE_COMUNICAZIONI_ENTE_SUAP, Utilities.getToday(true), tipocomunicazione, codicepratica,
		s);
	log(message);
    }

    public static void logComunicazioneSuapEnte(String tipocomunicazione, String codicepratica, String s) {

	String _ARCHIVIAZIONE_COMUNICAZIONI_SUAP_ENTE = "In Data ''{0}'' comunicazione SUPA->ENTE tipo: ''{1}'' ,  Codicepratica : ''{2}'' , Comunicazione : ''{3}''";
	String message = Utilities.formatMessage(_ARCHIVIAZIONE_COMUNICAZIONI_SUAP_ENTE, Utilities.getToday(true), tipocomunicazione, codicepratica,
		s);
	log(message);
    }
}
