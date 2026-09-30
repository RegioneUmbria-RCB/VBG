package it.gruppoinit.pal.gp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerArchiviBackoffice {

    private static final Logger log = LoggerFactory.getLogger(LoggerArchiviBackoffice.class);

    public static void log(String message) {

	log.debug(message);
    }

    public static void log(String message, Throwable e) {

	log.debug(message, e);
    }

    public static void logArchiviBackofficeINFO(String s) {

	String m = "Data ''{0}'' " + s;
	String message = Utilities.formatMessage(s, Utilities.getToday(true), null);
	log.info(message);
    }

    public static void logArchiviBackofficeERROR(String s) {

	String m = "Data ''{0}'' " + s;
	String message = Utilities.formatMessage(s, Utilities.getToday(true), null);
	log.error(message);
    }

    public static void logArchiviBackofficeDEBUG(String s) {

	String m = "Data ''{0}'' " + s;
	String message = Utilities.formatMessage(m, Utilities.getToday(true), null);
	log.debug(message);
    }
}
