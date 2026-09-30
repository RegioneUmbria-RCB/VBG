/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.commons.DeployProperties;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.GenerazioneFattureCommand;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaFatturaCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.dao.PayConnectorConfigDAO;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayPosDebMassive;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.scheduler.PendingRequestJob;
import it.gruppoinit.pal.gp.pay.scheduler.Schedulatore;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.service.PayPosDebMassiveService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniContabiliService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;
import it.gruppoinit.pal.gp.pay.service.PaySoggettiDebitoriService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.CaricamentoMassivoStatiEnum;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RiferimentiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;

/**
 * @author francol
 *
 */
@Service
public class PayConnectorServiceImpl extends BaseServiceImpl<PayConnectorConfig, String> implements PayConnectorService {

    private static final Logger log = LoggerFactory.getLogger(PayConnectorServiceImpl.class);
    public static final int SCHEDULER_TRIGGER_START_DELAY_MIN = 1;
    @Autowired
    private PayConnectorConfigDAO payConnectorConfigDAO;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayRichiesteService payRichiesteService;
    @Autowired
    private PayProfiliEntiCreditoriService payProfiliEntiCreditoriService;
    @Autowired
    private PayRegistrazioniContabiliService payRegistrazioniContabiliService;
    @Autowired
    private PaySoggettiDebitoriService paySoggettiDebitoriService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DeployProperties deployProps;
    @Autowired
    private SchedulerFactoryBean schedulerFactory;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;
    @Autowired
    private PayPosDebMassiveService payPosDebMassiveService;
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;

    /**
     * 1 - inizializzazione dei connettori come bean di Spring e relativi parametri di funzionamento letti da DB 2 -
     * inizializzazione dei job di quartz e loro schedulazione in base alla configurazione dei servizi di ciascun
     * connettore 3 - avvio dello scheduler di quartz
     */
    @PostConstruct
    public void initializeConnectors() {

	List<PayProfiliEntiCreditori> configuredProfiles = this.payProfiliEntiCreditoriService.findAll(null, null);
	Scheduler schdl = schedulerFactory.getScheduler();
	Calendar triggerStartTime = GregorianCalendar.getInstance();
	for (PayProfiliEntiCreditori profiloEnte : configuredProfiles) {
	    PayConnectorConfig config = profiloEnte.getPayConnector();
	    try {
		IPayConnector connector = this.getPayConnectorInstance(config, false);
		PayConnectorWsEndpoint endpointCfg;
		String tipoServizio = null;
		Map<String, PayConnectorWsEndpoint> scheduledServices = initializeSingleConnector(config, connector);
		boolean schedulerEnabled = BooleanUtils.toBoolean(deployProps.getSchedulerEnabled());
		if (schedulerEnabled) {
		    //configurazione dei job e dei trigger di quartz per lo scheduler dei servizi
		    Iterator<String> keys = scheduledServices.keySet().iterator();
		    while (keys.hasNext()) {
			tipoServizio = keys.next();
			endpointCfg = scheduledServices.get(tipoServizio);
			JobDataMap data = new JobDataMap();
			data.put(PendingRequestJob.CONTEXT_PARAM_TIPO_SERVIZIO, tipoServizio);
			data.put(PendingRequestJob.CONTEXT_PARAM_ID_ENTE, profiloEnte.getCfCodiceProfilo());
			data.put(PendingRequestJob.CONTEXT_PARAM_APPLICATION_CONTEXT, applicationContext);
			JobDetail jd = JobBuilder.newJob(PendingRequestJob.class).withIdentity(tipoServizio, config.getCodice()).usingJobData(data)
				.build();
			//creo il cron trigger
			triggerStartTime.add(Calendar.MINUTE, SCHEDULER_TRIGGER_START_DELAY_MIN);
			ScheduleBuilder<CronTrigger> sb = CronScheduleBuilder.cronSchedule(endpointCfg.getQuartzSchedule());
			Trigger ct = TriggerBuilder.newTrigger().withIdentity(tipoServizio, config.getCodice()).withSchedule(sb)
				.startAt(triggerStartTime.getTime()).build();
			//schedulo il job nello scheduler
			try {
			    schdl.scheduleJob(jd, ct);
			    String stato = " stato attivo";
			    if (BooleanUtils.isTrue(endpointCfg.getFlagSpegniScheduler())) {
				schdl.pauseJob(new JobKey(tipoServizio, config.getCodice()));
				stato = " stato sospeso";
			    }
			    if (log.isInfoEnabled()) {
				log.info("initializeConnectors - schedulato job {} con intervallo {}{} start at {}", jd.getKey(),
					endpointCfg.getQuartzSchedule(), stato, Utilities.formatDate(triggerStartTime.getTime(), true));
			    }
			} catch (SchedulerException e) {
			    log.error("initializeConnectors - errore nella schedulazione del job " + jd.getKey(), e);
			}
		    }
		}
		log.info("initializeConnectors - inizializzazione del connettore {} completata.", config.getCodice());
	    } catch (PayConfigurationException e) {
		log.error("initializeConnectors - errore nella configurazione del connettore {} di classe {}", config.getCodice(),
			config.getPayConnectorJavaClass());
	    }
	}
	try {
	    new Schedulatore(configurazionePagamentiService).configuraEAvvia(configuredProfiles, this, schdl);
	    //se ci sono task schedulati avvio lo scheduler di quartz
	    Set<JobKey> jKeys = schdl.getJobKeys(GroupMatcher.anyJobGroup());
	    if (!jKeys.isEmpty()) {
		schdl.start();
		log.info("initializeConnectors - scheduler di quartz avviato con {} jobs schedulati.", jKeys.size());
	    }
	} catch (SchedulerException e) {
	    log.error("initializeConnectors - errore nell'avvio dello scheduler di quartz ", e);
	}
    }

    private Map<String, PayConnectorWsEndpoint> initializeSingleConnector(PayConnectorConfig config, IPayConnector connector) {

	connector.setIdInstallazione(StringUtils.defaultString(deployProps.getInstallationId()));
	connector.setConnectorCode(config.getCodice());
	connector.setConnectorName(config.getDescrizione());
	//configurazione dei servizi del connettore
	Map<String, PayConnectorWsEndpoint> scheduledServices = new HashMap<>();
	//caricamento posizioni
	PayConnectorWsEndpoint endpointCfg = config.getWsCaricamento();
	String tipoServizio = TipiEvento.INVIA_POSIZIONI_A_PSP.name();
	if (endpointCfg != null) {
	    connector.setWsCaricamentoConfig(endpointCfg);
	    //se è presente l'espressione cron nella configurazione dell'endpoint memorizzo il servizio corrispondente per schedularlo come cron trigger in quartz
	    if (StringUtils.isNotBlank(endpointCfg.getQuartzSchedule())) {
		scheduledServices.put(tipoServizio, endpointCfg);
	    }
	}
	//annullamento posizioni
	endpointCfg = config.getWsAnnullamento();
	tipoServizio = TipiEvento.ANNULLA_POSIZIONI_PSP.name();
	if (endpointCfg != null) {
	    connector.setWsAnnullamentoConfig(endpointCfg);
	    if (StringUtils.isNotBlank(endpointCfg.getQuartzSchedule())) {
		scheduledServices.put(tipoServizio, endpointCfg);
		//schedulo separatamente gli annullamenti delle posizioni pagate offline perchè nei due casi il job deve essere invocato con parametri diversi
		scheduledServices.put(TipiEvento.ANNULLA_PAGAMENTI_OFFLINE_PSP.name(), endpointCfg);
	    }
	}
	//verifica stato posizioni
	endpointCfg = config.getWsVerifica();
	tipoServizio = TipiEvento.VERIFICA_STATO_PAGAMENTO_PSP.name();
	if (endpointCfg != null) {
	    connector.setWsVerificaConfig(endpointCfg);
	    if (StringUtils.isNotBlank(endpointCfg.getQuartzSchedule())) {
		scheduledServices.put(tipoServizio, endpointCfg);
	    }
	}
	//notifica\rendicontazione pagamenti
	endpointCfg = config.getWsNotifica();
	tipoServizio = TipiEvento.RENDICONTAZIONE_PAGAMENTO_PSP.name();
	if (endpointCfg != null) {
	    connector.setWsNotificaConfig(endpointCfg);
	    if (StringUtils.isNotBlank(endpointCfg.getQuartzSchedule())) {
		scheduledServices.put(tipoServizio, endpointCfg);
	    }
	}
	//generazione\invio avviso di pagamento
	endpointCfg = config.getWsAvviso();
	tipoServizio = TipiEvento.GENERA_AVVISO_PSP.name();
	if (endpointCfg != null) {
	    connector.setWsAvvisoConfig(endpointCfg);
	    if (StringUtils.isNotBlank(endpointCfg.getQuartzSchedule())) {
		scheduledServices.put(tipoServizio, endpointCfg);
	    }
	}
	//generazione\invio fattura
	endpointCfg = config.getWsFattura();
	tipoServizio = TipiEvento.GENERA_FATTURA_PSP.name();
	if (endpointCfg != null) {
	    connector.setWsFatturaConfig(endpointCfg);
	    if (StringUtils.isNotBlank(endpointCfg.getQuartzSchedule())) {
		scheduledServices.put(tipoServizio, endpointCfg);
	    }
	}
	//download ricevuta telemarica
	endpointCfg = config.getWsRicevuta();
	tipoServizio = TipiEvento.RECUPERA_RICEVUTA_PSP.name();
	if (endpointCfg != null) {
	    connector.setWsRicevutaConfig(endpointCfg);
	    if (StringUtils.isNotBlank(endpointCfg.getQuartzSchedule())) {
		scheduledServices.put(tipoServizio, endpointCfg);
	    }
	}
	//attivazione sessione di pagamento
	connector.setWsAttivaSessioneConfig(config.getWsAttivaSessione());
	//generazione IUV
	connector.setWsIuvConfig(config.getWsIUV());
	// carticamentoMassivo
	connector.setWsCaricamentoMassivoConfig(config.getWsCaricamentoMassivo());
	//sincronizzazione debiti per soggetto
	connector.setWsSincronizzaDebitiPerSoggettoConfig(config.getWsSincronizzaDebitiPerSoggetto());
	//servizio di autenticazione
	connector.setWsSecurityConfig(config.getWsSecurity());
	return scheduledServices;
    }

    @PreDestroy
    public void destroyConnectors() {

	try {
	    Scheduler schdl = schedulerFactory.getScheduler();
	    if (schdl.isStarted()) {
		schdl.shutdown();
		if (log.isInfoEnabled())
		    log.info("destroyConnectors - scheduler di quartz arrestato.");
	    }
	} catch (SchedulerException e) {
	    log.error("destroyConnectors - errore nell'arresto dello scheduler di quartz ", e);
	}
    }

    @Override
    public IPayConnector getPayConnectorInstance() throws PayConfigurationException {

	PayProfiliEntiCreditori currentProfile = PayConfigurationHelper.getProfiloEnteCreditore();
	PayConnectorConfig currentConnector = null;
	if (currentProfile != null) {
	    currentConnector = currentProfile.getPayConnector();
	}
	if (currentConnector != null) {
	    return this.getPayConnectorInstance(currentConnector);
	}
	log.error("getPayConnectorInstance! Connettore non trovato nel contesto corrente");
	throw new PayConfigurationException("Connettore non trovato nel contesto corrente");
    }

    private IPayConnector getPayConnectorInstance(PayConnectorConfig cfg, boolean inizializzaConnettore) throws PayConfigurationException {

	if (cfg != null) {
	    log.debug("inizializzo il connettore con codice {}", cfg.getCodice());
	    Object connBean = null;
	    Throwable lastError = null;
	    if (StringUtils.isNotBlank(cfg.getCodice()) && this.applicationContext.containsBean(cfg.getCodice())) {
		try {
		    connBean = this.applicationContext.getBean(cfg.getCodice());
		} catch (BeansException e) {
		    log.error("errore nel recupero del connettore per nome: " + cfg.getCodice() + ". Si cercherà di caricarlo per classe Java.", e);
		    lastError = e;
		}
	    }
	    if (connBean == null && StringUtils.isNotBlank(cfg.getPayConnectorJavaClass())) {
		log.debug("Il connettore con codice {} non è stato registrato lo registro", cfg.getCodice());
		try {
		    String connectorClassName = cfg.getPayConnectorJavaClass();
		    Class<?> connectorClass = Class.forName(connectorClassName);
		    log.debug("Il connettore con codice {} recupero la beanFactory", cfg.getCodice());
		    DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory();
		    Constructor<?> ctor = connectorClass.getConstructor();
		    log.debug("Il connettore con codice {} creao la classe con il costruttore di default {}", cfg.getCodice(), connectorClassName);
		    connBean = ctor.newInstance();
		    log.debug("Il connettore con codice {} inizializzo la classe {}", cfg.getCodice(), connectorClassName);
		    beanFactory.initializeBean(connBean, cfg.getCodice()); //could be class' canonical name
		    log.debug("Il connettore con codice {} autowireBeanProperties per la classe {}", cfg.getCodice(), connectorClassName);
		    beanFactory.autowireBeanProperties(connBean, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
		    log.debug("Il connettore con codice {} registerSingleton per la classe {}", cfg.getCodice(), connectorClassName);
		    if (inizializzaConnettore) {
			initializeSingleConnector(cfg, (IPayConnector) connBean);
		    }
		    beanFactory.registerSingleton(cfg.getCodice(), connBean);
		} catch (BeansException | ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
			| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		    log.error("errore nel recupero del connettore per classe java: " + cfg.getCodice(), e);
		    lastError = e;
		}
	    }
	    if (connBean != null) {
		if (connBean instanceof IPayConnector) {
		    return (IPayConnector) connBean;
		} else {
		    log.error("impossibile inizializzare il connettore {} per classe java: {} perchè non implementa l'interfaccia IPayConnector",
			    cfg.getCodice(), cfg.getPayConnectorJavaClass());
		    throw new PayConfigurationException("impossibile inizializzare il connettore " + cfg.getCodice() + " di classe " +
							cfg.getPayConnectorJavaClass() + " perchè non implementa l'interfaccia IPayConnector");
		}
	    } else {
		log.error("impossibile inizializzare il connettore {} per classe java: {} a causa di {}", cfg.getCodice(),
			cfg.getPayConnectorJavaClass(), lastError);
		throw new PayConfigurationException(
			"impossibile inizializzare il connettore " + cfg.getCodice() + " di classe " + cfg.getPayConnectorJavaClass(), lastError);
	    }
	} else {
	    log.error("impossibile inizializzare il connettore informazioni di configurazione mancanti: cfg nullo");
	    throw new PayConfigurationException("impossibile inizializzare il connettore: informazioni di configurazione mancanti: cfg nullo");
	}
    }

    @Override
    public IPayConnector getPayConnectorInstance(PayConnectorConfig cfg) throws PayConfigurationException {

	return this.getPayConnectorInstance(cfg, true);
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorieInPSP(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	ElencoPosizioniDebitorieEsitoType esitoPSP = new ElencoPosizioniDebitorieEsitoType();
	if (datiRegistrazioniCommand != null && !datiRegistrazioniCommand.getRegistrazioniPosizioni().isEmpty()) {
	    if (log.isInfoEnabled()) {
		log.info("registraPosizioniDebitorieInPSP - invio al connettore della richiesta di caricamento di {} registrazioni contabili.",
			datiRegistrazioniCommand.getRegistrazioniPosizioni().size());
	    }
	    esitoPSP = getPayConnectorInstance().registraPosizioniDebitorie(datiRegistrazioniCommand);
	    RiferimentiPosizioniDebitorieHelper esitoH = new RiferimentiPosizioniDebitorieHelper(esitoPSP);
	    List<PayRegistrazioniContabili> regContElaborate = datiRegistrazioniCommand.getRegistrazioniPosizioni();
	    // aggiorno lo stato delle posizioni debitorie in base all'esito delle chiamate al PSP
	    for (PayRegistrazioniContabili regElaborata : regContElaborate) {
		for (PayPosizioniDebitorie posElaborata : regElaborata.getPosizioniDebitorie()) {
		    EsitoOperazionePosizioneDebitoriaType esitoPosDeb = (EsitoOperazionePosizioneDebitoriaType) esitoH
			    .findRiferimentoPosizioneById(BigInteger.valueOf(posElaborata.getId().getCodice()));
		    if (esitoPosDeb != null) {
			payStatoPagamentiService.registraStatoPosizioneDebitoria(esitoPosDeb, posElaborata);
		    }
		}
	    }
	} else {
	    log.warn(
		    "registraPosizioniDebitorieInPSP - nessuna posizione valida da trasmettere al connettore: i servizi del PSP non sono stati invocati");
	}
	return esitoPSP;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorieInPSP(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagataOffline)
	    throws PayException {

	ElencoPosizioniDebitorieEsitoType esito = new ElencoPosizioniDebitorieEsitoType();
	if (datiRegistrazioniCommand != null) {
	    //predispongo l'esito OK con stato ANNULATO per tutte le posizioni da annullare
	    int posCount = 0;
	    for (int i = 0; i < datiRegistrazioniCommand.getRegistrazioniPosizioni().size();) {
		PayRegistrazioniContabili payReg = datiRegistrazioniCommand.getRegistrazioniPosizioni().get(i);
		List<PayPosizioniDebitorie> payPoss = new ArrayList<>(payReg.getPosizioniDebitorie());
		int y = 0;
		for (; y < payPoss.size();) {
		    EsitoOperazionePosizioneDebitoriaType esitoAnnull = new EsitoOperazionePosizioneDebitoriaType();
		    PayPosizioniDebitorie payPos = payPoss.get(y);
		    esitoAnnull.setStato(StatoPagamentoType.ANNULLATO);
		    esitoAnnull.setEsito(true);
		    Set<String> rifClient = payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice());
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoAnnull, payPos, rifClient);
		    esito.getEsitoPosizione().add(esitoAnnull);
		    y++;
		}
		posCount += y;
		if (payReg.getPosizioniDebitorie().isEmpty()) {
		    datiRegistrazioniCommand.getRegistrazioniPosizioni().remove(payReg);
		} else {
		    i++;
		}
	    }
	    if (!datiRegistrazioniCommand.getRegistrazioniPosizioni().isEmpty()) {
		log.info("annullaPosizioniDebitorieInPSP - invio al connettore della richiesta di annullamento per {} posizioni debitorie.",
			posCount);
		ElencoPosizioniDebitorieEsitoType esitiPSP = getPayConnectorInstance().annullaPosizioniDebitorie(datiRegistrazioniCommand,
			pagataOffline);
		RiferimentiPosizioniDebitorieHelper hEsitoPsp = new RiferimentiPosizioniDebitorieHelper(esitiPSP);
		for (EsitoOperazionePosizioneDebitoriaType esitoPosAnnullata : esito.getEsitoPosizione()) {
		    EsitoOperazionePosizioneDebitoriaType esitoPsp = (EsitoOperazionePosizioneDebitoriaType) hEsitoPsp
			    .findRiferimentoPosizioneById(esitoPosAnnullata.getIdPosizione());
		    //posizione elaborata dal connettore
		    if (esitoPsp != null) {
			//l'esito da restituire al chiamante viene aggiornato con l'esito restituiro dal connettore
			esitoPosAnnullata.setCodiceErrore(esitoPsp.getCodiceErrore());
			esitoPosAnnullata.setEsito(esitoPsp.isEsito());
			esitoPosAnnullata.setMessaggio(esitoPsp.getMessaggio());
			esitoPosAnnullata.setStato(esitoPsp.getStato());
			//si aggiorna lo stato della posizione nel nodo solo in caso di esito positivo restituito dal PSP
		    }
		    //le posizioni OTF non elaborate dal connettore vengono impostate su annullata senza bisogno di chiamare il PSP perchè hanno già predisposto esito true
		    if (esitoPosAnnullata.isEsito()) {
			try {
			    PayPosizioniDebitorie posAnnullata = datiRegistrazioniCommand.findPosizioneByIdPSP(esitoPosAnnullata.getIUV());
			    if (posAnnullata == null) {
				posAnnullata = this.payPosizioniDebitorieService.findById(new PkId(esitoPosAnnullata.getIdPosizione().intValue()));
			    }
			    if (posAnnullata != null) {
				EsitoOperazionePosizioneDebitoriaType newStatusInfo = esitoPosAnnullata;
				if (pagataOffline) {
				    StatoPosizioneType statoPosAnnullata = new StatoPosizioneType();
				    statoPosAnnullata.setCodiceErrore(esitoPosAnnullata.getCodiceErrore());
				    statoPosAnnullata.setEsito(esitoPosAnnullata.isEsito());
				    PopolamentoDatiHelper.completaDatiDaEsitoOperazioneDebitoriaType(statoPosAnnullata, esitoPosAnnullata);
				    statoPosAnnullata.setMessaggio(esitoPosAnnullata.getMessaggio());
				    statoPosAnnullata.setStato(esitoPosAnnullata.getStato());
				    statoPosAnnullata.setDatiPagamento(
					    datiRegistrazioniCommand.findDatiPagamentoByIdPosizione(posAnnullata.getId().getCodice()));
				    newStatusInfo = statoPosAnnullata;
				}
				payStatoPagamentiService.registraStatoPosizioneDebitoria(newStatusInfo, posAnnullata);
			    }
			} catch (Exception e) {
			    log.error("errore nell'aggiornamento sul DB dello stato per la posizione annullata con id " +
				      esitoPosAnnullata.getIdPosizione().intValue(),
				    e);
			    esitoPosAnnullata.setEsito(false);
			    esitoPosAnnullata.setMessaggio(
				    "l'annullamento è stato richiesto ma non è stato possibile aggiornare lo stato della posizione nel nodo pagamenti.");
			    esitoPosAnnullata.setStato(StatoPagamentoType.CON_ERRORE);
			}
		    }
		}
	    } else {
		log.warn("annullaPosizioniDebitorieInPSP - nessuna posizione valida da annullare sul PSP: i servizi del PSP non sono stati invocati");
	    }
	}
	return esito;
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPosizioniInPSP(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	if (cmd == null || cmd.getPosizioni() == null || cmd.getPosizioni().isEmpty()) {
	    return new ElencoStatoPosizioniType();
	}
	if (log.isInfoEnabled()) {
	    log.info("verificaStatoPosizioniInPSP - invio al connettore della richiesta di verifica stato per {} posizioni debitorie.",
		    cmd.getPosizioni().size());
	}
	return this.verificaPosizioniPSPImpl(cmd, false);
    }

    @Override
    public ElencoStatoPosizioniType rendicontazionePagamentiPSP(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	if (cmd == null || cmd.getPosizioni() == null || cmd.getPosizioni().isEmpty()) {
	    return new ElencoStatoPosizioniType();
	}
	if (log.isInfoEnabled()) {
	    log.info(
		    "rendicontazionePagamentiPSP - invio al connettore della richiesta di rendicontazione di avvenuto pagamento per {} posizioni debitorie.",
		    cmd.getPosizioni().size());
	}
	return this.verificaPosizioniPSPImpl(cmd, true);
    }

    @Override
    public ElencoDocumentiEsitoType generaFatturePSP(GenerazioneFattureCommand cmd) throws PayException {

	if (cmd == null || cmd.getRichieste() == null || cmd.getRichieste().isEmpty()) {
	    log.info(
		    "generaFatturePSP - nessuna richiesta di generazione fattura da inviare al connettore. I servizi del PSP non sono stati invocati");
	    return new ElencoDocumentiEsitoType();
	}
	if (log.isInfoEnabled()) {
	    log.info("generaFatturePSP - invio al connettore della richiesta di generazione fatture per {} posizioni debitorie.",
		    cmd.getRichieste().size());
	}
	ElencoDocumentiEsitoType retEsiti = this.getPayConnectorInstance().generaFatture(cmd);
	//per ogni esito restituito dal connettore registro le fatture associate alle posizioni debitorie se presenti negli esiti del connettore
	for (EsitoDocumentoPosizioneDebitoriaType esitoDoc : retEsiti.getEsitoPosizione()) {
	    //recupero dal command la posizione debitoria associata all'esito che sto elaborando
	    PayPosizioniDebitorie findMe = new PayPosizioniDebitorie(new PkId(esitoDoc.getIdPosizione().intValue()));
	    int index = cmd.getRichieste().indexOf(new RichiestaFatturaCommand(findMe));
	    if (index > -1) {
		RichiestaFatturaCommand found = cmd.getRichieste().get(index);
		//invoco il metodo transazionale per scrivere i dati della fattura su db e impostare la relativa 
		//richiesta su completata con transazioni separate per ciascuna posizione debitoria
		this.payRichiesteService.registraEsitoRichiestaDocumentoTrans(esitoDoc, found.getPosizioneDebitoria());
	    } else {
		throw new PayException("impossibile identificare la posizione con id " + findMe.getId() +
				       " per aggiornare l'esito dell'operazione di generazione fattura");
	    }
	}
	return retEsiti;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPSP(PosizioniDebitorieCommand cmd) throws PayException {

	if (cmd == null || cmd.getRegistrazioniPosizioni() == null || cmd.getRegistrazioniPosizioni().isEmpty()) {
	    log.info("inviaAvvisiPSP - nessuna richiesta di invio avviso da inviare al connettore. I servizi del PSP non sono stati invocati");
	    return new ElencoDocumentiEsitoType();
	}
	if (log.isInfoEnabled()) {
	    log.info("inviaAvvisiPSP - invio al connettore della richiesta di invio avvisi per {} registrazioni contabili.",
		    cmd.getRegistrazioniPosizioni().size());
	}
	ElencoDocumentiEsitoType retEsiti = this.getPayConnectorInstance().inviaAvvisiPagamento(cmd);
	//per ogni esito restituito dal connettore registro gli avvisi associati alle posizioni debitorie se presenti negli esiti del connettore
	for (EsitoDocumentoPosizioneDebitoriaType esitoDoc : retEsiti.getEsitoPosizione()) {
	    //recupero dal db la posizione debitoria associata all'esito che sto elaborando
	    PayPosizioniDebitorie pos = this.payPosizioniDebitorieService.findById(new PkId(esitoDoc.getIdPosizione().intValue()));
	    if (pos != null) {
		//invoco il metodo transazionale per scrivere i dati della fattura su db e impostare la relativa 
		//richiesta su completata con transazioni separate per ciascuna posizione debitoria
		this.payRichiesteService.registraEsitoRichiestaDocumentoTrans(esitoDoc, pos);
	    } else {
		throw new PayException("impossibile identificare la posizione con id " + esitoDoc.getIdPosizione().intValue() +
				       " per aggiornare l'esito dell'operazione di invio avviso");
	    }
	}
	return retEsiti;
    }

    @Override
    public ElencoDocumentiEsitoType scaricaRicevutePSP(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	if (cmd == null || cmd.getPosizioni() == null || cmd.getPosizioni().isEmpty()) {
	    log.info(
		    "scaricaRicevutePSP - nessuna richiesta di download della ricevuta da inviare al connettore. I servizi del PSP non sono stati invocati");
	    return new ElencoDocumentiEsitoType();
	}
	if (log.isInfoEnabled()) {
	    log.info("scaricaRicevutePSP - invio al connettore della richiesta di download ricevute per {} posizioni debitorie.",
		    cmd.getPosizioni().size());
	}
	ElencoDocumentiEsitoType retEsiti = this.getPayConnectorInstance().scaricaRicevuteTelematiche(cmd);
	//per ogni esito restituito dal connettore registro le ricevute associate alle posizioni debitorie se presenti negli esiti del connettore
	for (EsitoDocumentoPosizioneDebitoriaType esitoDoc : retEsiti.getEsitoPosizione()) {
	    //recupero dal command la posizione debitoria associata all'esito che sto elaborando
	    PayPosizioniDebitorie findMe = new PayPosizioniDebitorie(new PkId(esitoDoc.getIdPosizione().intValue()));
	    int index = cmd.getPosizioni().indexOf(findMe);
	    if (index > -1) {
		PayPosizioniDebitorie found = cmd.getPosizioni().get(index);
		//invoco il metodo transazionale per scrivere i dati della ricevuta su db e imposta la relativa 
		//richiesta su completata con transazioni separate per ciascuna posizione debitoria
		this.payRichiesteService.registraEsitoRichiestaDocumentoTrans(esitoDoc, found);
	    } else {
		throw new PayException("impossibile identificare la posizione con id " + findMe.getId() +
				       " per aggiornare l'esito dell'operazione di download ricevuta");
	    }
	}
	return retEsiti;
    }

    private ElencoStatoPosizioniType verificaPosizioniPSPImpl(RichiestaSuListaPosizioniCommand cmd, boolean rendicontazione) throws PayException {

	ElencoStatoPosizioniType esiti;
	//invoco il servizio del PSP tramite l'implementazione del connettore
	if (rendicontazione) {
	    esiti = this.getPayConnectorInstance().rendicontazionePagamenti(cmd);
	} else {
	    esiti = this.getPayConnectorInstance().verificaStatoPagamenti(cmd);
	}
	for (StatoPosizioneType statoPsp : esiti.getStatoPosizioni()) {
	    //identifico lo stato della posizione precedentemente letto dal db e già impostato nel command
	    StatoPagamentoType statoDB = null;
	    PkId idPos = new PkId(statoPsp.getIdPosizione().intValue());
	    int posIdx = cmd.getPosizioni().indexOf(new PayPosizioniDebitorie(idPos));
	    PayPosizioniDebitorie payPos = null;
	    if (posIdx > -1) {
		payPos = cmd.getPosizioni().get(posIdx);
		if (payPos.recuperaStatoCorrente() != null && StringUtils.isNotBlank(payPos.recuperaStatoCorrente().getStato())) {
		    statoDB = StatoPagamentoType.fromValue(payPos.recuperaStatoCorrente().getStato());
		}
	    }
	    boolean updateNeeded = false;
	    //verifico se lo stato della posizione è cambiato
	    if (statoDB != null && statoPsp.getStato() != null) {
		updateNeeded = !statoDB.equals(statoPsp.getStato());
	    } else {
		if (log.isWarnEnabled()) {
		    log.warn(
			    "verificaPosizioniPSPImpl impossibile recuperare nei dati del command lo stato attuale della posizione debitoria restituita dal PSP: {}",
			    RiferimentiPosizioniDebitorieHelper.riferimentoPosizioneToString(statoPsp));
		}
	    }
	    //identifico la posizione debitoria letta dal DB per aggiornarne stato e dati di pagamento
	    if (payPos != null) {
		//se ci sono reali modifiche allo stato rispetto a quelle già presenti nel DB
		if (updateNeeded) {
		    this.payStatoPagamentiService.registraStatoPosizioneDebitoria(statoPsp, payPos);
		    //inserimento nuovo stato e dati di pagamento della posizione debitoria + esito richiesta, tutto in unica transazione.
		} else {
		    //anche se non ci sono modifiche allo stato della posizione devo comunque aggiornare PAY_RICHIESTE
		    boolean esitoDefinitivo = !statoPsp.isErroreTemporaneo();
		    //se isErroreTemporaneo() == true le richieste non vengono chiuse anche in caso di esito positivo
		    this.payRichiesteService.aggiornaEsitoRichiestaTrans(payPos.recuperaRichiestaCorrente(), esitoDefinitivo);
		}
		// salvo lo stato nativo sullo stato corrente
		log.debug("statoPsp.getStatoPagamentoNativo() {}", statoPsp.getStatoPagamentoNativo());
		if (StringUtils.isNotBlank(statoPsp.getStatoPagamentoNativo())) {
		    this.payStatoPagamentiService.salvaStatoNativoSuStatoCorrente(payPos, statoPsp.getStatoPagamentoNativo());
		}
	    } else {
		//non dovrebbe verificarsi: i dati della posizione verificata non sono rintracciabili a partire dai valori restituiti dalla verifica
		if (log.isWarnEnabled()) {
		    log.warn(
			    "verificaStatoPosizioniInPSP impossibile recuperare nei dati del command la posizione debitoria restituita dal PSP: {} ,lo stato del pagamento non è stato aggiornato",
			    RiferimentiPosizioniDebitorieHelper.riferimentoPosizioneToString(statoPsp));
		}
	    }
	}
	return esiti;
    }

    @Override
    public PosizioniDebitorieCommand populatePosizioniDebitorieCommand(List<PayRichieste> richieste) {

	log.debug("populatePosizioniDebitorieCommand");
	PosizioniDebitorieCommand cmd = null;
	if (richieste != null) {
	    List<PayRegistrazioniContabili> payRegs = new ArrayList<>();
	    for (PayRichieste ric : richieste) {
		PayPosizioniDebitorie pos = ric.getPosizioneDebitoria();
		log.debug("populatePosizioniDebitorieCommand elaboro pos.getId() {}", pos.getId());
		pos = this.payPosizioniDebitorieService.findById(pos.getId());
		//imposto nella posizione il riferimento alla richiesta (campo transient)
		pos.impostaRichiestaCorrente(ric);
		//ricavo la registrazione contabile dalla posizione debitoria
		PayRegistrazioniContabili reg = pos.getRegistrazioneContabile();
		//fetch dei dati della registrazione contabile con find esplicito per evitare LazyInitializationException
		reg = this.payRegistrazioniContabiliService.findById(reg.getId());
		log.debug("populatePosizioniDebitorieCommand recupero registrazioneContabile reg.getId() {}", reg.getId());
		//fetch del soggetto debitore
		this.paySoggettiDebitoriService.findById(pos.getSoggettoDebitore().getId());
		log.debug("populatePosizioniDebitorieCommand fetch del soggetto debitore {}", pos.getSoggettoDebitore().getId());
		if (!payRegs.contains(reg)) {
		    payRegs.add(reg);
		    reg.setPosizioniDebitorie(new HashSet<PayPosizioniDebitorie>());
		    //ne sovrascrivo il set di posizioni debitorie in relazione per essere sicuro di trasmettere al connettore 
		    //la mia istanza di posizione debitoria in cui ho impostato il riferimento transient della richiesta. 
		    //Hibernate, se non usasse la cache, potrebbe popolare il set delle posizioni nella registrazione contabile con dei proxy 
		    //in cui il riferimento transient alla richietsa non è impostato
		}
		reg.getPosizioniDebitorie().add(pos);
	    }
	    log.debug("populatePosizioniDebitorieCommand prima di popolare il command ");
	    cmd = posizioniDebitorieCommandService.popolaPosizioniDebitorie(payRegs, null);
	}
	return cmd;
    }

    @Override
    public RichiestaSuListaPosizioniCommand populateRichiestaSuListaPosizioniCommand(List<PayRichieste> richieste) {

	RichiestaSuListaPosizioniCommand cmd = null;
	if (richieste != null) {
	    cmd = new RichiestaSuListaPosizioniCommand(null);
	    for (PayRichieste ric : richieste) {
		PayPosizioniDebitorie pos = ric.getPosizioneDebitoria();
		pos = this.payPosizioniDebitorieService.findById(pos.getId());
		//imposto nella posizione il riferimento alla richiesta (campo transient non scrive nel db)
		pos.impostaRichiestaCorrente(ric);
		//ricavo la registrazione contabile dalla posizione debitoria
		PayRegistrazioniContabili reg = pos.getRegistrazioneContabile();
		//fetch dei dati della registrazione contabile con find esplicito per evitare LazyInitializationException
		reg = this.payRegistrazioniContabiliService.findById(reg.getId());
		//fetch del soggetto debitore
		this.paySoggettiDebitoriService.findById(pos.getSoggettoDebitore().getId());
		//e imposto anche un riferimento allo stato corrente della posizione (sempre transient) 
		//se lo stato restituito dal connettore sarà diverso il nuovo stato sarà registrato in PAY_STATO_PAGAMENTI
		PayStatoPagamenti statoPos = this.payStatoPagamentiService.getStatoPosizioneDebitoria(pos);
		pos.impostaStatoCorrente(statoPos);
		cmd.getPosizioni().add(pos);
	    }
	}
	return cmd;
    }

    @Override
    public PayConnectorWsEndpoint getEndpointPerTipoServizio(TipiEvento tipoServizio) {

	PayConnectorWsEndpoint servizio = null;
	if (tipoServizio != null) {
	    PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	    PayConnectorConfig connCfg = profEnte.getPayConnector();
	    switch (tipoServizio) {
	    case ANNULLA_POSIZIONI_PSP:
	    case ANNULLA_PAGAMENTI_OFFLINE_PSP:
		servizio = connCfg.getWsAnnullamento();
		break;
	    case INVIA_POSIZIONI_A_PSP:
		servizio = connCfg.getWsCaricamento();
		break;
	    case VERIFICA_STATO_PAGAMENTO_PSP:
		servizio = connCfg.getWsVerifica();
		break;
	    case RENDICONTAZIONE_PAGAMENTO_PSP:
		servizio = connCfg.getWsNotifica();
		break;
	    case GENERA_AVVISO_PSP:
		servizio = connCfg.getWsAvviso();
		break;
	    default:
		break;
	    }
	}
	return servizio;
    }

    @Override
    public boolean isServizioSoloSchedulato(TipiEvento tipoServizio) {

	PayConnectorWsEndpoint servizio = this.getEndpointPerTipoServizio(tipoServizio);
	if (servizio != null) {
	    return servizio.isSoloSchedulato();
	}
	return false;
    }

    public boolean isSchedulerAttivo(TipiEvento tipoServizio) {

	boolean isAttivo = false;
	PayConnectorWsEndpoint servizio = this.getEndpointPerTipoServizio(tipoServizio);
	if (servizio != null) {
	    isAttivo = StringUtils.isNotBlank(servizio.getQuartzSchedule()) && BooleanUtils.isNotTrue(servizio.getFlagSpegniScheduler());
	}
	return isAttivo;
    }

    @Override
    public void insert(PayConnectorConfig entity) {

	if (this.validateEntity(entity)) {
	    this.payConnectorConfigDAO.insert(entity);
	}
    }

    @Override
    public void update(PayConnectorConfig entity) {

	if (this.validateEntity(entity)) {
	    this.payConnectorConfigDAO.update(entity);
	}
    }

    @Override
    public void delete(PayConnectorConfig entity) {

	if (this.isDeleteAllowed(entity)) {
	    this.payConnectorConfigDAO.delete(entity);
	}
    }

    @Override
    public List<PayConnectorConfig> findAll(Integer firstResult, Integer maxResult) {

	return this.payConnectorConfigDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayConnectorConfig findById(String id) {

	return this.payConnectorConfigDAO.findById(id);
    }

    @Override
    protected Class<PayConnectorConfig> getEntityClass() {

	return PayConnectorConfig.class;
    }

    @Override
    public Map<String, String> reloadConnectorParams() {

	Map<String, String> esiti = new LinkedHashMap<>();
	List<PayProfiliEntiCreditori> configuredProfiles = this.payProfiliEntiCreditoriService.findAll(null, null);
	for (PayProfiliEntiCreditori profiloEnte : configuredProfiles) {
	    PayConnectorConfig cfg = profiloEnte.getPayConnector();
	    String esito = "OK";
	    try {
		IPayConnector payConnectorInstance = getPayConnectorInstance(cfg, true);
		payConnectorInstance.refreshWs(cfg);
	    } catch (PayConfigurationException e1) {
		esito = "ERROR";
		log.error("Errore nella inizializzazione del connettore {}", cfg.getCodice(), e1);
	    }
	    esiti.put(cfg.getCodice(), esito);
	}
	return esiti;
    }

    @Override
    public void modificaDataScadenzaPosizioneInPsp(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	log.info("modificaDataScadenzaPosizioneInPsp - modifica data scadenza per la posizione {}, data {}", idPosizioneDebitoria, nuovaDataScadenza);
	this.getPayConnectorInstance().modificaDataScadenzaPosizioneDebitoria(idPosizioneDebitoria, nuovaDataScadenza);
    }

    @Override
    public void registraCaricamentoMassivoPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, String identificativoOperazione) {

	if (!datiRegistrazioniCommand.getRegistrazioniPosizioni().isEmpty()) {
	    log.info(
		    "registraCaricamentoMassivoPosizioniDebitorie - invio al connettore della richiesta di caricamento di {} registrazioni contabili.",
		    datiRegistrazioniCommand.getRegistrazioniPosizioni().size());
	    for (PayRegistrazioniContabili prc : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
		for (PayPosizioniDebitorie posElaborata : prc.getPosizioniDebitorie()) {
		    PayPosDebMassive entity = new PayPosDebMassive(posElaborata, identificativoOperazione, CaricamentoMassivoStatiEnum.CARICATO);
		    payPosDebMassiveService.insert(entity);
		}
	    }
	} else {
	    log.warn(
		    "registraCaricamentoMassivoPosizioniDebitorie - nessuna posizione valida da trasmettere al connettore: i servizi del PSP non sono stati invocati");
	}
    }

    @Override
    public void modificaDataFineValiditaPosizioneInPsp(Integer idPosizioneDebitoria, Date nuovaDataFineValidita) throws PayException {

	log.info("modificaDataFineValiditaPosizioneInPsp - modifica data fine validita per la posizione {}, data {}", idPosizioneDebitoria,
		nuovaDataFineValidita);
	this.getPayConnectorInstance().modificaDataFineValiditaPosizioneDebitoria(idPosizioneDebitoria, nuovaDataFineValidita);
    }
}
