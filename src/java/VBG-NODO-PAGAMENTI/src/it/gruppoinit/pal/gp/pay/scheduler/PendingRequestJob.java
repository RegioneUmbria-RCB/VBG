/**
 * 
 */
package it.gruppoinit.pal.gp.pay.scheduler;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;
import it.gruppoinit.pal.gp.pay.service.helper.RiferimentiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;

/**
 * Job di Quartz che riceve in input due parametri: tipoServizio e idEnte Interroga la PAY_RICHIESTE per verificare se
 * ci sono richieste da completare per l'ente e il tipo servizio passati in input Se sono presenti richieste da
 * soddisfare il job predispone un command con i dati delle posizioni debitorie da elaborare ed invoca il metodo del
 * connettore corrispondente al tipo di servizio. I codici per i tipi di servizio previsti sono: - INVIA_POSIZIONI_A_PSP
 * per il caricamento di posizioni debitorie - ANNULLA_POSIZIONI_PSP o ANNULLA_PAGAMENTI_OFFLINE_PSP per l'annullamento
 * delle posizioni - VERIFICA_STATO_PAGAMENTO_PSP per la verifica dello stato delle posizioni -
 * RENDICONTAZIONE_PAGAMENTO_PSP per il recupero dei dati di rendicontazione dei pagamenti avvenuti - GENERA_AVVISO_PSP
 * per la richiesta di generazione dell'avviso di pagamento (non ancora implementato)
 * 
 * @author Franco.Leone
 *
 */
@Transactional
public class PendingRequestJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(PendingRequestJob.class);
    public static final String CONTEXT_PARAM_TIPO_SERVIZIO = "TIPO_SERVIZIO";
    public static final String CONTEXT_PARAM_ID_ENTE = "ID_ENTE";
    public static final String CONTEXT_PARAM_APPLICATION_CONTEXT = "APPLICATION_CONTEXT";
    private PayProfiliEntiCreditoriService entiService = null;
    private PayRichiesteService richiesteService = null;
    private PayConnectorService connectorService = null;
    private ConfigurazionePagamentiService configService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

	PayProfiliEntiCreditori profEnte = null;
	String tipoRichiesta = (String) context.getMergedJobDataMap().get(CONTEXT_PARAM_TIPO_SERVIZIO);
	if (StringUtils.isBlank(tipoRichiesta))
	    throw new JobExecutionException("parametro " + CONTEXT_PARAM_TIPO_SERVIZIO + " mancante");
	String idEnte = (String) context.getMergedJobDataMap().get(CONTEXT_PARAM_ID_ENTE);
	if (StringUtils.isBlank(idEnte))
	    throw new JobExecutionException("parametro " + CONTEXT_PARAM_ID_ENTE + " mancante");
	ApplicationContext ctx = (ApplicationContext) context.getMergedJobDataMap().get(CONTEXT_PARAM_APPLICATION_CONTEXT);
	if (ctx == null)
	    throw new JobExecutionException("parametro " + CONTEXT_PARAM_APPLICATION_CONTEXT + " mancante");
	try {
	    if (this.entiService == null) {
		this.entiService = ctx.getBean(PayProfiliEntiCreditoriService.class);
	    }
	    if (this.richiesteService == null) {
		this.richiesteService = ctx.getBean(PayRichiesteService.class);
	    }
	    if (this.connectorService == null) {
		this.connectorService = ctx.getBean(PayConnectorService.class);
	    }
	    if (this.configService == null) {
		this.configService = ctx.getBean(ConfigurazionePagamentiService.class);
		//impostazioni threadLocal che derivano dal profilo ente creditore
		profEnte = this.configService.configuraRequestPerEnteCreditore(idEnte);
	    }
	    TipiEvento tipoEvt = TipiEvento.fromValue(tipoRichiesta);
	    List<PayRichieste> richieste = richiesteService.getRichiesteApertePerTipoEProfiloEnte(profEnte, tipoEvt);
	    log.info("execute - elaborazione richieste in sospeso di tipo {} per il profilo ente {}. Richieste da elaborare: {}", tipoRichiesta,
		    idEnte, richieste.size());
	    if (!richieste.isEmpty()) {
		ElencoPosizioniDebitorieEsitoType esitoPos = null;
		ElencoStatoPosizioniType esitoVerifica = null;
		//se ci sono richieste pendenti da completare popolo il command ed invoco il servizio del connettore specifico per il tipo richiesta che sto elaborando
		if (tipoEvt.equals(TipiEvento.INVIA_POSIZIONI_A_PSP)) {
		    PosizioniDebitorieCommand posCmd = this.connectorService.populatePosizioniDebitorieCommand(richieste);
		    log.info("execute - invocazione di registraPosizioniDebitorieInPSP per {} richieste pendenti per il profilo ente {}",
			    richieste.size(), idEnte);
		    esitoPos = connectorService.registraPosizioniDebitorieInPSP(posCmd);
		} else if (tipoEvt.equals(TipiEvento.ANNULLA_POSIZIONI_PSP) || tipoEvt.equals(TipiEvento.ANNULLA_PAGAMENTI_OFFLINE_PSP)) {
		    //TODO assicurarsi che le richieste di annullamento posizioni pagate offline registrino richieste del tipo ANNULLA_PAGAMENTI_OFFLINE_PSP
		    PosizioniDebitorieCommand posCmd = this.connectorService.populatePosizioniDebitorieCommand(richieste);
		    log.info("execute - invocazione di annullaPosizioniDebitorieInPSP per {} richieste pendenti per il profilo ente {}",
			    richieste.size(), idEnte);
		    esitoPos = connectorService.annullaPosizioniDebitorieInPSP(posCmd, tipoEvt.equals(TipiEvento.ANNULLA_PAGAMENTI_OFFLINE_PSP));
		    //l'annullamento delle posizioni pagate offline prevede il passaggio di un diverso parametro al connettore
		    //che può usarlo nel caso in cui il PSP esponga servizi specifici per questi casi perciò occorre che il job sia schedulato due volte 
		    //con entrambi i tipi di richiesta in input per gestire separatamente gli annullamenti normali da quelli dei pagamenti offline
		} else if (tipoEvt.equals(TipiEvento.VERIFICA_STATO_PAGAMENTO_PSP)) {
		    RichiestaSuListaPosizioniCommand statoCmd = this.connectorService.populateRichiestaSuListaPosizioniCommand(richieste);
		    log.info("execute - invocazione di verificaStatoPosizioniInPSP per {} richieste pendenti per il profilo ente {}",
			    richieste.size(), idEnte);
		    esitoVerifica = this.connectorService.verificaStatoPosizioniInPSP(statoCmd);
		} else if (tipoEvt.equals(TipiEvento.RENDICONTAZIONE_PAGAMENTO_PSP)) {
		    RichiestaSuListaPosizioniCommand statoCmd = this.connectorService.populateRichiestaSuListaPosizioniCommand(richieste);
		    log.info("execute - invocazione di rendicontazionePagamentiPSP per {} richieste pendenti per il profilo ente {}",
			    richieste.size(), idEnte);
		    esitoVerifica = this.connectorService.rendicontazionePagamentiPSP(statoCmd);
		} else if (tipoEvt.equals(TipiEvento.GENERA_AVVISO_PSP)) {
		    //TODO non ancora implementato
		} else if (tipoEvt.equals(TipiEvento.GENERA_FATTURA_PSP)) {
		    //TODO non ancora implementato
		} else if (tipoEvt.equals(TipiEvento.RECUPERA_RICEVUTA_PSP)) {
		    //TODO non ancora implementato
		} else {
		    throw new JobExecutionException(
			    "errore nell'elaborazione delle richieste in sospeso. Tipo richiesta non supportato: " + tipoRichiesta);
		}
		if (esitoPos != null) {
		    new RiferimentiPosizioniDebitorieHelper(esitoPos);
		} else if (esitoVerifica != null) {
		    new RiferimentiPosizioniDebitorieHelper(esitoVerifica);
		} else {
		    //TODO gestione esito generazione avvisi
		}
	    }
	} catch (Exception e) {
	    log.error("execute - errore nell'elaborazione delle richieste in sospeso di tipo " + tipoRichiesta + " per il profilo ente " + idEnte +
		      ". Errore: " + e.getMessage(),
		    e);
	    throw new JobExecutionException(e);
	}
    }
}