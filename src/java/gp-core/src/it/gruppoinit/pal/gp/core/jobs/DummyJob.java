package it.gruppoinit.pal.gp.core.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

/**
 * Implementare StatefulJob se l'esecuzione non deve iniziare se la precedente non è terminata
 */
public class DummyJob implements StatefulJob {

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	System.out.println(ctx.toString());
	System.out.println("idcomunealias=" + idcomunealias);
	System.out.println("software=" + software);
    }
}
