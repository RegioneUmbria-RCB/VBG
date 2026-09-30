package it.gruppoinit.pal.gp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerChiusuraAttivitaScadute {

    private static final Logger log = LoggerFactory.getLogger(LoggerChiusuraAttivitaScadute.class);

    public static void logDebug(String message, String[] arg) {

	log.debug(message, arg);
    }

    public static void logInfo(String message, String[] arg) {

	log.info(message, arg);
    }

    public static void logDebug(String message) {

	log.debug(message);
    }

    public static void logInfo(String message) {

	log.info(message);
    }
}
