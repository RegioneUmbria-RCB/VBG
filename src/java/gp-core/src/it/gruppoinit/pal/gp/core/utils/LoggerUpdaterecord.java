package it.gruppoinit.pal.gp.core.utils;

public class LoggerUpdaterecord {

    public enum TIPO_OPERAZIONE {
	INSERIMENTO, //
	AGGIORNAMENTO, //
	CANCELLAZIONE
    }

    private LoggerUpdaterecord() {

	super();
    }

    public static void log(String message) {

	LoggerCancellazioni.log.error(message);
    }

    public static void log(String message, Object utente) {

	String nmessage = Utilities.formatMessage("in data ''{0}'' l''utente ''{1}'' ha compiuto la seguente operazione: " + message,
		Utilities.getToday(true), utente);
	LoggerCancellazioni.log.error(nmessage);
    }

    public static void logUpdatePIAnagrafica(String responsabile, String PINuova, String PIVecchia, String anagrafica, Integer codiceanagrafica) {

	String message = Utilities.formatMessage(UPDATE_PI_ANAGRAFICA, Utilities.getToday(true), responsabile, anagrafica, codiceanagrafica,
		PIVecchia, PINuova);
	log(message);
    }

    public static void logUpdateCFAnagrafica(String responsabile, String anagrafica, Integer codiceanagrafica, String CFNuovo, String CFVecchio) {

	String message = Utilities.formatMessage(UPDATE_CF_ANAGRAFICA, Utilities.getToday(true), responsabile, anagrafica, codiceanagrafica,
		CFVecchio, CFNuovo);
	log(message);
    }

    private static final String UPDATE_CF_ANAGRAFICA = "In data ''{0}'' l'' utente ''{1}'' ha modificato il CF dell''anagrafica  {2} [{3}] da ''{4}'' a ''{5}''";
    private static final String UPDATE_PI_ANAGRAFICA = "In data ''{0}'' l'' utente ''{1}'' ha modificato la PI dell''anagrafica {2} [{3}] da ''{4}'' a ''{5}''";
}
