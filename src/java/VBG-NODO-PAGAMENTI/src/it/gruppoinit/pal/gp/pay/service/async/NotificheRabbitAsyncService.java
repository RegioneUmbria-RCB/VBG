package it.gruppoinit.pal.gp.pay.service.async;

import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.NotificheRabbitService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.helper.RabbitConfig;
import it.gruppoinit.pal.gp.pay.service.helper.rabbit.model.RabbitAggiornaStatoPosizioneDebitoria;
import it.gruppoinit.sigeprosecurity.ws.ISecurityClient;
import it.gruppoinit.sigeprosecurity.ws.SecurityConfig;

public class NotificheRabbitAsyncService extends BaseAsync {

    protected static final Logger log = LoggerFactory.getLogger(NotificheRabbitAsyncService.class);
    private Integer idPosizioneDebitoria;
    private String cfEnteCreditore;
    private Integer idPayPosizioniStato;
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    private ConfigurazionePagamentiService configurazionePagamentiService;
    private NotificheRabbitService notificheRabbitService;
    private ISecurityClient securityClient;

    public NotificheRabbitAsyncService(ApplicationContext context, Integer idPosizioneDebitoria, String cfEnteCreditore,
	    Integer idPayPosizioniStato) {

	this.applicationContext = context;
	this.idPosizioneDebitoria = idPosizioneDebitoria;
	this.cfEnteCreditore = cfEnteCreditore;
	this.idPayPosizioniStato = idPayPosizioniStato;
	this.payConnectorConfigValuesService = getBeanOfType(PayConnectorConfigValuesService.class);
	this.configurazionePagamentiService = getBeanOfType(ConfigurazionePagamentiService.class);
	this.notificheRabbitService = getBeanOfType(NotificheRabbitService.class);
	this.securityClient = getBeanOfType(ISecurityClient.class);
    }

    @Override
    public String getIdOperazione() {

	return "NotificheRabbitAsyncService[" + cfEnteCreditore + "-" + idPosizioneDebitoria + "-" + UUID.randomUUID().toString() + "]";
    }

    @Override
    public void process() throws AsyncProcessException {

	try {
	    configurazionePagamentiService.configuraRequestPerEnteCreditore(cfEnteCreditore);
	} catch (PayConfigurationException e) {
	    throw new AsyncProcessException(e);
	}
	log.debug("{} - notifica rabbit-mq aggiornamento stato per posizione {} - carico configurazione ", idOperazione, idPosizioneDebitoria);
	SecurityConfig securityConfig = SecurityConfig.fromPayConnectorConfigValuesService(payConnectorConfigValuesService);
	RabbitConfig r = RabbitConfig.fromSecurityParams(this.securityClient.getParams(securityConfig));
	log.debug("{} - notifica rabbit-mq aggiornamento stato  per posizione {} - configurazione caricata", idOperazione, idPosizioneDebitoria);
	if (r.isRabbitPagamentiServiceAvailable()) {
	    log.debug("{} - notifica rabbit-mq aggiornamento stato  per posizione {} - urlNotifica disponibile {} creo il client", idOperazione,
		    idPosizioneDebitoria, r.getAggiornaStatoAPI());
	    WebClient client = getClient(r.getAggiornaStatoAPI());
	    RabbitAggiornaStatoPosizioneDebitoria statoP = notificheRabbitService.popolaBeanAggiornamentoStato(idPayPosizioniStato,
		    securityConfig.getAlias(), cfEnteCreditore);
	    String body = null;
	    try {
		body = JSONUtils.marshal(statoP, false);
	    } catch (JAXBException e) {
		throw new AsyncProcessException(e);
	    }
	    log.debug("{} - notifica rabbit-mq aggiornamento stato  per posizione {} - urlNotifica disponibile {} - notifico {}", idOperazione,
		    idPosizioneDebitoria, r.getAggiornaStatoAPI(), body);
	    client.post(body);
	    log.debug("{} - Fine notifica aggiornamento stato posizione {}", idOperazione, idPosizioneDebitoria);
	}
    }

    private WebClient getClient(String apiUrl) {

	WebClient client = WebClient.create(apiUrl);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(10000);
	conduit.getClient().setReceiveTimeout(20000);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	return client;
    }
}
