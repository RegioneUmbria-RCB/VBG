package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.Date;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.exception.ServizioRemotoException;
import it.gruppoinit.pal.gp.pay.service.NotificheRabbitService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.async.NotificheRabbitAsyncService;
import it.gruppoinit.pal.gp.pay.service.helper.RabbitConfig;
import it.gruppoinit.pal.gp.pay.service.helper.rabbit.model.RabbitAggiornaStatoPosizioneDebitoria;
import it.gruppoinit.pal.gp.pay.service.helper.rabbit.model.RabbitAggiornadataScadenzaPosizioneDebitoria;
import it.gruppoinit.sigeprosecurity.ws.ISecurityClient;
import it.gruppoinit.sigeprosecurity.ws.SecurityConfig;

@Service
public class NotificheRabbitServiceImpl implements NotificheRabbitService {

    private static final Logger log = LoggerFactory.getLogger(NotificheRabbitServiceImpl.class);
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    private TaskExecutor taskExecutor;
    private ApplicationContext applicationContext;
    private PayStatoPagamentiService payStatoPagamentiService;
    private ISecurityClient securityClient;

    @Autowired
    public void setPayConnectorConfigValuesService(PayConnectorConfigValuesService payConnectorConfigValuesService) {

	this.payConnectorConfigValuesService = payConnectorConfigValuesService;
    }

    @Autowired
    public void setPayPosizioniDebitorieService(PayPosizioniDebitorieService payPosizioniDebitorieService) {

	this.payPosizioniDebitorieService = payPosizioniDebitorieService;
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {

	this.taskExecutor = taskExecutor;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {

	this.applicationContext = applicationContext;
    }

    @Autowired
    public void setPayStatoPagamentiService(PayStatoPagamentiService payStatoPagamentiService) {

	this.payStatoPagamentiService = payStatoPagamentiService;
    }

    @Autowired
    public void setSecurityClient(ISecurityClient securityClient) {

	this.securityClient = securityClient;
    }

    @Override
    public void notificaStatoRabbitMQ(Integer idPosizione, String cfCodiceProfilo, Integer idPayStatoPagamenti) throws Exception {

	log.debug("notificaApiBackend# entro nel metodo {},{}", idPosizione, cfCodiceProfilo);
	try {
	    taskExecutor.execute(new NotificheRabbitAsyncService(applicationContext, idPosizione, cfCodiceProfilo, idPayStatoPagamenti));
	} catch (Exception e) {
	    log.error("eseguiTask# Errore nell' esecuzione della comunicazione per la posizione " + idPosizione + "-" + cfCodiceProfilo +
		      ", errore: " + e.getMessage(),
		    e);
	}
	log.debug("Fine notifica posizione {},{}", idPosizione, cfCodiceProfilo);
    }

    @Override
    public void notificaAggiornamentoDataScadenza(Integer idPosizioneDebitoria, Date dataScadenza) throws ServizioRemotoException {

	PayPosizioniDebitorie pos = payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	Set<String> findRiferimentiClient = payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(idPosizioneDebitoria);
	log.debug("notifica rabbit-mq datascadenza per posizione {} - carico configurazione ", idPosizioneDebitoria);
	SecurityConfig securityConfig = SecurityConfig.fromPayConnectorConfigValuesService(payConnectorConfigValuesService);
	RabbitConfig r = RabbitConfig.fromSecurityParams(this.securityClient.getParams(securityConfig));
	log.debug("notifica rabbit-mq datascadenza per posizione {} - configurazione caricata", idPosizioneDebitoria);
	if (r.isRabbitPagamentiServiceAvailable()) {
	    log.debug("notifica rabbit-mq datascadenza per posizione {} - urlNotifica disponibile {} creo il client", idPosizioneDebitoria,
		    r.getAggiornaDataScadenzaAPI());
	    WebClient client = getClient(r.getAggiornaDataScadenzaAPI());
	    RabbitAggiornadataScadenzaPosizioneDebitoria stato = RabbitAggiornadataScadenzaPosizioneDebitoria.fromPayPosizioneDebitoria(pos,
		    securityConfig.getAlias(), findRiferimentiClient);
	    String body;
	    try {
		body = Utilities.marshalJsonObject(stato, RabbitAggiornadataScadenzaPosizioneDebitoria.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    } catch (JAXBException e) {
		throw new RuntimeException("Errore nella trasformazione dei dati nella classe " + RabbitAggiornadataScadenzaPosizioneDebitoria.class,
			e);
	    }
	    log.debug("notifica rabbit-mq datascadenza per posizione {} - urlNotifica disponibile {} - notifico {}", idPosizioneDebitoria,
		    r.getAggiornaStatoAPI(), body);
	    try {
		client.post(body);
	    } catch (Exception e) {
		log.error("Errore nella notifica di " + stato, e);
		throw new ServizioRemotoException(e);
	    }
	    log.debug("Fine notifica rabbit-mq datascadenza posizione {}", idPosizioneDebitoria);
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

    @Override
    public RabbitAggiornaStatoPosizioneDebitoria popolaBeanAggiornamentoStato(Integer idPayStatoPagamenti, String alias, String cfEnteCreditore) {

	log.debug("idPayStatoPagamenti: {}", idPayStatoPagamenti);
	log.debug("alias input: {}", alias);
	log.debug("cfEnteCreditore: {}", cfEnteCreditore);
	log.debug("alias ormhelper: {}", ORMHelper.getIdcomuneAlias());
	log.debug("idcomune ormhelper: {}", ORMHelper.getIdcomune());
	PayStatoPagamenti entity = payStatoPagamentiService.findById(new PkId(idPayStatoPagamenti));
	log.debug("entity: {}", entity);
	log.debug("entity.getPosizioneDebitoria: {}", entity.getPosizioneDebitoria());
	String cfPiva = entity.getPosizioneDebitoria().getSoggettoDebitore().getCfPi();
	String idcomune = entity.getId().getIdcomune();
	String nominativo = entity.getPosizioneDebitoria().getSoggettoDebitore().getDenominazioneCompleta();
	Set<String> riferimentoClient = payPosizioniDebitorieService
		.findRiferimentiClientByPosizioneDebitoria(entity.getPosizioneDebitoria().getId().getCodice());
	String stato = entity.getStato();
	String uuid = entity.getPosizioneDebitoria().getUuid();
	Date dataEvento = entity.getDataEvento();
	return RabbitAggiornaStatoPosizioneDebitoria.fromPayPosizioneStato(alias, cfEnteCreditore, cfPiva, idcomune, nominativo, riferimentoClient,
		stato, uuid, dataEvento);
    }
}
