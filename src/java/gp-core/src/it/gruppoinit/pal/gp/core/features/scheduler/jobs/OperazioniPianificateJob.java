package it.gruppoinit.pal.gp.core.features.scheduler.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.scheduler.TaskschedulerService;
import it.gruppoinit.pal.gp.core.features.scheduler.audit.EscuzionePianificazioniAuditLogger;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;

public class OperazioniPianificateJob extends BaseJob implements StatefulJob {

    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE = "LISTA_ALIAS_DA_ELABORARE";
    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE_DESC = "La lista degli alias da elaborare separata da \";\". Se non specificato prende la lista dalla security";

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	try {
	    EscuzionePianificazioniAuditLogger.logger.info("Inizio");
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String listaAliasDaElaborare = (String) ctx.getJobDetail().getJobDataMap().get(PARAM_LISTA_ALIAS_DA_ELABORARE);
	    ORMHelper.setIdcomuneAlias(idcomunealias);
	    setORMHelper(idcomunealias, software);
	    //1. Recupero l'alias dalla security a meno che non venga passato fisso come filtro
	    List<String> aliasList = null;
	    if (StringUtils.isNotEmpty(listaAliasDaElaborare)) {
		EscuzionePianificazioniAuditLogger.logger.debug("Recupero la lista degli alias dai parametri del job");
		aliasList = Arrays.asList(listaAliasDaElaborare.split(";"));
	    } else {
		EscuzionePianificazioniAuditLogger.logger.debug("Recupero la lista degli alias dalla security");
		SecurityWSClient securityWSClient = (SecurityWSClient) ContextLoader.getCurrentWebApplicationContext().getBean("securityWSClient");
		GetSecurityListResponse securityListResponse = securityWSClient.getWsPort().getSecurityList(new GetSecurityListRequest());
		aliasList = new ArrayList<String>();
		for (SecurityListType securityListRespItem : securityListResponse.getSecurity()) {
		    if (securityListRespItem.isAttivo()) {
			aliasList.add(securityListRespItem.getAlias());
		    }
		}
	    }
	    EscuzionePianificazioniAuditLogger.logger.debug("Alias da elaborare: {}", aliasList);
	    for (String alias : aliasList) {
		try {
		    EscuzionePianificazioniAuditLogger.logger.debug("Processo i dati per l'alias {}", alias);
		    setORMHelper(alias, software);
		    TaskschedulerService service = (TaskschedulerService) ContextLoader.getCurrentWebApplicationContext()
			    .getBean("taskschedulerServiceImpl");
		    service.elabora(true);
		} catch (Exception e) {
		    EscuzionePianificazioniAuditLogger.logger.error("Errore nel processamento dell'alias {}", alias, e);
		} finally {
		    EscuzionePianificazioniAuditLogger.logger.debug("Fine processamento dell'alias {}", alias);
		}
	    }
	} catch (Exception e) {
	    EscuzionePianificazioniAuditLogger.logger.error("Errore durante l'esecuzione del job", e);
	} finally {
	    EscuzionePianificazioniAuditLogger.logger.info("Fine");
	    ORMHelper.destroyORMHelper();
	}
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put(PARAM_LISTA_ALIAS_DA_ELABORARE, PARAM_LISTA_ALIAS_DA_ELABORARE_DESC);
	return m;
    }
}
