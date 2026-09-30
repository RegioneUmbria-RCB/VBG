package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStrRicalcoloRestClient;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxReqParams;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxRequest;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloAreeIstanzeService;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;

public class RicalcolaAreeJob extends BaseJob implements StatefulJob {

    private SecurityWSClient securityWSClient;
    private static final Logger log = LoggerFactory.getLogger(RicalcolaAreeJob.class);
    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE = "LISTA_ALIAS_DA_ELABORARE";
    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE_DESC = "La lista degli alias da elaborare separata da \";\". Se non specificato prende la lista dalla security";

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("RicalcolaAreeJob: begin");
	String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	String listaAliasDaElaborare = (String) ctx.getJobDetail().getJobDataMap().get(PARAM_LISTA_ALIAS_DA_ELABORARE);
	ORMHelper.setIdcomuneAlias(idcomunealias);
	setORMHelper(idcomunealias, software);
	log.debug("RicalcolaAreeJob: recupero il client security");
	securityWSClient = (SecurityWSClient) ContextLoader.getCurrentWebApplicationContext().getBean("securityWSClient");
	try {
	    //1. Recupero l'alias dalla security a meno che non venga passato fisso come filtro
	    List<String> aliasList = null;
	    if (StringUtils.isNotEmpty(listaAliasDaElaborare)) {
		aliasList = Arrays.asList(listaAliasDaElaborare.split(";"));
	    } else {
		GetSecurityListRequest securityListRequest = new GetSecurityListRequest();
		//securityListRequest.setAlias(idcomunealias);
		GetSecurityListResponse securityListResponse;
		securityListResponse = securityWSClient.getWsPort().getSecurityList(securityListRequest);
		log.debug("RicalcolaAreeJob: Chiamata alla security effettuata");
		aliasList = new ArrayList<String>();
		for (SecurityListType securityListRespItem : securityListResponse.getSecurity()) {
		    if (securityListRespItem.isAttivo()) {
			aliasList.add(securityListRespItem.getAlias());
		    }
		}
	    }
	    for (String alias : aliasList) {
		log.debug("RicalcolaAreeJob: processo Alias {}. Setto ORMHelper", alias);
		try {
		    setORMHelper(alias, software);
		    log.debug("RicalcolaAreeJob: processo i dati per l'alias {}", alias);
		    RicalcoloAreeIstanzeService service = (RicalcoloAreeIstanzeService) ContextLoader.getCurrentWebApplicationContext()
			    .getBean("ricalcoloAreeIstanzeServiceImpl");
		    log.debug("RicalcolaAreeJob: Estrazione degli uuid delle istanze da ricalcolare");
		    List<String> testateIdL = service.getTestateDaRicalcolare();
		    if (testateIdL == null || testateIdL.isEmpty()) {
			log.debug("RicalcolaAreeJob: Non sono state trovate istanze da ricalcolare per l'alias {}", alias);
			log.info("RicalcolaAreeJob: execute end");
			continue;
		    }
		    RicalcoloMaxRequest req = new RicalcoloMaxRequest();
		    req.setAlias(alias);
		    req.setRicalcoloAreeIdL(testateIdL);
		    RicalcoloMaxReqParams params = new RicalcoloMaxReqParams();
		    params.setSoftware(ORMHelper.getSoftware());
		    params.setIdcomune(ORMHelper.getIdcomune());
		    params.setToken(ORMHelper.getToken());
		    log.debug("RicalcolaAreeJob: Inizio chiamata al componente di ricalcolo");
		    IstanzeStrRicalcoloRestClient restClient = new IstanzeStrRicalcoloRestClient();
		    List<String> ricalcoloAreeIdL = restClient.ricalcolaArea(req, params).getRicalcoloAreeIdList();
		    log.debug("RicalcolaAreeJob: Fine chiamata al componente di ricalcolo, id restituiti: {}", ricalcoloAreeIdL);
		    log.info("RicalcolaAreeJob: execute end");
		} catch (Exception e) {
		    log.error("RicalcolaAreeJob: errore nel processamento dell'alias {}", alias, e);
		}
	    }
	} catch (Exception e) {
	    log.error("RicalcolaAreeJob: Errore durante l'esecuzione del job", e);
	} finally {
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
