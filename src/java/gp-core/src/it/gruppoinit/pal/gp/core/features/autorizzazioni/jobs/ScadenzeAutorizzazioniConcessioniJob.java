package it.gruppoinit.pal.gp.core.features.autorizzazioni.jobs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.IScadenzeAutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniScadenzeAutorizzazioniConcessioniException;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;
import it.gruppoinit.pal.gp.core.service.impl.JobSchedulerManagerImpl;

public class ScadenzeAutorizzazioniConcessioniJob extends BaseJob implements StatefulJob {

    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE = "LISTA_ALIAS_DA_ELABORARE";
    private static final String PARAM_LISTA_ALIAS_DA_ELABORARE_DESC = "La lista degli alias da elaborare separata da \";\". Se non specificato prende la lista dalla security";
    private static final String OVERRIDE_PARAM_LISTA_ALIAS_DA_ELABORARE = "scheduler.ScadenzeAutorizzazioniConcessioniJob.param.LISTA_ALIAS_DA_ELABORARE";
    private static final String OVERRIDE_ESEGUI = "scheduler.ScadenzeAutorizzazioniConcessioniJob.esegui";

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	boolean esegui = false;
	try {
	    Properties systemSchedulerProperties = getSystemSchedulerProperties();
	    esegui = esegui(systemSchedulerProperties);
	    if (esegui) {
		List<String> aliasDaProcessare = getAliasDaProcessareLocale(ctx, systemSchedulerProperties);
		for (String alias : aliasDaProcessare) {
		    processaAlias(alias);
		}
	    }
	} catch (IOException e1) {
	    log.error("Non è stato possibile recuperare la classe istanziata per " + IScadenzeAutorizzazioniConcessioniService.class + ". Errore:" +
		      e1.getMessage(),
		    e1);
	}
    }

    private void processaAlias(String alias) {

	ORMHelper.setIdcomuneAlias(alias);
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	try {
	    IScadenzeAutorizzazioniConcessioniService service = getBeanOfType(IScadenzeAutorizzazioniConcessioniService.class.getName());
	    service.sistemaScadenzeDelleAutorizzazioniPerAlias(alias);
	} catch (ClassNotFoundException e) {
	    log.error("Non è stato possibile recuperare la classe istanziata per " + IScadenzeAutorizzazioniConcessioniService.class + ". Errore:" +
		      e.getMessage(),
		    e);
	} catch (OperazioniScadenzeAutorizzazioniConcessioniException e) {
	    log.error("Si è verificato il seguente problema durante l'elaborazione delle scadenze:" + e.getMessage(), e);
	}
    }

    private List<String> getAliasDaProcessareLocale(JobExecutionContext ctx, Properties systemSchedulerProperties) {

	String parametri = StringUtils.defaultString(systemSchedulerProperties.getProperty(OVERRIDE_PARAM_LISTA_ALIAS_DA_ELABORARE), "");
	if (StringUtils.isBlank(parametri)) {
	    return super.getAliasDaProcessare(ctx, PARAM_LISTA_ALIAS_DA_ELABORARE);
	}
	List<String> aliasDaProcessare = new ArrayList<String>();
	String[] ps = parametri.split(",");
	for (String alias : ps) {
	    if (StringUtils.isNotBlank(alias)) {
		aliasDaProcessare.add(alias.trim());
	    }
	}
	return aliasDaProcessare;
    }

    private boolean esegui(Properties systemSchedulerProperties) {

	boolean esegui = StringUtils
		.defaultString(systemSchedulerProperties.getProperty(JobSchedulerManagerImpl.SCHEDULER_ABILITA_ATTIVITA_SISTEMA), "false")
		.equalsIgnoreCase("true");
	if (esegui) {
	    return StringUtils.defaultString(systemSchedulerProperties.getProperty(OVERRIDE_ESEGUI), "true").equalsIgnoreCase("true");
	}
	return esegui;
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put(PARAM_LISTA_ALIAS_DA_ELABORARE, PARAM_LISTA_ALIAS_DA_ELABORARE_DESC);
	return m;
    }

    private Properties getSystemSchedulerProperties() throws IOException {

	Properties p = new Properties();
	InputStream is = getClass().getClassLoader().getResourceAsStream(JobSchedulerManagerImpl.SYSTEM_SCHEDULER_PROPERTIES);
	p.load(is);
	is.close();
	return p;
    }
}
