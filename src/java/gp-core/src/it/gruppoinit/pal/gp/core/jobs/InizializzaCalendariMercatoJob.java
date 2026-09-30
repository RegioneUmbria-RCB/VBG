package it.gruppoinit.pal.gp.core.jobs;

import java.util.ArrayList;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.service.CalendariomercatoParametriService;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * <pre>
  0 10 0 1 JAN ? *
 * </pre>
 * 
 * da far girare il 1 Gennaio alle ore 00:10:00 di ogni anno
 * 
 * @author riccardob
 *
 */
public class InizializzaCalendariMercatoJob extends BaseJob implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(InizializzaCalendariMercatoJob.class);
    private static final String BLOCCA_PRIMO_GENNAIO = "BLOCCA_PRIMO_GENNAIO";

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("InizializzaCalendariMercatoJob start.....");
	String id = String.valueOf(System.currentTimeMillis());
	LoggerUpdaterecord.log("######################################################################");
	try {
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    boolean bloccaPrimoGennaio = bloccaPrimoGennaio((String) ctx.getJobDetail().getJobDataMap().get(BLOCCA_PRIMO_GENNAIO));
	    LoggerUpdaterecord.log(id + "==>InizializzaCalendariMercatoJob ALIAS:" + idcomunealias + ", Software:" + software + ", AT: " +
				   Utilities.formatDate(new Date(), true));
	    setORMHelper(idcomunealias, software);
	    MercatipresenzeTService mercatipresenzeTService = (MercatipresenzeTService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("mercatipresenzeTServiceImpl");
	    MercatiService mercatiService = (MercatiService) ContextLoader.getCurrentWebApplicationContext().getBean("mercatiServiceImpl");
	    MercatiUsoService mercatiUsoService = (MercatiUsoService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("mercatiUsoServiceImpl");
	    // Map beansOfType = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(GiornisectimanaService.class);
	    CalendariomercatoParametriService calendariomercatoParametriService = (CalendariomercatoParametriService) ContextLoader
		    .getCurrentWebApplicationContext().getBean("calendariomercatiParametriServiceImpl");
	    GiornisectimanaService giornisectimanaService = (GiornisectimanaService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("giornisectimanaServiceImpl");
	    UserSecurityService userSecurityService = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("userSecurityService");
	    log.info("Inizio l'aggiornamento.....");
	    List<Mercati> mercati = mercatiService.findAllMercatiAttivi(null, null);
	    int anno = Calendar.getInstance().get(Calendar.YEAR);
	    Responsabili utente = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    for (Mercati _mercato : mercati) {
		List<MercatiUso> mercatoUsi = mercatiUsoService.findByMercato(_mercato);
		for (MercatiUso _uso : mercatoUsi) {
		    String descrizioneMercato = _mercato.getDescrizione() + " (" + _mercato.getId() + ")-USO:" + _uso.getDescrizione();
		    Giornisettimana gsect = giornisectimanaService.findById(_uso.getGiornisettimana().getId());
		    List<Giornisettimana> gs = new ArrayList<Giornisettimana>();
		    if (gsect == null) {
			LoggerUpdaterecord.log(id + "==>InizializzaCalendariMercatoJob Il mercato " + descrizioneMercato +
					       " non ha configurato la voce Giorno della Settimana giornate e lo considero come elaborato AT: " +
					       Utilities.formatDate(new Date(), true));
		    } else {
			gsect.setTransientSelected(true);
			gs.add(gsect);
			int giornateDellAnno = mercatipresenzeTService.countByMercatoUsoAnno(_mercato.getId().getCodice(), _uso.getId().getCodice(),
				anno);
			if (giornateDellAnno == 0) {
			    LoggerUpdaterecord.log(id + "==>InizializzaCalendariMercatoJob Elaboro il mercato " + descrizioneMercato + " AT: " +
						   Utilities.formatDate(new Date(), true));
			    CalendariomercatoParametri calendariomercatoParametri = new CalendariomercatoParametri();
			    calendariomercatoParametri.setAnno(anno);
			    calendariomercatoParametri.setMercatiUso(_uso);
			    calendariomercatoParametri.setStep(0);
			    calendariomercatoParametri.setGiorniSettimana(gs);
			    calendariomercatoParametri.setGiorniMercato(calendariomercatoParametriService
				    .findGiorniMercatoInUnAnno(calendariomercatoParametri.getAnno(),
					    calendariomercatoParametri.getMercatiUso(), calendariomercatoParametri.getGiorniSettimana())
				    .getGiorniMercato());
			    calendariomercatoParametri
				    .setGiorniFestivi(calendariomercatoParametriService.findGiornifestivi(calendariomercatoParametri.getAnno()));
			    try {
				mercatipresenzeTService.inserisciCalendario(calendariomercatoParametri, _mercato, utente, bloccaPrimoGennaio);
			    } catch (Exception e) {
				log.error("InizializzaCalendariMercatoJob errore nell'elaborazione del mercato " + descrizioneMercato + " id:" + id +
					  "-->{}",
					e);
				LoggerUpdaterecord
					.log(id + "==>InizializzaCalendariMercatoJob errore nell'elaborazione del mercato " + descrizioneMercato +
					     " AT: " + Utilities.formatDate(new Date(), true) + ": errore=" + e.getMessage());
			    }
			} else {
			    LoggerUpdaterecord
				    .log(id + "==>InizializzaCalendariMercatoJob Il mercato " + descrizioneMercato + " ha già configurate N#" +
					 giornateDellAnno + " giornate e lo considero come elaborato AT: " + Utilities.formatDate(new Date(), true));
			}
		    }
		}
	    }
	    log.info("Aggiornamento completato.....");
	} catch (Exception e) {
	    log.error("execute", e);
	    LoggerUpdaterecord.log(id + "==>InizializzaCalendariMercatoJob STOP WITH ERROR AT: " + Utilities.formatDate(new Date(), true));
	    LoggerUpdaterecord.log(id + "==>######################################################################");
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
	LoggerUpdaterecord.log(id + "==>InizializzaCalendariMercatoJob STOP AT: " + Utilities.formatDate(new Date(), true));
	LoggerUpdaterecord.log(id + "==>##########################################################################");
	log.info("InizializzaCalendariMercatoJob end.....");
    }

    public boolean bloccaPrimoGennaio(String val) {

	return StringUtils.defaultIfEmpty(val, "0").trim().equals("1");
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> h = new HashMap<String, String>();
	h.put(BLOCCA_PRIMO_GENNAIO,
		"Il parametro permette di impostare il comportamento di non generare la giornata del primo gennaio, Se impostato a 1 allora la prima giornata di gennaio non viene inserita nei calendari");
	return h;
    }
}
