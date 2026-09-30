package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.jobs;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;

public class ComunicazioniMassiveJob extends BaseJob implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(ComunicazioniMassiveJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	try {
	    log.info("inizio elaborazione comunicazioni massive delle manifestazioni");
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    setORMHelper(idcomunealias, software);
	    IComunicazioniManifestazioniService service = (IComunicazioniManifestazioniService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("comunicazioniManifestazioniServiceImpl");
	    service.elabora();
	} catch (Exception e) {
	    log.error("errore in elaborazione comunicazioni massive delle manifestazioni", e);
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	    log.info("fine elaborazione comunicazioni massive delle manifestazioni");
	}
    }

    @Override
    public Map<String, String> getParam() {

	return null;
    }
}
