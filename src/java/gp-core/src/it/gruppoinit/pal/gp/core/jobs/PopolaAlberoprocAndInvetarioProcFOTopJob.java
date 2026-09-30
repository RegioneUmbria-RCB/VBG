package it.gruppoinit.pal.gp.core.jobs;

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
import it.gruppoinit.pal.gp.core.service.CopiaAlberoprocAndInvetarioProcFOTopManager;

public class PopolaAlberoprocAndInvetarioProcFOTopJob extends GenericParam implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(PopolaAlberoprocAndInvetarioProcFOTopJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("PopolaAlberoprocAndInvetarioProcFOTopJob#execute");
	try {
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String softwares = (String) ctx.getJobDetail().getJobDataMap()
		    .get(PopolaAlberoprocAndInvetarioProcFOTopJob.PARAM_SOFTWARE_PER_CUI_FILTRARE);
	    String intervallogironi = (String) ctx.getJobDetail().getJobDataMap()
		    .get(PopolaAlberoprocAndInvetarioProcFOTopJob.PARAM_INTERVALLO_GIORNI);
	    Integer _intervallogironi = null;
	    if (StringUtils.isNotBlank(intervallogironi)) {
		_intervallogironi = Integer.parseInt(intervallogironi);
	    }
	    String maxInterventi = (String) ctx.getJobDetail().getJobDataMap()
		    .get(PopolaAlberoprocAndInvetarioProcFOTopJob.PARAM_NUMERO_MASSIMO_DI_INTERVENTI);
	    Integer _maxInterventi = null;
	    if (StringUtils.isNotBlank(maxInterventi)) {
		_maxInterventi = Integer.parseInt(maxInterventi);
	    }
	    String maxProcedimenti = (String) ctx.getJobDetail().getJobDataMap()
		    .get(PopolaAlberoprocAndInvetarioProcFOTopJob.PARAM_NUMERO_MASSIMO_DI_PROCEDIMENTI);
	    Integer _maxProcedimenti = null;
	    if (StringUtils.isNotBlank(maxProcedimenti)) {
		_maxProcedimenti = Integer.parseInt(maxProcedimenti);
	    }
	    CopiaAlberoprocAndInvetarioProcFOTopManager service = (CopiaAlberoprocAndInvetarioProcFOTopManager) ContextLoader
		    .getCurrentWebApplicationContext().getBean("copiaAlberoprocAndInvetarioProcFOTopManagerImpl");
	    List<String> _softwares = new ArrayList<String>();
	    if (StringUtils.isNotBlank(softwares)) {
		String[] s = StringUtils.split(softwares, ",");
		_softwares = Arrays.asList(s);
	    }
	    service.eseguiCopia(idcomunealias, software, _softwares, _intervallogironi, _maxInterventi, _maxProcedimenti);
	    log.info("execute end");
	} catch (Exception e) {
	    log.error("PopolaAlberoprocAndInvetarioProcFOTopJob#execute", e);
	    throw new JobExecutionException(e.getMessage());
	}
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put("INTERVALLO_GIORNI*", "Numero interno che riporta un intervallo di giorni per cui effettuare la ricerca");
	m.put("SOFTWARE_PER_CUI_FILTRARE*",
		"Indicare il codice software per cui effettuare le ricerche. Per specificare più software usare la virgola. Es: CO,CE.");
	m.put("NUMERO_MASSIMO_DI_INTERVENTI*", "Indicare il numero massimo di interventi da selezionare");
	m.put("NUMERO_MASSIMO_DI_PROCEDIMENTI*", "Indicare il numero massimo di procedimenti da selezionare");
	return m;
    }

    private final static String PARAM_INTERVALLO_GIORNI = "INTERVALLO_GIORNI*";
    private final static String PARAM_SOFTWARE_PER_CUI_FILTRARE = "SOFTWARE_PER_CUI_FILTRARE*";
    private final static String PARAM_NUMERO_MASSIMO_DI_INTERVENTI = "NUMERO_MASSIMO_DI_INTERVENTI*";
    private final static String PARAM_NUMERO_MASSIMO_DI_PROCEDIMENTI = "NUMERO_MASSIMO_DI_PROCEDIMENTI*";
}
