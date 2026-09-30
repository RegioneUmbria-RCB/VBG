package it.gruppoinit.pal.gp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerRitentaProtocollazioneIstanzaFallite {

    private static final Logger log = LoggerFactory.getLogger(LoggerRitentaProtocollazioneIstanzaFallite.class);

    public static void logDebug(String message) {

	log.debug(message);
    }

    public static void logInfo(String message) {

	log.info(message);
    }

    public static void logError(String message) {

	log.error(message);
    }

    public static void logDebug(String message, Object[] objects) {

	log.debug(message, objects);
    }

    public static void logInfo(String message, Object[] objects) {

	log.info(message, objects);
    }

    public static void logError(String message, Object[] objects) {

	log.error(message, objects);
    }

    public static void logDebug(String message, Object object) {

	log.debug(message, object);
    }

    public static void logInfo(String message, Object object) {

	log.info(message, object);
    }

    public static void logError(String message, Object object) {

	log.error(message, object);
    }
}
