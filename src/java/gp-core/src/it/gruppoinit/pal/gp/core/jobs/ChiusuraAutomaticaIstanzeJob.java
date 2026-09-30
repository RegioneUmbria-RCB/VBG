package it.gruppoinit.pal.gp.core.jobs;

import java.util.HashMap;
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
import it.gruppoinit.pal.gp.core.service.IstanzeManager;
import it.gruppoinit.pal.gp.core.service.rules.OperazioniAutomaticheBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

public class ChiusuraAutomaticaIstanzeJob extends GenericParam implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(ChiusuraAutomaticaIstanzeJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("execute");
	OperazioniAutomaticheBusinessRules opautBusinessRules = new OperazioniAutomaticheBusinessRules();
	opautBusinessRules.setOperazioneAutomatica(true);
	SigeproBusinessRules.setClassRules(OperazioniAutomaticheBusinessRules.class, opautBusinessRules);
	try {
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    boolean chiudiLeInterrotteSospese = false;
	    String chiudiLeSospese = (String) ctx.getJobDetail().getJobDataMap().get(PARAM_CHIUDI_LE_ISTANZE_SOSPESE);
	    if (StringUtils.defaultString(chiudiLeSospese, "N").equalsIgnoreCase("S")) {
		chiudiLeInterrotteSospese = true;
	    }
	    IstanzeManager service = (IstanzeManager) ContextLoader.getCurrentWebApplicationContext().getBean("istanzeManagerImpl");
	    log.info("inizio l'aggiornamento");
	    service.updateProcessaIstanzedaChiudere(idcomunealias, software, true, chiudiLeInterrotteSospese);
	    log.info("aggiornamento completato");
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

	Map<String, String> m = new HashMap<String, String>();
	m.put(PARAM_CHIUDI_LE_ISTANZE_SOSPESE,
		"Specificare se devono venire chiuse anche le istanze che risultano nello stato sospesa o interrotta. valori S o N. Predefinito N");
	return m;
    }

    private final static String PARAM_CHIUDI_LE_ISTANZE_SOSPESE = "CHIUDI_LE_ISTANZE_SOSPESE";
}
