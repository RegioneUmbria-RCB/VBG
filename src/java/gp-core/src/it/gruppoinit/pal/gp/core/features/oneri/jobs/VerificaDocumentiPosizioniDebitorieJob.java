package it.gruppoinit.pal.gp.core.features.oneri.jobs;

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
import it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch.IstanzeoneriPosdebBatchService;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;

public class VerificaDocumentiPosizioniDebitorieJob extends BaseJob implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(VerificaDocumentiPosizioniDebitorieJob.class);
    private static final String PARAM_NUMERO_RECORD_DA_ELABORARE = "NUM_RECORD_DA_ELABORARE";
    private static final String PARAM_NUMERO_RECORD_DA_ELABORARE_DESC = "Indicare il numero di record da processare per ente. Ogni esecuzione del job cerca di elaborare questo numero di record. Il conteggio viene incrementato quando una riga della tabella ISTANZEONERI_POS_DEB_BATCH viene segnata come completata";
    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE = "LISTA_ALIAS_DA_ELABORARE";
    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE_DESC = "La lista degli alias da elaborare separata da ; se non specificato prende la lista dalla security";
    private SecurityWSClient securityWSClient;
    private final int NUMERO_RECORD_DA_ELABORARE_DEFAULT = 50;

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("VerificaDocumentiPosizioniDebitorieJob: begin");
	String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	String parNumeroMRecordDaElaborare = (String) ctx.getJobDetail().getJobDataMap().get(PARAM_NUMERO_RECORD_DA_ELABORARE);
	String listaAliasDaElaborare = (String) ctx.getJobDetail().getJobDataMap().get(PARAM_LISTA_ALIAS_DA_ELABORARE);
	int numeroRecordDaElaborare = NUMERO_RECORD_DA_ELABORARE_DEFAULT;
	if (Utilities.isInteger(parNumeroMRecordDaElaborare)) {
	    numeroRecordDaElaborare = Integer.parseInt(parNumeroMRecordDaElaborare.trim());
	}
	log.debug("VerificaDocumentiPosizioniDebitorieJob: setto ORMHELPER da alias {}", idcomunealias);
	ORMHelper.setIdcomuneAlias(idcomunealias);
	setORMHelper(idcomunealias, software);
	log.debug("VerificaDocumentiPosizioniDebitorieJob: recupero il client security");
	securityWSClient = (SecurityWSClient) ContextLoader.getCurrentWebApplicationContext().getBean("securityWSClient");
	// il job dovrebbe girare per tutte le installazioni e quindi a partire
	// dall'alias verifica dalla security le installazioni attive e per ogni
	// installazione setta ORMHELPER e lancia il metodo del service per eseguire
	// l'operazione specifica
	// la logica è :
	// verifica tutte le righe non completate di ISTANZEONERI_POSDEB_BATCH
	try {
	    List<String> aliasList = null;
	    if (StringUtils.isNotEmpty(listaAliasDaElaborare)) {
		aliasList = Arrays.asList(listaAliasDaElaborare.split(";"));
	    } else {
		GetSecurityListRequest securityListRequest = new GetSecurityListRequest();
		securityListRequest.setAlias(idcomunealias);
		GetSecurityListResponse securityListResponse = securityWSClient.getWsPort().getSecurityList(securityListRequest);
		log.debug("VerificaDocumentiPosizioniDebitorieJob: Chiamata alla security effettuata");
		aliasList = new ArrayList<String>();
		for (SecurityListType securityListRespItem : securityListResponse.getSecurity()) {
		    if (securityListRespItem.isAttivo()) {
			aliasList.add(securityListRespItem.getAlias());
		    }
		}
	    }
	    for (String alias : aliasList) {
		log.debug("VerificaDocumentiPosizioniDebitorieJob: processo Alias {}. Setto ORMHelper", alias);
		try {
		    setORMHelper(alias, software);
		    log.debug("VerificaDocumentiPosizioniDebitorieJob: processo Alias {}. ORMHelper Settato", alias);
		    // Per ogni riga esegue la chiamata al nodo dei pagamenti per verificare se
		    // presente il
		    // documento richiesto.
		    // se si segna la chiamata come completa ed invoca un nuovo
		    // evento fattura generata o avviso generato (oppure documento generato passando
		    // il tipo di documento)
		    log.debug("VerificaDocumentiPosizioniDebitorieJob: processo i dati per l'alias {}", alias);
		    IstanzeoneriPosdebBatchService service = getBeanOfType(IstanzeoneriPosdebBatchService.class.getName());
		    service.updateProcessaDocumentiNonCompleti(numeroRecordDaElaborare);
		    log.debug("VerificaDocumentiPosizioniDebitorieJob: dati per Alias {} processati", alias);
		} catch (Exception e) {
		    log.error("errore nel processamento dell'alias {}, {}", alias, e);
		}
	    }
	} catch (Exception e1) {
	    log.error("{}", e1);
	}
	log.info("VerificaDocumentiPosizioniDebitorieJob: end");
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put(PARAM_NUMERO_RECORD_DA_ELABORARE, PARAM_NUMERO_RECORD_DA_ELABORARE_DESC);
	m.put(PARAM_LISTA_ALIAS_DA_ELABORARE, PARAM_LISTA_ALIAS_DA_ELABORARE_DESC);
	return m;
    }
}
