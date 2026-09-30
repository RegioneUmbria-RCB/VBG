package it.gruppoinit.pal.gp.core.service.helper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.EventoDocumentoIstanzaStcDisponibile;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.EventoDocumentoMovimentoStcDisponibile;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.NlaManager;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.init.sigepro.rte.types.SportelloType;

@Component
public class DownloadFileSTCAsincronoHelper implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(DownloadFileSTCAsincronoHelper.class);
    private TaskExecutor taskExecutor;
    private long sleepTimeBeforeExecute = 10000;
    private IstanzeeventiService istanzeeventiService;
    private IstanzeService istanzeService;
    private IEventPublisher eventPublisher;
    private MovimentiService movimentiService;
    // BEGIN variabili ORMHelper // USATE SOLO NEL METODO RUN
    private String idcomuneAlias;
    private String idcomune;
    private String software;
    private String token;
    private String hibernateSfKey;
    // END VARIABILI ORMHELPER
    // codiceIstanza USATA SOLAMENTE nel metodo RUN
    private Integer codiceIstanza;
    private Integer codiceMovimento;
    private SportelloType sportelloCheHaInviatoIFile;
    private String idPraticaDestinatario;
    private String idAttivitaDestinatario;

    public DownloadFileSTCAsincronoHelper() {

	super();
    }

    private DownloadFileSTCAsincronoHelper(Integer codiceIstanza, String idcomuneAlias, String idcomune, String software, String token,
	    String hibernateSfKey, Integer codiceMovimento, SportelloType sportelloMittente, String idPraticaDestinatario,
	    String idAttivitaDestinatario) {

	this();
	this.idcomuneAlias = idcomuneAlias;
	this.idcomune = idcomune;
	this.software = software;
	this.token = token;
	this.hibernateSfKey = hibernateSfKey;
	this.codiceIstanza = codiceIstanza;
	this.codiceMovimento = codiceMovimento;
	this.sportelloCheHaInviatoIFile = sportelloMittente;
	this.idPraticaDestinatario = idPraticaDestinatario;
	this.idAttivitaDestinatario = idAttivitaDestinatario;
    }

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {

	this.taskExecutor = taskExecutor;
    }

    public synchronized void eseguiTaskPerIstanza(Integer codiceIstanza, String idcomunealias, String idcomune, String software, String token,
	    String hibernateSfKey, SportelloType sportello, String idPraticaDestinatario, String idAttivitaDestinatario) {

	if (log.isDebugEnabled()) {
	    log.debug("eseguiTaskPerIstanza# entro nel metodo");
	}
	try {
	    if (codiceIstanza != null) {
		taskExecutor.execute(new DownloadFileSTCAsincronoHelper(codiceIstanza, idcomunealias, idcomune, software, token, hibernateSfKey, null,
			sportello, idPraticaDestinatario, idAttivitaDestinatario));
	    } else {
		log.debug("Non ci sono notifiche per la pratica {}-{}", idcomune, codiceIstanza);
	    }
	} catch (Exception e) {
	    log.error("eseguiTaskPerIstanza# Errore nell' esecuzione della notifica automatica per l'istanza {}-{}: {}",
		    new Object[] { idcomune, codiceIstanza, e });
	    try {
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		istanzeeventiService.insert("Errore il download dei file dell' istanza: " + StringUtils.left(e.getMessage(), 3900),
			IstanzeeventiConstants.CATEGORIA_STC, null, istanza);
	    } catch (Exception ex) {
		log.error("Errore nell'inserimento dell'evento per l'istanza:{}-{}: {}", new Object[] { idcomune, codiceIstanza, ex });
	    }
	} finally {
	}
    }

    public synchronized void eseguiTaskPerMovimento(Integer codiceMovimento, String idcomunealias, String idcomune, String software, String token,
	    String hibernateSfKey, SportelloType sportelloMittente, String idPraticaDestinatario, String idAttivitaDestinatario) {

	if (log.isDebugEnabled()) {
	    log.debug("eseguiTaskPerMovimento# entro nel metodo");
	}
	try {
	    if (codiceMovimento != null) {
		taskExecutor.execute(new DownloadFileSTCAsincronoHelper(null, idcomunealias, idcomune, software, token, hibernateSfKey,
			codiceMovimento, sportelloMittente, idPraticaDestinatario, idAttivitaDestinatario));
	    }
	} catch (Exception e) {
	    log.error("eseguiTaskPerMovimento# Errore nell' esecuzione del download allegati stc per il movimento {}-{}: {}",
		    new Object[] { idcomune, codiceMovimento, e });
	    try {
		Movimenti movimenti = movimentiService.findById(new PkId(codiceMovimento));
		istanzeeventiService.insert("Errore il download dei file del movimento: " + StringUtils.left(e.getMessage(), 3900),
			IstanzeeventiConstants.CATEGORIA_STC, movimenti, null);
	    } catch (Exception ex) {
		log.error("Errore nell'inserimento dell'evento per l'istanza:{}-{}: {}", new Object[] { idcomune, codiceMovimento, ex });
	    }
	} finally {
	}
    }

    @Override
    public void run() {

	if (codiceIstanza == null && codiceMovimento == null) {
	    return;
	}
	if (log.isDebugEnabled()) {
	    log.debug("run# Setto le variabili ORMHelper idcomunealias: {}, idcomune: {}, software: {}",
		    new Object[] { idcomuneAlias, idcomune, software });
	}
	ORMHelper.setIdcomuneAlias(idcomuneAlias);
	ORMHelper.setIdcomune(idcomune);
	ORMHelper.setHibernateSFKey(hibernateSfKey);
	ORMHelper.setSoftware(software);
	ORMHelper.setToken(token);
	if (log.isDebugEnabled()) {
	    log.debug("run# recupero i service");
	}
	SecurityContext context = SecurityContextHolder.getContext();
	Authentication authentication = context.getAuthentication();
	if (authentication == null) {
	    UserSecurityService ss = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext().getBean("userSecurityService",
		    UserSecurityService.class);
	    try {
		UserDetails ud = ss.loadAdministratorUser();
		if (log.isDebugEnabled()) {
		    log.debug("run# carico l'utente amministratore");
		}
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ud, "", ud.getAuthorities()));
	    } catch (Exception e) {
		log.error("Non è stato possibile recuperare l'utente autenticato a causa di: {}", e);
		throw new SecurityException("Non è stato possibile recuperare l'utente autenticato a causa di: " + e.getMessage(), e);
	    }
	}
	VerticalizzazioniService vps = (VerticalizzazioniService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("verticalizzazioniServiceImpl", VerticalizzazioniService.class);
	if (log.isDebugEnabled()) {
	    log.debug("run# verticalizzazioniService recuperato {}", vps != null);
	}
	long sleepTimeBeforeExecuteConfigurato = this.sleepTimeBeforeExecute;
	if (vps != null) {
	    Verticalizzazioniparametri sleepP = vps.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_NOTIFICHE_AUT_ATTESA_MILLIS);
	    if (sleepP != null) {
		if (StringUtils.isNotBlank(StringUtils.defaultString(sleepP.getValore()).trim())) {
		    try {
			Long sleepValue = Long.parseLong(sleepP.getValore().trim());
			sleepTimeBeforeExecuteConfigurato = sleepValue.longValue();
		    } catch (Exception e) {
			log.error("Errore nella configurazione del parametro {} della verticalizzazione STC, Valore non valido={} ",
				WebConstants.VERTICALIZZAZIONE_STC_NOTIFICHE_AUT_ATTESA_MILLIS, sleepP.getValore());
		    }
		}
	    }
	}
	if (codiceMovimento != null) {
	    sleepTimeBeforeExecuteConfigurato += 2000;
	}
	if (log.isDebugEnabled()) {
	    log.debug("run# lancio Thread.sleep({})", sleepTimeBeforeExecuteConfigurato);
	}
	try {
	    Thread.sleep(sleepTimeBeforeExecuteConfigurato);
	} catch (InterruptedException e) {
	    ORMHelper.destroyORMHelper();
	    log.error("run# errore nella chiamata a Thread.sleep non mando in esecuzione il restante codice: {}", e);
	    return;
	}
	if (log.isDebugEnabled()) {
	    log.debug("run# movimentiservice recuperato {}", movimentiService != null);
	}
	IstanzeeventiService ieventiService = (IstanzeeventiService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("istanzeeventiServiceImpl", IstanzeeventiService.class);
	if (log.isDebugEnabled()) {
	    log.debug("run# ieventiService recuperato {}", ieventiService != null);
	}
	IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean isInserimentoDaStc = false;
	boolean isInserimentoDaStcDiretto = false;
	if (rules != null) { // recupero i valori delle business rule per reimpostarle dopo l'esecuzione del blocco
	    isInserimentoDaStcDiretto = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name());
	    isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
	}
	NlaManager nlaManager = (NlaManager) ContextLoader.getCurrentWebApplicationContext().getBean("nlaManagerImpl", NlaManager.class);
	if (log.isDebugEnabled()) {
	    log.debug("run# movimentiservice recuperato {}", movimentiService != null);
	}
	if (codiceIstanza != null) {
	    //
	    // scarico gli allegati per l'istanza
	    if (log.isDebugEnabled()) {
		log.debug("run# SCARICO GLI ALLEGATI STC PER L'ISTANZA {}-{}", codiceIstanza, ORMHelper.getIdcomune());
	    }
	    nlaManager.downloadAllegatiSTCIstanza(codiceIstanza, sportelloCheHaInviatoIFile, idPraticaDestinatario);
	    this.eventPublisher.publish(new EventoDocumentoIstanzaStcDisponibile(codiceIstanza));
	}
	if (codiceMovimento != null) {
	    //
	    // scarico gli allegati per il movimento
	    if (log.isDebugEnabled()) {
		log.debug("run# SCARICO GLI ALLEGATI STC PER IL MOVIMENTO {}-{}", codiceMovimento, ORMHelper.getIdcomune());
	    }
	    nlaManager.downloadAllegatiSTCMovimento(codiceMovimento, sportelloCheHaInviatoIFile, idPraticaDestinatario, idAttivitaDestinatario);
	    this.eventPublisher.publish(new EventoDocumentoMovimentoStcDisponibile(codiceMovimento));
	}
	// rilascio l'ORMHelper
	if (log.isDebugEnabled()) {
	    log.debug("run# terminata l'esecuzione del task di notifica automatica per l'istanza {}-{}", idcomune, codiceIstanza);
	}
	if (rules != null) {
	    // riassegno le business rules ereditate
	    rules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), isInserimentoDaStc);
	    rules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name(), isInserimentoDaStcDiretto);
	}
	ORMHelper.destroyORMHelper();
    }
}
