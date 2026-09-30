package it.gruppoinit.pal.gp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerArchiviazioneDocumentiDaFileSystem {

    private static final Logger log = LoggerFactory.getLogger(LoggerArchiviazioneDocumentiDaFileSystem.class);

    public static void log(String message) {

	log.debug(message);
    }

    public static void logArchiviazioneDocumentiDaFileSystemErrori(String s) {

	String _ARCHIVIAZIONE_FILE_MESSAGGIO_ERRORI = "Data ''{0}'' errori archiviazione\n ''{1}''";
	String message = Utilities.formatMessage(_ARCHIVIAZIONE_FILE_MESSAGGIO_ERRORI, Utilities.getToday(true), s);
	log(message);
    }

    public static void logArchiviazioneDocumentiDaFileSystem(String s) {

	String message = Utilities.formatMessage(ARCHIVIAZIONE_FILE_MESSAGGIO, Utilities.getToday(true), s);
	log(message);
    }

    private static final String ARCHIVIAZIONE_FILE_MESSAGGIO = "Data ''{0}'' dettaglio archiviazione  ''{1}''";
    private static final String ARCHIVIAZIONE_FILE_MESSAGGIO_ERRORI = "Data ''{0}'' errori durante l'archiviazione ''{1}''";
    private static final String ALLINEAMENTO_STRADARIO_MESSAGGIO_AGGIORNAMENTO_OK = "''{0}'' : Aggiornati  ''{1}''. Inseriti ''{2}'' ";
    private static final String ALLINEAMENTO_STRADARIO_MESSAGGIO_AGGIORNAMENTO_KO = "''{0}'' : Errori :  ''{1}''.";
}
