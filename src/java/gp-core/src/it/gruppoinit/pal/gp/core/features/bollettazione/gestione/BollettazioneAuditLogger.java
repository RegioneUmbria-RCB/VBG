package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.PosizioneDebitoriaBollettazioneBean;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class BollettazioneAuditLogger {

    public static Logger logger = LoggerFactory.getLogger("bollettazione");

    public BollettazioneAuditLogger() {

	super();
    }

    public String messaggioAperturaReportInvioNodoPerBollettazione(DettaglioBollettazione boll, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n\n=========================================================");
	sb.append("\n==Invio delle posizioni debitorie al nodo dei pagamenti==\n");
	sb.append(Utilities.getToday(true));
	sb.append("\nBollettazione: ").append(boll.getDescrizione()).append(", id: ").append(boll.getId());
	sb.append("\nutente che effettua l'operazione: ").append(autore);
	return sb.toString();
    }

    public String messaggioReportInvioNodoPagamenti(PosizioneDebitoriaBollettazioneBean posizioneDebitoriaBean, FunzioneBusinessRemotaException e) {

	StringBuilder sb = new StringBuilder("\n");
	sb.append("Anagrafe: ");
	if (StringUtils.isNotBlank(posizioneDebitoriaBean.getSoggettoDebitore().toSoggettoDebitoreType().getNome())) {
	    sb.append(posizioneDebitoriaBean.getSoggettoDebitore().toSoggettoDebitoreType().getNome()).append(" ");
	}
	sb.append(posizioneDebitoriaBean.getSoggettoDebitore().toSoggettoDebitoreType().getCognome()).append(", ")
		.append(posizioneDebitoriaBean.getSoggettoDebitore().toSoggettoDebitoreType().getCfpi());
	sb.append("\nIdentificativi righe ").append(posizioneDebitoriaBean.getIdRigheBollettazione());
	sb.append("\nproblema rilevato: ").append(e.getMessage());
	sb.append("\n\n");
	return sb.toString();
    }

    public void scriviReportInvioNodoPagamenti(String messaggio) {

	logger.error(messaggio);
    }
}
