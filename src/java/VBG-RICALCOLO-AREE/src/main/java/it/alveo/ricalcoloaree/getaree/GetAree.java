package it.alveo.ricalcoloaree.getaree;

import it.alveo.ricalcoloaree.constants.WebConstants;
import it.alveo.ricalcoloaree.entities.RicalcoloAree;
import it.alveo.ricalcoloaree.entities.composefields.RicalcoloAreePK;
import it.alveo.ricalcoloaree.respdata.RespSessionData;
import it.alveo.ricalcoloaree.utils.LogUtil;
import jakarta.persistence.EntityManager;

public class GetAree {

    public RespSessionData getProcessByRicacloloAreeId(String idcomune, String ricacloloAreeId, EntityManager entitymanager) {

	String methodName = "getProcessByRicacloloAreeId(...)";
	LogUtil.info(this, methodName, "GET called " + ricacloloAreeId);
	RicalcoloAree ricalcoloaree = entitymanager.find(RicalcoloAree.class, RicalcoloAreePK.getIsttance(idcomune, ricacloloAreeId));
	if (ricalcoloaree == null) {
	    throw new RuntimeException("No record found");
	}
	StringBuilder sb = new StringBuilder();
	if (ricalcoloaree.getStato().equals(WebConstants.COMPLETATA) || ricalcoloaree.getStato().equals(WebConstants.CON_SCARTI)) {
	    sb.append("Elaborazione completata");
	} else if (ricalcoloaree.getStato().equals(WebConstants.IN_ERRORE)) {
	    sb.append("In errore");
	} else {
	    sb.append("Elaborazione in corso");
	    sb.append(", ");
	    if (ricalcoloaree.getTotali() == 0) {
		sb.append("non è ancora stato calcolato il numero totale delle pratiche da elaborare");
	    } else {
		sb.append("Da fare: ").append(ricalcoloaree.getDafare()).append(" ");
		sb.append("Fatti: ").append(ricalcoloaree.getFatti()).append(" ");
		sb.append("Totali: ").append(ricalcoloaree.getTotali());
	    }
	}
	LogUtil.info(this, methodName, "GET ended " + ricacloloAreeId);
	return new RespSessionData(ricalcoloaree.getStato(), sb.toString(), ricalcoloaree.getFatti(), ricalcoloaree.getDafare(),
		ricalcoloaree.getTotali());
    }
}
