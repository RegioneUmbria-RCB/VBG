package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.IMigrazioneConfigurazioniNodoPagamentiService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;
/**
 * Il task verifica se ci sono record di massive_dettaglio che non abbiano record collegati con la tabella
 * Massive_dett_destinatari. In questo caso inserisce una riga di Massive_dett_destinatari con codiceanagrafe e mail
 * della riga di massive_dettaglio
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_UPDATE_CONF_MAPPATURA_NODO_PAG" spring-bean-id="upgrUpdateConfigurazioniMappaturaNodoPagamentiTask"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateConfigurazioniMappaturaNodoPagamentiTask"
 * 			fail-on-error="false" autocommit="true"&gt;
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpdateConfigurazioniMappaturaNodoPagamentiTask")
public class UpdateConfigurazioniMappaturaNodoPagamentiTask extends BaseJavaTask {

    @Autowired
    private IMigrazioneConfigurazioniNodoPagamentiService service;

    @Override
    public void initialize() throws SetupRunException {

	// NON DEVE ESEGUIRE CODICE PARTICOLARE NELL'INIZIALIZZAZIONE
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	List<String> errori = service.migraConfigurazioniDaContiAParametri();
	List<String> errori2 = service.upgrCodiceVersamento();
	if (!errori2.isEmpty()) {
	    errori.addAll(errori2);
	}
	if (!errori.isEmpty()) {
	    StringBuilder messaggio = new StringBuilder("ATTENZIONE!!! Non è stato possibile convertire le seguenti configurazioni:");
	    for (String e : errori) {
		messaggio.append("\n<br/>").append(e);
	    }
	    handleErrorCondition(messaggio.toString());
	}
	return 0;
    }
}
