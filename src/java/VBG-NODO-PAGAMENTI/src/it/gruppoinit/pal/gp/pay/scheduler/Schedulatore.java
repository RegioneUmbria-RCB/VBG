package it.gruppoinit.pal.gp.pay.scheduler;

import java.util.List;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;

public class Schedulatore {

    private static final Logger log = LoggerFactory.getLogger(Schedulatore.class);
    private ConfigurazionePagamentiService configurazionePagamentiService;

    public Schedulatore(ConfigurazionePagamentiService configurazionePagamentiService) {

	super();
	this.configurazionePagamentiService = configurazionePagamentiService;
    }

    public void configuraEAvvia(List<PayProfiliEntiCreditori> configuredProfiles, PayConnectorService payConnectorService, Scheduler scheduler) {

	for (PayProfiliEntiCreditori profiloEnte : configuredProfiles) {
	    try {
		this.configurazionePagamentiService.configuraRequestPerEnteCreditore(profiloEnte);
		IPayConnector connector = payConnectorService.getPayConnectorInstance(profiloEnte.getPayConnector());
		List<ServiziSchedulatiEnum> servizi = connector.getListaServiziSchedulatiSupportati();
		for (ServiziSchedulatiEnum servizio : servizi) {
		    connector.getServizioSchedulatoPerTipo(servizio).configuraESchedula(scheduler);
		}
	    } catch (PayConfigurationException e) {
		log.error("Servizi Schedulati errore nel recupero del connettore", e);
	    }
	}
    }
}
