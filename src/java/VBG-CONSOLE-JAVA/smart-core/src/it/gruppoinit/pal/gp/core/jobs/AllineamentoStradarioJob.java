package it.gruppoinit.pal.gp.core.jobs;

import it.gruppoinit.pal.gp.core.service.StradarioManager;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

public class AllineamentoStradarioJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(AllineamentoStradarioJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	//	log.info("execute");
	//	try {
	//	    StradarioManager service = (StradarioManager) ContextLoader.getCurrentWebApplicationContext().getBean("stradarioManagerImpl");
	//	    service.allineaStradario();
	//	    log.info("execute end");
	//	} catch (Exception e) {
	//	    log.error("execute", e);
	//	    throw new JobExecutionException(e.getMessage());
	//	}
    }
}
