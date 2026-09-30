package it.gruppoinit.pal.gp.core.utils;

import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAllineamentostradario {

    private static final Logger log = LoggerFactory.getLogger(LoggerAllineamentostradario.class);

    public static void log(String message) {

	log.debug(message);
    }

    public static void logAllineamentoStradario(String responsabile, List<Comuni> listComuni) {

	StringBuffer buffer = new StringBuffer();
	for (Comuni comune : listComuni) {
	    buffer = buffer.append(comune.getComune()).append(", ");
	}
	String comuni = buffer.substring(0, (buffer.length() - 2));
	String message = Utilities.formatMessage(ALLINEAMENTO_STRADARIO_MANUALE, Utilities.getToday(true), responsabile, comuni);
	log(message);
    }

    public static void logAllineamentoStradario(List<Comuni> listComuni) {

	StringBuffer buffer = new StringBuffer();
	for (Comuni comune : listComuni) {
	    buffer = buffer.append(comune.getComune()).append(", ");
	}
	String comuni = buffer.substring(0, (buffer.length() - 1));
	String message = Utilities.formatMessage(ALLINEAMENTO_STRADARIO_AUTOMATICO, Utilities.getToday(true), comuni);
	log(message);
    }

    private static final String ALLINEAMENTO_STRADARIO_MANUALE = "In data ''{0}'' l'' operatore ''{1}'' ha effettualo l''allineamento dello stradario per i comuni  ''{2}''";
    private static final String ALLINEAMENTO_STRADARIO_AUTOMATICO = "In data ''{0}'' il processo automatico ha effettualo l'allineamento dello stradario per i comuni  ''{1}''";
}
