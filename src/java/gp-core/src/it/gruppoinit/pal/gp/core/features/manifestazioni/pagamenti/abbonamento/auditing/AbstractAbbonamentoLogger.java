package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAbbonamentoLogger implements IAbbonamentoLogger {

    private static final String GUID_OPERAZIONE = "GUID: ";
    private static final String UTENTE_CHE_EFFETTUA_L_OPERAZIONE = "Utente che effettua l'operazione: ";
    protected static final String DELIMITATORE_LOG = "=========================================================";
    public static final Logger logger = LoggerFactory.getLogger("abbonamento");
    private String guidOperazione;
    protected String messaggio;
    protected String autore;

    public AbstractAbbonamentoLogger(String autore) {

	this.autore = autore;
	this.guidOperazione = UUID.randomUUID().toString();
    }

    @Override
    public void log() {

	StringBuilder sb = new StringBuilder();
	sb.append(DELIMITATORE_LOG);
	sb.append("\n").append(GUID_OPERAZIONE).append(this.guidOperazione);
	sb.append("\n").append(UTENTE_CHE_EFFETTUA_L_OPERAZIONE).append(autore);
	sb.append(messaggio);
	sb.append("\n");
	logger.info(sb.toString());
    }

    @Override
    public void logFineMetodo() {

	StringBuilder sb = new StringBuilder();
	sb.append(DELIMITATORE_LOG);
	sb.append("\n").append(GUID_OPERAZIONE).append(this.guidOperazione);
	sb.append("\n").append(UTENTE_CHE_EFFETTUA_L_OPERAZIONE).append(autore);
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
	sb.append("\n").append(UTENTE_CHE_EFFETTUA_L_OPERAZIONE).append(autore);
	sb.append("\n").append("ERRORE");
	sb.append("\n").append(messaggio);
	sb.append("\n").append(DELIMITATORE_LOG);
	sb.append("\n");
	logger.info(sb.toString());
    }
}
