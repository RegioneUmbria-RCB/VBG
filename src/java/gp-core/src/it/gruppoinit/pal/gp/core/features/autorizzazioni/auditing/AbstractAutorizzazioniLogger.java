package it.gruppoinit.pal.gp.core.features.autorizzazioni.auditing;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class AbstractAutorizzazioniLogger implements IAutorizzazioniLogger {

    private static final String GUID_OPERAZIONE = "GUID: ";
    private static final String UTENTE_CHE_EFFETTUA_L_OPERAZIONE = "Utente che effettua l'operazione: ";
    protected static final String DELIMITATORE_LOG = "=========================================================";
    public static final Logger logger = LoggerFactory.getLogger("autorizzazioni");
    private String guidOperazione;
    protected String messaggio;
    protected String autore;

    public AbstractAutorizzazioniLogger(String autore) {

	this.autore = autore;
	this.guidOperazione = UUID.randomUUID().toString();
    }

    @Override
    public void log() {

	StringBuilder sb = new StringBuilder();
	sb.append(DELIMITATORE_LOG);
	sb.append("\n").append(GUID_OPERAZIONE).append(this.guidOperazione);
	sb.append("\n").append(UTENTE_CHE_EFFETTUA_L_OPERAZIONE).append(autore).append(getIdComuneAndSoftwareString());
	sb.append("\n");
	sb.append(messaggio);
	sb.append("\n");
	logger.info(sb.toString());
    }

    @Override
    public void logFineMetodo() {

	StringBuilder sb = new StringBuilder();
	sb.append(DELIMITATORE_LOG);
	sb.append("\n").append(GUID_OPERAZIONE).append(this.guidOperazione);
	sb.append("\n").append(UTENTE_CHE_EFFETTUA_L_OPERAZIONE).append(autore).append(getIdComuneAndSoftwareString());
	sb.append("\n").append("Fine");
	sb.append("\n").append(DELIMITATORE_LOG);
	sb.append("\n");
	logger.info(sb.toString());
    }

    @Override
    public void logError(String messaggio) {

	StringBuilder sb = new StringBuilder();
	sb.append(DELIMITATORE_LOG);
	sb.append("\n").append(GUID_OPERAZIONE).append(this.guidOperazione);
	sb.append("\n").append(UTENTE_CHE_EFFETTUA_L_OPERAZIONE).append(autore).append(getIdComuneAndSoftwareString());
	sb.append("\n").append("ERRORE");
	sb.append("\n").append(messaggio);
	sb.append("\n").append(DELIMITATORE_LOG);
	sb.append("\n");
	logger.info(sb.toString());
    }

    @Override
    public void logError(String messaggio, Throwable e) {

	StringBuilder sb = new StringBuilder();
	sb.append(DELIMITATORE_LOG);
	sb.append("\n").append(GUID_OPERAZIONE).append(this.guidOperazione);
	sb.append("\n").append(UTENTE_CHE_EFFETTUA_L_OPERAZIONE).append(autore).append(getIdComuneAndSoftwareString());
	sb.append("\n").append("ERRORE");
	sb.append("\n").append(messaggio);
	sb.append("\n").append(stakTrace(e));
	sb.append("\n").append(DELIMITATORE_LOG);
	sb.append("\n");
	logger.info(sb.toString());
    }

    private String stakTrace(Throwable e) {

	if (e == null) {
	    return "";
	}
	StackTraceElement[] stackTrace = e.getStackTrace();
	if (stackTrace == null || stackTrace.length == 0) {
	    return "";
	}
	StringBuilder sb = new StringBuilder("Stack trace dell'errore:\n");
	for (StackTraceElement stackTraceElement : stackTrace) {
	    if (stackTraceElement != null) {
		sb.append(stackTraceElement.toString()).append("\n");
	    }
	}
	return sb.toString();
    }

    private String getIdComuneAndSoftwareString() {

	return "[" + ORMHelper.getIdcomune() + "-" + ORMHelper.getSoftware() + "]";
    }
}
