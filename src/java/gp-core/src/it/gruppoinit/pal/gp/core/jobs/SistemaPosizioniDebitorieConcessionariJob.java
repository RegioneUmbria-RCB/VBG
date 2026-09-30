package it.gruppoinit.pal.gp.core.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.SistemaPosizioniDebitorieConcessionariJobService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoConcessionarioSegnatoPresente;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

public class SistemaPosizioniDebitorieConcessionariJob extends BaseJob implements StatefulJob {

    private static final String MAIL_ACCOUNT = "MAIL_ACCOUNT";
    private static final String DEBUG = "DEBUG";
    private static final String MAIL_CONTROLLO = "MAIL_CONTROLLO";
    private static final Logger log = LoggerFactory.getLogger(SistemaPosizioniDebitorieConcessionariJob.class);
    private static final String MAIL_TIPO = "MAIL_TIPO";

    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("SistemaPosizioniDebitorieConcessionariJob start.....");
	Date data = Calendar.getInstance().getTime();
	String id = String.valueOf(String.valueOf(System.currentTimeMillis())) + "_" + Utilities.formatDate(data, false);
	StringBuilder sb = new StringBuilder();
	LoggerUpdaterecord.log("######################################################################");
	LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob START AT: " + Utilities.formatDate(new Date(), true));
	int posCreate = 0;
	int recordProcessati = 0;
	try {
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get("idcomunealias");
	    String software = (String) ctx.getJobDetail().getJobDataMap().get("software");
	    String debug = (String) ctx.getJobDetail().getJobDataMap().get(DEBUG);
	    boolean isDebug = StringUtils.defaultString(debug, "false").equalsIgnoreCase("true");
	    setORMHelper(idcomunealias, software);
	    LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob idcomunealias " + ORMHelper.getIdcomuneAlias() + ", software " +
				   ORMHelper.getSoftware());
	    MercatipresenzeTService mercatipresenzeTService = (MercatipresenzeTService) getBeanOfType(MercatipresenzeTService.class.getName());
	    MercatipresenzeDService mercatipresenzeDService = (MercatipresenzeDService) getBeanOfType(MercatipresenzeDService.class.getName());
	    SistemaPosizioniDebitorieConcessionariJobService service = (SistemaPosizioniDebitorieConcessionariJobService) getBeanOfType(
		    SistemaPosizioniDebitorieConcessionariJobService.class.getName());
	    List<Integer> mpds = mercatipresenzeDService.findPresenzeConcessionariSenzaPosizioniDebitorie(data);
	    log.debug("Cerco le presenze senza posizioni debitorie {}", Integer.valueOf(mpds.size()));
	    LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob presenze senza posizioni debitorie " + mpds.size());
	    for (Integer mpd : mpds) {
		recordProcessati++;
		MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findById(new PkId(mpd));
		if (isDebug) {
		    LoggerUpdaterecord
			    .log(id + "==>SistemaPosizioniDebitorieConcessionariJob processo la presenza:" + mercatipresenzeD.getId().getCodice() +
				 ", mercatipresenzeD.getDettPosizioneDebitoria():" + mercatipresenzeD.getDettPosizioneDebitoria() +
				 ", mercatipresenzeD.getNumeropresenze() " + mercatipresenzeD.getNumeropresenze());
		}
		if (mercatipresenzeD.getDettPosizioneDebitoria() == null && mercatipresenzeD.getNumeropresenze().intValue() == 1) {
		    if (isDebug) {
			LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob invio evento per la presenza:" +
					       mercatipresenzeD.getId().getCodice() + ", mercatipresenzeD.getDettPosizioneDebitoria():" +
					       mercatipresenzeD.getDettPosizioneDebitoria() + ", mercatipresenzeD.getNumeropresenze() " +
					       mercatipresenzeD.getNumeropresenze());
		    }
		    try {
			service.rilanciaEventoNelService(new EventoConcessionarioSegnatoPresente(mercatipresenzeD.getId().getCodice(), data));
			if (isDebug)
			    LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob evento pubblicato per la presenza:" +
						   mercatipresenzeD.getId().getCodice() + ", mercatipresenzeD.getDettPosizioneDebitoria():" +
						   mercatipresenzeD.getDettPosizioneDebitoria() + ", mercatipresenzeD.getNumeropresenze() " +
						   mercatipresenzeD.getNumeropresenze());
		    } catch (Exception e) {
			LoggerUpdaterecord
				.log(id + "==>SistemaPosizioniDebitorieConcessionariJob errore in mpd " + mpd + ", dettaglio: " + dettaglioErrore(e));
			log.error("SistemaPosizioniDebitorieConcessionariJob. id==>" + id + " Errore==>" + e.getMessage(), e);
		    }
		    mercatipresenzeD = mercatipresenzeDService.findById(new PkId(mercatipresenzeD.getId().getCodice()));
		    if (mercatipresenzeD.getDettPosizioneDebitoria() != null) {
			posCreate++;
		    }
		}
		log.debug("record processati {} posizioni create {}", Integer.valueOf(recordProcessati), Integer.valueOf(posCreate));
	    }
	    String destinatario = (String) ctx.getJobDetail().getJobDataMap().get(MAIL_CONTROLLO);
	    if (StringUtils.isNotBlank(destinatario)) {
		verificaEdInviaMail(ctx, destinatario, mercatipresenzeTService, id, data);
	    }
	} catch (ClassNotFoundException e1) {
	    log.error("==>Errore: " + e1.getMessage(), e1);
	    LoggerUpdaterecord.log(id + "==>Errore: " + e1.getMessage());
	    ORMHelper.destroyORMHelper();
	}
	sb.append("Processate ").append(recordProcessati).append(" presenze").append(" create ").append(posCreate).append(" posizioni debitorie");
	LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob report: " + sb.toString());
	LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob STOP AT: " + Utilities.formatDate(new Date(), true));
	LoggerUpdaterecord.log(id + "==>##########################################################################");
	ORMHelper.destroyORMHelper();
    }

    private static String dettaglioErrore(Exception e) {

	StringBuilder errore = new StringBuilder();
	errore.append(e.getMessage());
	errore.append("\n");
	errore.append(printStackTrace(e.getStackTrace()));
	if (e.getCause() != null) {
	    errore.append("causato da: ").append(e.getCause().getMessage()).append("\n");
	    errore.append(e.getCause().getStackTrace());
	}
	return errore.toString();
    }

    private static String printStackTrace(StackTraceElement[] stackTrace) {

	StringBuilder errore = new StringBuilder();
	if (stackTrace != null) {
	    byte b;
	    int i;
	    StackTraceElement[] arrayOfStackTraceElement;
	    for (i = (arrayOfStackTraceElement = stackTrace).length, b = 0; b < i;) {
		StackTraceElement stackTraceElement = arrayOfStackTraceElement[b];
		errore.append(stackTraceElement).append("\n");
		b++;
	    }
	}
	return errore.toString();
    }

    private void verificaEdInviaMail(JobExecutionContext ctx, String destinatario, MercatipresenzeTService service, String id, Date data) {

	try {
	    setORMHelper(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	    List<IdentificativoDescrizioneBean> checkPosizioniCreate = service.checkPosizioniDebitorieCreatePerConcessionari(data);
	    log.info("verificaEdInviaMail checkPosizioniCreate.size {}", checkPosizioniCreate.size());
	    if (!checkPosizioniCreate.isEmpty()) {
		String report = getReport(checkPosizioniCreate);
		MailtipoService mailtipoService = (MailtipoService) ContextLoader.getCurrentWebApplicationContext().getBean("mailtipoServiceImpl");
		MailServiceWSClient mailServiceWSClient = (MailServiceWSClient) ContextLoader.getCurrentWebApplicationContext()
			.getBean("mailServiceWSClient");
		String accountId = (String) ctx.getJobDetail().getJobDataMap().get(MAIL_ACCOUNT);
		String templateId = (String) ctx.getJobDetail().getJobDataMap().get(MAIL_TIPO);
		Mailtipo template = mailtipoService.findById(new PkId(Integer.parseInt(templateId)));
		MailMessageType msg = new MailMessageType();
		msg.setDestinatari(destinatario);
		msg.setCorpoMail(template.getCorpo() + report);
		msg.setOggetto(template.getOggetto());
		msg.setInviaComeHtml(Boolean.TRUE);
		LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob problemi rilevati: " + report);
		log.info("verificaEdInviaMail prima di inviare la mail {}", report);
		mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), Integer.parseInt(accountId), ORMHelper.getToken(), msg);
	    }
	} catch (Exception e) {
	    log.error("execute", e);
	    LoggerUpdaterecord.log(id + "==>SistemaPosizioniDebitorieConcessionariJob problemi nell'invio della mail: " +
				   Utilities.formatDate(new Date(), true) + ", dettaglio: " + e.getMessage());
	    LoggerUpdaterecord.log(id + "==>######################################################################");
	}
    }

    private String getReport(List<IdentificativoDescrizioneBean> checkPosizioniCreate) {

	StringBuilder problemi = new StringBuilder("<br />");
	problemi.append("Si sono verificati problemi nella creazione delle posizioni nei seguenti mercati: \n<table border=\"1\">");
	problemi.append("\n<tr><td>Mercato</td><td>Problemi</td></tr>");
	for (IdentificativoDescrizioneBean idb : checkPosizioniCreate) {
	    problemi.append("\n<tr><td>").append(idb.getDescrizione()).append("</td><td>").append(idb.getId()).append("</td>").append("</tr>");
	}
	problemi.append("\n</table>");
	return problemi.toString();
    }

    public Map<String, String> getParam() {

	Map<String, String> h = new HashMap<String, String>();
	h.put(MAIL_CONTROLLO,
		"Se specificato al termine del service vengono verificate che le posizioni debitorie siano state create per i concessionari. Se non sono state create viene unviata una mail a questo indirizzo");
	h.put(MAIL_ACCOUNT, "L'identificativo dell'account dal quale far partire la mail");
	h.put(MAIL_TIPO, "L'identificativo del template della mail da far partire");
	return h;
    }
}
