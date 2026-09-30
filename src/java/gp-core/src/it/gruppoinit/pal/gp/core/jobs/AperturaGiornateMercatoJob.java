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
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

/**
 * <pre>
 *  0 0 1 ? * * *
 * </pre>
 * 
 * da far girare alle 1 di notte tutti i giorni dell'anno
 * 
 * @author riccardob
 *
 */
public class AperturaGiornateMercatoJob extends BaseJob implements StatefulJob {

    private static final String MAIL_ACCOUNT = "MAIL_ACCOUNT";
    private static final String MAIL_CONTROLLO = "MAIL_CONTROLLO";
    private static final Logger log = LoggerFactory.getLogger(AperturaGiornateMercatoJob.class);
    private static final String MAIL_TIPO = "MAIL_TIPO";

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("AperturaGiornateMercatoJob start.....");
	String id = String.valueOf(System.currentTimeMillis());
	LoggerUpdaterecord.log("######################################################################");
	LoggerUpdaterecord.log(id + "==>AperturaGiornateMercatoJob START AT: " + Utilities.formatDate(new Date(), true));
	MercatipresenzeTService service = (MercatipresenzeTService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("mercatipresenzeTServiceImpl");
	try {
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    setORMHelper(idcomunealias, software);
	    log.info("Inizio l'aggiornamento.....");
	    service.inizializzaGiornateMercatoOdierne(idcomunealias, software);
	    log.info("Aggiornamento completato.....");
	} catch (Exception e) {
	    log.error("execute", e);
	    LoggerUpdaterecord.log(id + "==>AperturaGiornateMercatoJob STOP WITH ERROR AT: " + Utilities.formatDate(new Date(), true));
	    LoggerUpdaterecord.log(id + "==>######################################################################");
	}
	String destinatario = (String) ctx.getJobDetail().getJobDataMap().get(MAIL_CONTROLLO);
	if (StringUtils.isNotBlank(destinatario)) {
	    verificaEdInviaMail(ctx, destinatario, service, id);
	}
	LoggerUpdaterecord.log(id + "==>AperturaGiornateMercatoJob STOP AT: " + Utilities.formatDate(new Date(), true));
	LoggerUpdaterecord.log(id + "==>##########################################################################");
	log.info("AperturaGiornateMercatoJob end.....");
	ORMHelper.destroyORMHelper();
    }

    private void verificaEdInviaMail(JobExecutionContext ctx, String destinatario, MercatipresenzeTService service, String id) {

	try {
	    setORMHelper(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	    List<IdentificativoDescrizioneBean> checkPosizioniCreate = service
		    .checkPosizioniDebitorieCreatePerConcessionari(Calendar.getInstance().getTime());
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
		LoggerUpdaterecord.log(id + "==>AperturaGiornateMercatoJob problemi rilevati: " + report);
		log.info("verificaEdInviaMail prima di inviare la mail {}", report);
		mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), Integer.parseInt(accountId), ORMHelper.getToken(), msg);
	    }
	} catch (Exception e) {
	    log.error("execute", e);
	    LoggerUpdaterecord.log(id +
		    "==>AperturaGiornateMercatoJob problemi nell'invio della mail: " +
		    Utilities.formatDate(new Date(), true) +
		    ", dettaglio: " +
		    e.getMessage());
	    LoggerUpdaterecord.log(id + "==>######################################################################");
	}
    }

    private String getReport(List<IdentificativoDescrizioneBean> checkPosizioniCreate) {

	StringBuilder problemi = new StringBuilder("<br />");
	problemi.append("Si sono verificati problemi nella creazione delle posizioni nei seguenti mercati: \n<table border=\"1\">");
	problemi.append("\n<tr><td>Mercato</td><td>Problemi</td></tr>");
	for (IdentificativoDescrizioneBean idb : checkPosizioniCreate) {
	    problemi.append("\n<tr><td>")//
		    .append(idb.getDescrizione()) //
		    .append("</td><td>") // 
		    .append(idb.getId()) //
		    .append("</td>") //
		    .append("</tr>");
	}
	problemi.append("\n</table>");
	return problemi.toString();
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> h = new HashMap<String, String>();
	h.put(MAIL_CONTROLLO,
		"Se specificato al termine del service vengono verificate che le posizioni debitorie siano state create per i concessionari. Se non sono state create viene unviata una mail a questo indirizzo");
	h.put(MAIL_ACCOUNT, "L'identificativo dell'account dal quale far partire la mail");
	h.put(MAIL_TIPO, "L'identificativo del template della mail da far partire");
	return h;
    }
}
