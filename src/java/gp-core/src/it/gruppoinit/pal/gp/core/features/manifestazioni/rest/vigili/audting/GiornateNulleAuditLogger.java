package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.audting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class GiornateNulleAuditLogger {

    public static Logger logger = LoggerFactory.getLogger("gg.nulla");

    public GiornateNulleAuditLogger() {

	super();
    }

    public String messaggioSegnaGiornataNulla(Integer idGiornata, String note, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n\n=========================================================");
	sb.append("\n==Segna giornata come annullata==\n");
	sb.append(Utilities.getToday(true));
	sb.append("\nid-giornata: ").append(", id: " + idGiornata);
	sb.append("\nutente che effettua l'operazione: ").append(autore);
	logger.info(sb.toString());
	return sb.toString();
    }

    public String messaggioSegnaGiornataNonNulla(Integer idGiornata, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n\n=========================================================");
	sb.append("\n==Segna giornata come non annullata==\n");
	sb.append(Utilities.getToday(true));
	sb.append("\nid-giornata: ").append(", id: " + idGiornata);
	sb.append("\nutente che effettua l'operazione: ").append(autore);
	logger.info(sb.toString());
	return sb.toString();
    }
}
