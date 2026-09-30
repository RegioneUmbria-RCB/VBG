package it.gruppoinit.pal.gp.core.features.protocollazione.logic.notificafirma;

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

public class VerificaNotificaFirmaJob extends BaseJob implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(VerificaNotificaFirmaJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("Inizio VerificaNotificaFirmaJob");
	try {
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    setORMHelper(idcomunealias, software);
	    NotificaFirmaService service = (NotificaFirmaService) ContextLoader.getCurrentWebApplicationContext().getBean("notificaFirmaServiceImpl");
	    service.verificaFirmeNonNotificate();
	} catch (Exception e) {
	    log.error("error VerificaNotificaFirmaJob", e);
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
	log.info("Fine VerificaNotificaFirmaJob");
    }

    @Override
    public Map<String, String> getParam() {

	// TODO Auto-generated method stub
	return null;
    }
}
