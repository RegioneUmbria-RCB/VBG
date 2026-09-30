package it.gruppoinit.pal.gp.core.features.bollettazione.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneFactory;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneIstanzeService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneMercatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloFactory;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy.TIPO_PERIODO;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;
import it.gruppoinit.pal.gp.core.service.BollCfgTipoService;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

public class CreazioneBollettazioneJob extends BaseJob implements StatefulJob {

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("{} start.....", getClass().getName());
	String id = String.valueOf(UUID.randomUUID().toString());
	LoggerUpdaterecord.log("######################################################################");
	LoggerUpdaterecord.log(id + "==>" + getClass().getName() + " START AT: " + Utilities.formatDate(new Date(), true));
	String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	setORMHelper(idcomunealias, software);
	BollCfgTipoService bollCfgTipoService = (BollCfgTipoService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("bollCfgTipoServiceImpl");
	CalcoloBollettazioneService calcoloBollettazioneService = (CalcoloBollettazioneService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("calcoloBollettazioneServiceImpl");
	CalcoloBollettazioneIstanzeService calcoloBollettazioneIstanzeService = (CalcoloBollettazioneIstanzeService) ContextLoader
		.getCurrentWebApplicationContext().getBean("calcoloBollettazioneIstanzeServiceImpl");
	CalcoloBollettazioneMercatiService calcoloBollettazioneMercatiService = (CalcoloBollettazioneMercatiService) ContextLoader
		.getCurrentWebApplicationContext().getBean("calcoloBollettazioneMercatiServiceImpl");
	CreazioneBollTestata creazioneBollTestata = popolaTestata(ctx, bollCfgTipoService);
	String codiceResponsabile = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.CODICE_RESPONSABILE.name());
	Integer responsabile = null;
	if (Utilities.isInteger(codiceResponsabile)) {
	    responsabile = Integer.parseInt(codiceResponsabile);
	}
	try {
	    CalcoloBollettazioneFactory calcoloBollettazioneFactory = new CalcoloBollettazioneFactory(bollCfgTipoService,
		    calcoloBollettazioneIstanzeService, calcoloBollettazioneMercatiService);
	    Integer idBollettazione = calcoloBollettazioneFactory.creaBollettazione(creazioneBollTestata, responsabile);
	    boolean inviaPosizioni = inviaPosizioni(ctx);
	    LoggerUpdaterecord.log(id + "==>" + getClass().getName() + " creata bollettazione con id: " + idBollettazione + ", inviaPosizioni: " +
				   inviaPosizioni + " - " + Utilities.formatDate(new Date(), true));
	    if (inviaPosizioni && idBollettazione != null) {
		calcoloBollettazioneService.validaInteraBollettazione(idBollettazione, true, codiceResponsabile);
		LoggerUpdaterecord.log(id + "==>" + getClass().getName() + " Validazione effettuata per la bollettazione con id: " + idBollettazione +
			       ", " + Utilities.formatDate(new Date(), true));
		String errori = calcoloBollettazioneService.inviaNodoPagamenti(idBollettazione);
		if (StringUtils.isNotBlank(errori)) {
		    throw new FunzioneBusinessRemotaException("Errore nell'invio delle posizioni al nodo dei pagamenti: " + errori);
		}
		LoggerUpdaterecord.log(id + "==>" + getClass().getName() + " Posizioni inviate al nodo dei pagamenti con id: " + idBollettazione +
				       ", " + Utilities.formatDate(new Date(), true));
	    }
	} catch (Exception e) {
	    log.error("Errore nell'invio al nodo dei pagamenti: ", e);
	    LoggerUpdaterecord.log("######################################################################");
	    LoggerUpdaterecord.log(id + "==>" + getClass().getName() + " ERRORE: " + e.getMessage());
	    String errore = erroreCreazioneBollettazione(e);
	    verificaEdInviaMail(ctx, id, errore);
	}
	LoggerUpdaterecord.log(id + "==>" + getClass().getName() + " STOP AT: " + Utilities.formatDate(new Date(), true));
	LoggerUpdaterecord.log(id + "==>##########################################################################");
	log.info("{} end.....", getClass().getName());
    }

    private String erroreCreazioneBollettazione(Exception e) {

	StringBuilder errore = new StringBuilder("Si è verificato un errore nella creazione della bollettazione: <br />");
	errore.append("- " + e.getMessage()).append("<br />StackTrace: ");
	errore.append(popolaStacktrace(e, 0));
	return errore.toString();
    }

    private String popolaStacktrace(Throwable e, int level) {

	StringBuilder errore = new StringBuilder("Causato da: <br />");
	StackTraceElement[] stackTrace = e.getStackTrace();
	for (StackTraceElement stackTraceElement : stackTrace) {
	    errore.append("- ") //
		    .append(stackTraceElement.getClassName()).append("#") //
		    .append(stackTraceElement.getMethodName()).append("(") //
		    .append(stackTraceElement.getLineNumber()).append(")");
	}
	if (level < 3 && e.getCause() != null) { // solo tre ricorsioni max per evitare loop
	    errore.append(popolaStacktrace(e.getCause(), ++level));
	}
	return errore.toString();
    }

    private CreazioneBollTestata popolaTestata(JobExecutionContext ctx, BollCfgTipoService bollCfgTipoService) {

	CreazioneBollTestata ret = new CreazioneBollTestata();
	String bollCfgTipo = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.TIPO_BOLLETTAZIONE.name());
	Integer bollCfgTipoId = null;
	if (Utilities.isInteger(bollCfgTipo)) {
	    bollCfgTipoId = Integer.parseInt(bollCfgTipo);
	}
	ret.setBollCfgTipoId(bollCfgTipoId);
	BollCfgTipo bollCfgTipoBean = bollCfgTipoService.findById(new PkId(bollCfgTipoId));
	IntervalloDate intervalloDate = getIntervalloDate(bollCfgTipoBean, ctx, bollCfgTipoService);
	ret.setIntervalloDate(intervalloDate);
	ret.setDescrizione(formattaDescrizione(ctx, bollCfgTipoBean, intervalloDate));
	ret.setComuni(getComuni(ctx));
	ret.setInterventi(getInterventi(ctx));
	ret.setEndoprocedimenti(getEndoProcedimenti(ctx));
	return ret;
    }

    private boolean inviaPosizioni(JobExecutionContext ctx) {

	return StringUtils.defaultIfEmpty((String) ctx.getJobDetail().getJobDataMap().get(PARAMS.INVIA_POSIZIONI.name()), "false") //
		.equalsIgnoreCase("true");
    }

    private Set<Integer> getEndoProcedimenti(JobExecutionContext ctx) {

	return paramToIntegerSet(ctx, PARAMS.LISTA_ENDOPROCEDIMENTI);
    }

    private Set<String> getInterventi(JobExecutionContext ctx) {

	return paramToSet(ctx, PARAMS.LISTA_INTERVENTI);
    }

    private Set<String> getComuni(JobExecutionContext ctx) {

	return paramToSet(ctx, PARAMS.LISTA_CODICI_COMUNI);
    }

    private Set<String> paramToSet(JobExecutionContext ctx, PARAMS param) {

	String ret = (String) ctx.getJobDetail().getJobDataMap().get(param.name());
	Set<String> s = new HashSet<String>();
	if (StringUtils.isNotBlank(ret)) {
	    String[] v = ret.split(",");
	    if (v != null && v.length > 0) {
		for (String cod : v) {
		    s.add(cod);
		}
	    }
	}
	return s;
    }

    private Set<Integer> paramToIntegerSet(JobExecutionContext ctx, PARAMS param) {

	String ret = (String) ctx.getJobDetail().getJobDataMap().get(param.name());
	Set<String> s = paramToSet(ctx, param);
	if (StringUtils.isNotBlank(ret)) {
	    String[] v = ret.split(",");
	    if (v != null && v.length > 0) {
		for (String cod : v) {
		    s.add(cod);
		}
	    }
	}
	Set<Integer> iret = new HashSet<Integer>();
	for (String stringVal : s) {
	    if (Utilities.isInteger(stringVal)) {
		iret.add(Integer.parseInt(stringVal));
	    }
	}
	return iret;
    }

    private IntervalloDate getIntervalloDate(BollCfgTipo bollCfgTipo, JobExecutionContext ctx, BollCfgTipoService bollCfgTipoService) {

	String tipoPeriodo = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.TIPOLOGIA_PERIODO.name());
	String periodo = bollCfgTipo.getPeriodo();
	PeriodiEnum p = PeriodiEnum.fromValue(periodo);
	CalcoloIntervalloFactory calcoloIntervalloFactory = new CalcoloIntervalloFactory();
	CalcoloIntervalloStrategy intervalloStrategy = calcoloIntervalloFactory.get(p);
	TIPO_PERIODO tipop = TIPO_PERIODO.valueOf(tipoPeriodo);
	Calendar c = Calendar.getInstance();
	return intervalloStrategy.getIntervallo(intervalloStrategy.calcolaDataInizioOperazioneDaDataRiferimento(tipop, c.getTime())); // la data inizio operazione va calcolata con il tipo periodo 
    }

    private static final String SEGNAPOSTO_TIPO_BOLLETTAZIONE = "#TIPO_BOLLETTAZIONE#";
    private static final String SEGNAPOSTO_DALLA_DATA = "#DALLA_DATA#";
    private static final String SEGNAPOSTO_ALLA_DATA = "#ALLA_DATA#";
    private static final String DEFAULT_DESCRIZIONE_BOLLETTAZIONE = SEGNAPOSTO_TIPO_BOLLETTAZIONE + // 
								    " periodo " + SEGNAPOSTO_DALLA_DATA + //
								    " - " + SEGNAPOSTO_ALLA_DATA;

    private String formattaDescrizione(JobExecutionContext ctx, BollCfgTipo bollCfgTipoBean, IntervalloDate intervalloDate) {

	String paramDescrizione = StringUtils.defaultIfEmpty((String) ctx.getJobDetail().getJobDataMap().get(PARAMS.DESCRIZIONE_BOLLETTAZIONE.name()),
		DEFAULT_DESCRIZIONE_BOLLETTAZIONE);
	String dallaData = Utilities.formatDate(intervalloDate.getDataInizio(), false);
	String allaData = Utilities.formatDate(intervalloDate.getDataFine(), false);
	return paramDescrizione.replace(SEGNAPOSTO_DALLA_DATA, dallaData).replace(SEGNAPOSTO_ALLA_DATA, allaData)
		.replace(SEGNAPOSTO_TIPO_BOLLETTAZIONE, bollCfgTipoBean.getDescrizione());
    }

    private void verificaEdInviaMail(JobExecutionContext ctx, String id, String report) {

	try {
	    log.info("verificaEdInviaMail ");
	    MailtipoService mailtipoService = (MailtipoService) ContextLoader.getCurrentWebApplicationContext().getBean("mailtipoServiceImpl");
	    MailServiceWSClient mailServiceWSClient = (MailServiceWSClient) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("mailServiceWSClient");
	    String accountId = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.MAIL_ACCOUNT.name());
	    String templateId = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.MAIL_TIPO.name());
	    String destinatario = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.MAIL_CONTROLLO.name());
	    Mailtipo template = mailtipoService.findById(new PkId(Integer.parseInt(templateId)));
	    MailMessageType msg = new MailMessageType();
	    msg.setDestinatari(destinatario);
	    msg.setCorpoMail(template.getCorpo() + report);
	    msg.setOggetto(template.getOggetto());
	    msg.setInviaComeHtml(Boolean.TRUE);
	    LoggerUpdaterecord.log(id + "==>AperturaGiornateMercatoJob problemi rilevati: " + report);
	    log.info("verificaEdInviaMail prima di inviare la mail {}", report);
	    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), Integer.parseInt(accountId), ORMHelper.getToken(), msg);
	} catch (Exception e) {
	    log.error("execute", e);
	    LoggerUpdaterecord.log(id + "==>" + getClass().getName() + " problemi nell'invio della mail: " + Utilities.formatDate(new Date(), true) +
				   ", dettaglio: " + e.getMessage());
	    LoggerUpdaterecord.log(id + "==>######################################################################");
	}
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> h = new HashMap<String, String>();
	PARAMS[] values = PARAMS.values();
	for (PARAMS param : values) {
	    h.put(param.name(), param.descrizione());
	}
	return h;
    }

    public enum PARAMS {

	TIPO_BOLLETTAZIONE("L'identificativo del tipo di bollettazione da creare"),
	TIPOLOGIA_PERIODO("Scelta tra PERIODO_ATTUALE, PERIODO_PRECEDENTE"),
	DESCRIZIONE_BOLLETTAZIONE("La descrizione da inserire come titolo della bollettazione. Sono previsti i segnaposto " + SEGNAPOSTO_DALLA_DATA +
				  ", " + SEGNAPOSTO_ALLA_DATA + ", " + SEGNAPOSTO_TIPO_BOLLETTAZIONE),
	LISTA_CODICI_COMUNI("Lista dei codici comuni da utilizzare come filtro, può essere vuota"),
	LISTA_INTERVENTI("Lista dei codici intervento (SC_CODICI) da utilizzare come filtro, può essere vuota"),
	LISTA_ENDOPROCEDIMENTI("Lista dei codici endoprocedimento da utilizzare come filtro, può essere vuota"),
	CODICE_RESPONSABILE("L'identificativo dell'operatore che crea la bollettazione"),
	MAIL_CONTROLLO("In caso di errore nella creazione viene inviata una mail a questo indirizzo"),
	MAIL_ACCOUNT("L'identificativo dell'account dal quale far partire la mail"),
	MAIL_TIPO("L'identificativo del template della mail da far partire"),
	INVIA_POSIZIONI("Decidere se inviare anche le posizioni debitorie (valori true/false predefinito false)");

	private String descrizione;

	PARAMS(String descrizione) {

	    this.descrizione = descrizione;
	}

	public String descrizione() {

	    return descrizione;
	}
    }
}
