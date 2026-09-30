package it.gruppoinit.pal.gp.core.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ProtocollazioneManager;
import it.gruppoinit.pal.gp.core.utils.LoggerRitentaProtocollazioneIstanzaFallite;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class RitentaProtocollazioneIstanzaFalliteJob extends BaseJob implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(RitentaProtocollazioneIstanzaFalliteJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	Date dataStart = new Date();
	boolean go = true;
	String _dataStart = Utilities.formatDate(dataStart, true);
	LoggerRitentaProtocollazioneIstanzaFallite.logInfo("--------------------------------------------------------------------------");
	LoggerRitentaProtocollazioneIstanzaFallite.logInfo("--------------------------------------------------------------------------");
	LoggerRitentaProtocollazioneIstanzaFallite.logInfo("START TASK: {} at {}", new Object[] { ctx.getJobDetail().getName(), _dataStart });
	///
	String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	String _software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	// recupero parametri
	//.
	log.debug("execute# Recupero i dati dai parametri...");
	String _swAttivi = StringUtils
		.defaultIfEmpty((String) ctx.getJobDetail().getJobDataMap().get(RitentaProtocollazioneIstanzaFalliteJob.PARAM_SW_ATTIVI), "");
	String[] swAttivi = StringUtils.split(_swAttivi, ";");
	List<String> swDaCiclare = new ArrayList<String>();
	//.
	String _codicetipoprotocollazione = StringUtils.defaultIfEmpty(
		(String) ctx.getJobDetail().getJobDataMap().get(RitentaProtocollazioneIstanzaFalliteJob.PARAM_TIPO_PROTOCOLLAZIONE), "");
	String[] codicetipoprotocollazione = StringUtils.split(_codicetipoprotocollazione, ";");
	//. Gestione software per cui attivare la procedura, se il parametro PARAM_SW_ATTIVI popolato sovrascrive il valore del software impostato 
	// nella pagina principale. (Software TT nella pagina principale non accettato come parametro, se si vuole attivare per più software
	// usare il parametro SW_ATTIVI separandoli con ';')
	log.debug("execute# Logica recupero software per cui eseguire l'operazione.....");
	String _swDaCiclare = "";
	if (swAttivi != null && swAttivi.length > 0) {
	    swDaCiclare = Arrays.asList(swAttivi);
	    _swDaCiclare = StringUtils.join(swAttivi, ",");
	    log.debug("execute# Software selezionati dal parametro {} = {}", RitentaProtocollazioneIstanzaFalliteJob.PARAM_SW_ATTIVI, _swDaCiclare);
	} else {
	    if (!_software.equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
		swDaCiclare.add(_software);
		_swDaCiclare = StringUtils.join(swDaCiclare.toArray(), ",");
		log.debug("execute# Software selezionati dal parametro sw del job = {}", _swDaCiclare);
	    } else {
		LoggerRitentaProtocollazioneIstanzaFallite.logInfo("JOB ATTIVATO PER SOFTWARE TT, IMPOSSIBILE PROCEDERE");
		go = false;
	    }
	}
	//.
	if (go) {
	    log.debug("execute# Istanzio service...");
	    IstanzeService istanzeService = (IstanzeService) ContextLoader.getCurrentWebApplicationContext().getBean("istanzeServiceImpl");
	    ProtocollazioneManager protocollazioneManager = (ProtocollazioneManager) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("protocollazioneManagerImpl");
	    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("                                                                   ");
	    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("PARAMETRI:");
	    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("{}: {}", new Object[] { WebConstants.IDCOMUNE_ALIAS, idcomunealias });
	    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("{}: {}",
		    new Object[] { RitentaProtocollazioneIstanzaFalliteJob.PARAM_SW_ATTIVI, _swDaCiclare });
	    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("{}: {}",
		    new Object[] { RitentaProtocollazioneIstanzaFalliteJob.PARAM_TIPO_PROTOCOLLAZIONE, _codicetipoprotocollazione });
	    try {
		for (String sw : swDaCiclare) {
		    LoggerRitentaProtocollazioneIstanzaFallite
			    .logInfo("------------------------- ESEGUO TASK PER SOFTWARE: {} ----------------------", sw);
		    setORMHelper(idcomunealias, sw);
		    LoggerRitentaProtocollazioneIstanzaFallite.logInfo(
			    "Recupero tutte le istanze con protocollazione fallita. istanze.tipoProtFallita != null, istanze.numeroprotocollo != null, tipo protocollazione = {}",
			    _codicetipoprotocollazione);
		    List<Integer> istanzeDaElaborare = istanzeService
			    .findIstanzaProtocollazioneFallitaByTipoProtocollazione(codicetipoprotocollazione);
		    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("Numero istanze trovate = {}", istanzeDaElaborare.size());
		    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("                                                                   ");
		    for (Integer codiceIstanza : istanzeDaElaborare) {
			Map<String, String> result = protocollazioneManager.insertRipetiProtocollazioneFallitaIstanza(codiceIstanza);
			boolean isGeneraRicevuta = true;
			for (Map.Entry<String, String> entry : result.entrySet()) {
			    if (entry.getKey().equals("NULL")) {
				isGeneraRicevuta = false;
			    }
			    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("{}", "PROTOCOLLO: " + entry.getValue());
			}
			if (isGeneraRicevuta) {
			    String resultRicevuta = protocollazioneManager.insertNuovaRicevutaPratica(codiceIstanza);
			    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("{}", "RICEVUTA: " + resultRicevuta);
			} else {
			    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("{}", "RICEVUTA: Non generata, non è stato creato un protocollo");
			}
			LoggerRitentaProtocollazioneIstanzaFallite
				.logInfo("-------------------------------------------------------------------------");
		    }
		    ///////////////////////////////////////// LOGICA /////////////////////////////////
		    try {
		    } catch (Exception e) {
			LoggerRitentaProtocollazioneIstanzaFallite.logError("execute per software", e);
			JobExecutionException e2 = new JobExecutionException(e);
			// Quartz will automatically unschedule all triggers associated with this job so that it does not run again
			//e2.setUnscheduleAllTriggers(true);
			throw e2;
		    }
		    LoggerRitentaProtocollazioneIstanzaFallite.logInfo("------------------------- END TASK PER SOFTWARE: {} ----------------------",
			    sw);
		}
		//updateLastDurationTime(dataStart);
	    } catch (Exception e) {
		LoggerRitentaProtocollazioneIstanzaFallite.logError("execute", e);
		JobExecutionException e2 = new JobExecutionException(e);
		// Quartz will automatically unschedule all triggers associated with this job so that it does not run again
		//e2.setUnscheduleAllTriggers(true);
		throw e2;
	    } finally {
		resetThreadLocalVars();
	    }
	}
	Date dataEnd = new Date();
	String _dataEnd = Utilities.formatDate(dataEnd, true);
	LoggerRitentaProtocollazioneIstanzaFallite.logInfo("END TASK: {} at {}", new Object[] { ctx.getJobDetail().getName(), _dataEnd });
	LoggerRitentaProtocollazioneIstanzaFallite
		.logInfo("------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put("TIPO_PROTOCOLLAZIONE",
		"Tipo protocollazione da ripetere, possono essere inserite più tipologie separate da ';'. Usare il codice e non la descrizione IstanzeService.TipoInserimento Es 1;2");
	m.put("SW_ATTIVI",
		"Software per cui eseguire le operazioni, sovrascrive il valore nella pagina principale. Può assumere più valori separati da ';'. Es. SS;CO");
	return m;
    }

    private final static String PARAM_TIPO_PROTOCOLLAZIONE = "TIPO_PROTOCOLLAZIONE";
    private final static String PARAM_SW_ATTIVI = "SW_ATTIVI";
}
