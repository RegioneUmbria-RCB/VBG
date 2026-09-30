package it.gruppoinit.pal.gp.core.service.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiBaseService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.helper.async.BaseOperazioniAutomaticheAsync;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

@Component
public class NotificheAutomaticheAsincroneHelper extends BaseOperazioniAutomaticheAsync {

    private static final Logger log = LoggerFactory.getLogger(NotificheAutomaticheAsincroneHelper.class);
    private Map<String, List<Integer>> stack = new HashMap<String, List<Integer>>();
    private TaskExecutor taskExecutor;
    private long sleepTimeBeforeExecute = 10000;
    private IstanzeeventiService istanzeeventiService;
    private IstanzeService istanzeService;
    // codiceIstanza USATA SOLAMENTE nel metodo RUN
    private Integer codiceIstanza;
    private List<Integer> codiciMovimento;

    // end
    public NotificheAutomaticheAsincroneHelper() {

	super();
    }

    /**
     * Costruttore usato solo all'interno della classe per l'executor
     * 
     * @param codiceIstanza
     * @param idcomuneAlias
     * @param idcomune
     * @param software
     * @param token
     * @param hibernateSfKey
     */
    private NotificheAutomaticheAsincroneHelper(Integer codiceIstanza, String idcomuneAlias, String idcomune, String software, String token,
	    String hibernateSfKey, List<Integer> codiciMovimento) {

	this();
	this.idcomuneAlias = idcomuneAlias;
	this.idcomune = idcomune;
	this.software = software;
	this.token = token;
	this.hibernateSfKey = hibernateSfKey;
	this.codiceIstanza = codiceIstanza;
	this.codiciMovimento = codiciMovimento;
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
    public void setTaskExecutor(TaskExecutor taskExecutor) {

	this.taskExecutor = taskExecutor;
    }

    /**
     * 
     * @return
     */
    public Map<String, List<Integer>> getStack() {

	return stack;
    }

    public synchronized void eseguiTaskPerIstanza(Integer codiceIstanza, String idcomunealias, String idcomune, String software, String token,
	    String hibernateSfKey) {

	String key = getStackKey(idcomunealias, idcomune, codiceIstanza);
	if (log.isDebugEnabled()) {
	    log.debug("eseguiTaskPerIstanza# stackKey: {}", key);
	}
	try {
	    List<Integer> codiciMovimentoLocal = getStack().get(key);
	    if (codiciMovimentoLocal != null) {
		taskExecutor.execute(new NotificheAutomaticheAsincroneHelper(codiceIstanza, idcomunealias, idcomune, software, token, hibernateSfKey,
			codiciMovimentoLocal));
	    } else {
		log.debug("Non ci sono notifiche per la pratica {}-{}", idcomune, codiceIstanza);
	    }
	} catch (Exception e) {
	    log.error("eseguiTaskPerIstanza# Errore nell' esecuzione della notifica automatica per l'istanza {}-{}: {}",
		    new Object[] { idcomune, codiceIstanza, e });
	    try {
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		istanzeeventiService.insert("Errore nell' esecuzione della notifica automatica: " + StringUtils.left(e.getMessage(), 3900),
			IstanzeeventiConstants.CATEGORIA_STC_NAUT, null, istanza);
	    } catch (Exception ex) {
		log.error("Errore nell'inserimento dell'evento per l'istanza:{}-{}: {}", new Object[] { idcomune, codiceIstanza, ex });
	    }
	} finally {
	    if (log.isDebugEnabled()) {
		log.debug("eseguiTaskPerIstanza# elimino lo stack per l'istanza {}-{}", idcomune, codiceIstanza);
	    }
	    getStack().remove(key);
	}
    }

    public String getStackKey(String idcomunealias, String idcomune, Integer codiceIstanza) {

	return idcomunealias + "-" + idcomune + "-" + codiceIstanza;
    }

    @Override
    public void runInternal() {

	log.debug("run# eseguo le notifiche automatiche per l'istanza [{}-{}], alias {}, software {}, codici movimento {}",
		new Object[] { idcomune, codiceIstanza, idcomuneAlias, software, codiciMovimento });
	if (this.codiciMovimento == null || this.codiciMovimento.isEmpty()) {
	    return;
	}
	if (log.isDebugEnabled()) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    log.debug("run# verifica authentication {}", authentication);
	}
	VerticalizzazioniService vps = (VerticalizzazioniService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("verticalizzazioniServiceImpl", VerticalizzazioniService.class);
	log.debug("run# verticalizzazioniService recuperato {}", vps != null);
	long sleepTimeBeforeExecuteConfigurato = this.sleepTimeBeforeExecute;
	if (vps != null) {
	    Verticalizzazioniparametri sleepP = vps.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_NOTIFICHE_AUT_ATTESA_MILLIS);
	    if (sleepP != null && StringUtils.isNotBlank(StringUtils.defaultString(sleepP.getValore()).trim())) {
		try {
		    Long sleepValue = Long.parseLong(sleepP.getValore().trim());
		    sleepTimeBeforeExecuteConfigurato = sleepValue.longValue();
		} catch (Exception e) {
		    log.error("Errore nella configurazione del parametro {} della verticalizzazione STC, Valore non valido={} ",
			    WebConstants.VERTICALIZZAZIONE_STC_NOTIFICHE_AUT_ATTESA_MILLIS, sleepP.getValore());
		}
	    }
	}
	log.debug("run# lancio Thread.sleep({})", sleepTimeBeforeExecuteConfigurato);
	try {
	    Thread.sleep(sleepTimeBeforeExecuteConfigurato);
	} catch (InterruptedException e) {
	    ORMHelper.destroyORMHelper();
	    log.error("run# errore nella chiamata a Thread.sleep non mando in esecuzione il restante codice: {}", e.getMessage(), e);
	    return;
	}
	// recupero i service
	MovimentiNoSecurityService movimentiNoSecurityService = (MovimentiNoSecurityService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("movimentiNoSecurityServiceImpl", MovimentiNoSecurityService.class);
	MovimentiService movimentiService = (MovimentiService) ContextLoader.getCurrentWebApplicationContext().getBean("movimentiServiceImpl",
		MovimentiService.class);
	log.debug("run# movimentiNoSecurityService recuperato {}", movimentiNoSecurityService != null);
	log.debug("run# movimentiService recuperato {}", movimentiService != null);
	IstanzeeventiService ieventiService = (IstanzeeventiService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("istanzeeventiServiceImpl", IstanzeeventiService.class);
	log.debug("run# ieventiService recuperato {}", ieventiService != null);
	IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean isInserimentoDaStc = false;
	boolean isInserimentoDaStcDiretto = false;
	if (rules != null) { // recupero i valori delle business rule per reimpostarle dopo l'esecuzione del blocco
	    isInserimentoDaStcDiretto = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name());
	    isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
	}
	for (Integer codiceMovimento : codiciMovimento) {
	    log.debug("run# cerco il movimento {} per l'istanza {}, isInserimentoDaStcDiretto {}, isInserimentoDaStc {}",
		    new Object[] { codiceMovimento, codiceIstanza, isInserimentoDaStcDiretto, isInserimentoDaStc });
	    Movimenti mov = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
	    log.debug("run# movimento per l'istanza {}, trovato? {}", codiceIstanza, mov != null);
	    if (mov != null && mov.getIstanza() != null && mov.getIstanza().getId().getCodice() != null) {
		if (idcomune.equals(StringUtils.defaultString(mov.getIstanza().getId().getIdcomune()))
			&& codiceIstanza.equals(mov.getIstanza().getId().getCodice())) {
		    int inviato = (mov.getInviatoConStc() == null ? MovimentiBaseService.STC_NON_INVIATO : mov.getInviatoConStc().intValue());
		    if (inviato == MovimentiBaseService.STC_NON_INVIATO) {
			try {
			    log.debug("run# invio la notifica cm{}, ci{}", codiceMovimento, codiceIstanza);
			    movimentiService.notificaStc(mov);
			    log.debug("run# movimento notificato con successo  cm{}, ci{}", codiceMovimento, codiceIstanza);
			} catch (Exception e) {
			    log.error("run# errore nella notifica automatica del movimento {} {}",
				    new Object[] { codiceIstanza, codiceMovimento, e.getMessage() }, e);
			    try {
				ieventiService.insert("errore nella notifica automatica del movimento " + StringUtils.left(e.getMessage(), 3900),
					IstanzeeventiConstants.CATEGORIA_STC_NAUT, mov, null);
			    } catch (Exception ex) {
				log.error("run# errore nella inserimento dell'evento {}-{}-{}",
					new Object[] { idcomune, codiceMovimento, codiceIstanza });
			    }
			}
		    } else {
			log.warn("run# il movimento cm:{}-ci:{} risulta già notificato.", codiceMovimento, codiceIstanza);
		    }
		} else {
		    // istanzeeventi il codice movimento non appartiene all'istanza
		    log.error("run# il movimento {}-{} non appartiene all'istanza: {}", new Object[] { idcomune, codiceMovimento, codiceIstanza });
		}
	    }
	}
	// recupero i movimenti da eseguire con notifica automatica
	// per ogni movimento eseguo la notifica automatica
	// rilascio l'ORMHelper
	log.debug("run# terminata l'esecuzione del task di notifica automatica per l'istanza {}-{}", idcomune, codiceIstanza);
	if (rules != null) {
	    // riassegno le business rules ereditate
	    rules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), isInserimentoDaStc);
	    rules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name(), isInserimentoDaStcDiretto);
	}
	ORMHelper.destroyORMHelper();
    }

    @Override
    public Logger getLogger() {

	return log;
    }
}
