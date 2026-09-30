package it.gruppoinit.pal.gp.core.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.IAttivitaManager;
import it.gruppoinit.pal.gp.core.utils.LoggerChiusuraAttivitaScadute;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

public class ChiusuraAutomaticaAttivitaJob extends BaseWS implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(ChiusuraAutomaticaAttivitaJob.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("ChiusuraAutomaticaAttivitaJob start.....");
	LoggerChiusuraAttivitaScadute.logInfo("######################################################################");
	LoggerChiusuraAttivitaScadute.logInfo("START AT: {}", new String[] { Utilities.formatDate(new Date(), true) });
	try {
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    IAttivitaManager service = (IAttivitaManager) ContextLoader.getCurrentWebApplicationContext().getBean("IAttivitaManagerImpl");
	    log.info("Inizio l'aggiornamento.....");
	    service.updateProcessaAttivitaChiudere(idcomunealias, software);
	    log.info("Aggiornamento completato.....");
	} catch (Exception e) {
	    log.error("execute", e);
	    LoggerChiusuraAttivitaScadute.logInfo("STOP WITH ERROR AT: {}", new String[] { Utilities.formatDate(new Date(), true) });
	    LoggerChiusuraAttivitaScadute.logInfo("######################################################################");
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
	LoggerChiusuraAttivitaScadute.logInfo("STOP AT: {}", new String[] { Utilities.formatDate(new Date(), true) });
	LoggerChiusuraAttivitaScadute.logInfo("##########################################################################");
	log.info("ChiusuraAutomaticaAttivitaJob end.....");
    }
}
