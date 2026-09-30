package it.gruppoinit.pal.gp.core.features.nodopagamenti.jobs.csipiemonte;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;

public class AllineaPosizioniDebitorieJob extends BaseJob implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(AllineaPosizioniDebitorieJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("AllineaPosizioniDebitorieJob start");
	try {
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    ORMHelper.setIdcomuneAlias(idcomunealias);
	    setORMHelper(idcomunealias, software);
	    AllineaPosizioniDebitorieService service = (AllineaPosizioniDebitorieService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("allineaPosizioniDebitorieServiceImpl");
	    service.allinea();
	} catch (Exception e) {
	    //segnare il log anche nella tabella dei logs
	    log.error("AllineaPosizioniDebitorieJob error: ", e);
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
	log.info("AllineaPosizioniDebitorieJob end");
    }

    @Override
    public Map<String, String> getParam() {

	return null;
    }
}
