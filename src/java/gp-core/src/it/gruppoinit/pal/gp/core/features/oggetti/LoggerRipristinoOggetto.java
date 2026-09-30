package it.gruppoinit.pal.gp.core.features.oggetti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class LoggerRipristinoOggetto {

    protected static final Logger log = LoggerFactory.getLogger("features.oggetti");

    public static void log(String message) {

	log.error(message);
    }

    public static void log(String message, Responsabili responsabile) {

	String nmessage = Utilities.formatMessage("in data ''{0}'' l''operatore ''{1}'' ha compiuto la seguente operazione: " + message,
		Utilities.getToday(true), responsabile);
	log.error(nmessage);
    }
}
