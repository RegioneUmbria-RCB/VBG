package it.gruppoinit.pal.gp.core.jobs;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistMotiviService;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;

/**
 * <pre>
 * 0 0-10 6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,22 ? * * *
 * </pre>
 * 
 * da far girare ogni giorno ogni 10 minuti tra le 6 della mattina e le 22 della sera di tutti i giorni dell'anno
 * 
 * @author riccardob
 *
 */
public class BlacklistJob extends BaseJob implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(BlacklistJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("BlacklistJob execute");
	try {
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    ORMHelper.setIdcomuneAlias(idcomunealias);
	    setORMHelper(idcomunealias, software);
	    BlacklistMotiviService service = (BlacklistMotiviService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("blacklistMotiviServiceImpl");
	    StringBuilder sb = service.updateBlackList();
	    log.info("result: {}", sb);
	    LoggerUpdaterecord.log(sb.toString());
	    log.info("BlacklistJob execute end");
	} catch (Exception e) {
	    log.error("execute", e);
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }

    @Override
    public Map<String, String> getParam() {

	return null;
    }
}
