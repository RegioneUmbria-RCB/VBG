package it.gruppoinit.pal.gp.core.features.nodopagamenti.jobs;

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
import it.gruppoinit.pal.gp.core.features.nodopagamenti.AggiornaDettPosizioniDebitorieService;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;

public class AggiornaDettaglioPosizioniDebitorieJob extends BaseJob implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(AggiornaDettaglioPosizioniDebitorieJob.class);
    private final static String PARAM_NUMERO_MASSIMO_POSIZIONI_DEBITORIE = "NUMERO_MASSIMO_POSIZIONI_DEBITORIE";
    private final static String PARAM_NUMERO_MASSIMO_POSIZIONI_DEBITORIE_DESC = "Indicare il numero massimo di posizioni debitorie da verificare." +
	    " Se lasciato vuoto verranno presi in considerazione, in una unica soluzione, tutte le posizioni debitorie presenti; l'operazione potrebbe rallentare l'applicativo.";
    private SecurityWSClient securityWSClient;
    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE = "LISTA_ALIAS_DA_ELABORARE";
    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE_DESC = "La lista degli alias da elaborare separata da \";\". Se non specificato prende la lista dalla security";

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("AggiornaDettaglioPosizioniDebitorieJob: begin");
	String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	String parNumeroMassimoPos = (String) ctx.getJobDetail().getJobDataMap().get(PARAM_NUMERO_MASSIMO_POSIZIONI_DEBITORIE);
	Integer numeroMassimoPos = StringUtils.isBlank(parNumeroMassimoPos) ? null : Integer.parseInt(parNumeroMassimoPos);
	String listaAliasDaElaborare = (String) ctx.getJobDetail().getJobDataMap().get(PARAM_LISTA_ALIAS_DA_ELABORARE);
	ORMHelper.setIdcomuneAlias(idcomunealias);
	setORMHelper(idcomunealias, software);
	log.debug("AggiornaDettaglioPosizioniDebitorieJob: recupero il client security");
	securityWSClient = (SecurityWSClient) ContextLoader.getCurrentWebApplicationContext().getBean("securityWSClient");
	try {
	    List<String> aliasList = null;
	    if (StringUtils.isNotEmpty(listaAliasDaElaborare)) {
		aliasList = Arrays.asList(listaAliasDaElaborare.split(";"));
	    } else {
		GetSecurityListRequest securityListRequest = new GetSecurityListRequest();
		//securityListRequest.setAlias(idcomunealias);
		GetSecurityListResponse securityListResponse;
		securityListResponse = securityWSClient.getWsPort().getSecurityList(securityListRequest);
		log.debug("AggiornaDettaglioPosizioniDebitorieJob: Chiamata alla security effettuata");
		aliasList = new ArrayList<String>();
		for (SecurityListType securityListRespItem : securityListResponse.getSecurity()) {
		    if (securityListRespItem.isAttivo()) {
			aliasList.add(securityListRespItem.getAlias());
		    }
		}
	    }
	    for (String alias : aliasList) {
		log.debug("AggiornaDettaglioPosizioniDebitorieJob: processo Alias {}. Setto ORMHelper", alias);
		try {
		    setORMHelper(alias, software);
		    // Effettuo una chiama al nodo dei pagamenti per ogni idcomune.
		    log.debug("AggiornaDettaglioPosizioniDebitorieJob: processo i dati per l'alias {}", alias);
		    AggiornaDettPosizioniDebitorieService service = (AggiornaDettPosizioniDebitorieService) ContextLoader
			    .getCurrentWebApplicationContext().getBean("aggiornaDettPosizioniDebitorieServiceImpl");
		    service.aggiornaDettaglioPosizioniDebitorie(numeroMassimoPos);
		    log.debug("AggiornaDettaglioPosizioniDebitorieJob: dati per Alias {} processati", alias);
		} catch (Exception e) {
		    log.error("errore nel processamento dell'alias {}, {}", alias, e);
		}
	    }
	} catch (Exception e) {
	    log.error("{}", e);
	}
	log.info("AggiornaDettaglioPosizioniDebitorieJob: end");
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put(PARAM_NUMERO_MASSIMO_POSIZIONI_DEBITORIE, PARAM_NUMERO_MASSIMO_POSIZIONI_DEBITORIE_DESC);
	m.put(PARAM_LISTA_ALIAS_DA_ELABORARE, PARAM_LISTA_ALIAS_DA_ELABORARE_DESC);
	return m;
    }
}
