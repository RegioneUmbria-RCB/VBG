package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.IComunicazioniCommissioniService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * Il task verifica se ci sono record di massive_dettaglio che non abbiano record collegati con la tabella
 * Massive_dett_destinatari. In questo caso inserisce una riga di Massive_dett_destinatari con codiceanagrafe e mail
 * della riga di massive_dettaglio
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_COMUNICAZIONI_COMM_DESTINATARI" spring-bean-id="upgrComunicazioniCommissioniDettDestinariTask"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrComunicazioniCommissioniDettDestinariTask"
 * 			fail-on-error="true" autocommit="true"&gt;
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrComunicazioniCommissioniDettDestinariTask")
public class UpgrComunicazioniCommissioniDettDestinariTask extends BaseJavaTask {

    @Autowired
    IComunicazioniCommissioniService comunicazioniCommissioniService;

    @Override
    public void initialize() throws SetupRunException {

	//implementazione non necessaria
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	this.comunicazioniCommissioniService.upgrDestinatariComunicazioniCommissioniDettagli();
	return 0;
    }
}
