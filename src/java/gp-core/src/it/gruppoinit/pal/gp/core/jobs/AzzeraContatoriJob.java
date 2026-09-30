package it.gruppoinit.pal.gp.core.jobs;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.IstanzeManager;
import it.gruppoinit.pal.gp.core.service.rules.OperazioniAutomaticheBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

public class AzzeraContatoriJob extends BaseJob implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(AzzeraContatoriJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("execute");
	OperazioniAutomaticheBusinessRules opautBusinessRules = new OperazioniAutomaticheBusinessRules();
	opautBusinessRules.setOperazioneAutomatica(true);
	SigeproBusinessRules.setClassRules(OperazioniAutomaticheBusinessRules.class, opautBusinessRules);
	try {
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    setORMHelper(idcomunealias, software);
	    IstanzeManager service = (IstanzeManager) ContextLoader.getCurrentWebApplicationContext().getBean("istanzeManagerImpl");
	    log.info("inizio l'aggiornamento dei contatori");
	    service.updateContatori(idcomunealias, software);
	    log.info("aggiornamento dei contatori completato");
	} catch (Exception e) {
	    log.error("execute", e);
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	    SigeproBusinessRules.buildDefaultRules();
	}
    }

    @Override
    public Map<String, String> getParam() {

	return new HashMap<String, String>();
    }
}
