package it.gruppoinit.pal.gp.core.jobs;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.service.ElaborazioneDizionariCart;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

public class AggiornamentoSchede2DizionarioJob extends BaseAggiornamentoDizionario implements Job {

    private static final Logger log = LoggerFactory.getLogger(AggiornamentoSchede2DizionarioJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	//	Map<String, String> aliasProcessati = new HashMap<String, String>();
	//	LoggerCancellazioni.log("#AggiornamentoSchede2DizionarioJob#");
	//	try {
	//	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	//	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	//	    ORMHelper.setIdcomuneAlias(idcomunealias);
	//	    ORMHelper.setSoftware(software);
	//	    SdeproxyService sdeproxyService = (SdeproxyService) ContextLoader.getCurrentWebApplicationContext().getBean("sdeproxyServiceImpl");
	//	    List<Sdeproxy> ls = sdeproxyService.findAll(null, null);
	//	    Properties p = loadConfig();
	//	    for (Sdeproxy sde : ls) {
	//		if (isAliasDaProcessare(sde.getAliasEnte(), p)) {
	//		    if (aliasProcessati.get(sde.getAliasEnte()) != null) {
	//			LoggerCancellazioni.log("#AggiornamentoSchede2DizionarioJob#inizio l'elaborazione dell'allineamento delle schede per l'ente "
	//				+ sde.getAliasEnte() + "-" + sde.getDescrizione());
	//			if (setORMHelper(sde.getAliasEnte(), sde.getIdente(), software)) {
	//			    ElaborazioneDizionariCart service = (ElaborazioneDizionariCart) ContextLoader.getCurrentWebApplicationContext().getBean(
	//				    "elaborazioneDizionariCartImpl");
	//			    service.aggiornaSchedeEndo2(sde.getIdente(), sde.getAliasEnte(), software, sde.getDescrizione());
	//			    LoggerCancellazioni.log("#AggiornamentoSchede2DizionarioJob#Elaborazione dell'allineamento delle schede per l'ente {}-{}"
	//				    + sde.getAliasEnte() + "-" + sde.getDescrizione());
	//			    aliasProcessati.put(sde.getAliasEnte(), sde.getAliasEnte());
	//			}
	//		    }
	//		}
	//	    }
	//	    LoggerCancellazioni.log("#AggiornamentoSchede2DizionarioJob END#");
	//	} catch (Exception e) {
	//	    log.error("execute", e);
	//	    throw new JobExecutionException(e.getMessage());
	//	} finally {
	//	    ORMHelper.destroyORMHelper();
	//	}
    }
}
