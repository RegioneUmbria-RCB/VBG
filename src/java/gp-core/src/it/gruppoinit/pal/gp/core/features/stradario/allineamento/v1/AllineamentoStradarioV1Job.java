package it.gruppoinit.pal.gp.core.features.stradario.allineamento.v1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;

public class AllineamentoStradarioV1Job extends BaseJob implements StatefulJob {

    private static final String CODICI_COMUNE = "CODICI_COMUNE";
    private static final Logger log = LoggerFactory.getLogger(AllineamentoStradarioV1Job.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("execute");
	try {
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String listaCodiciComune = (String) ctx.getJobDetail().getJobDataMap().get(CODICI_COMUNE);
	    Set<String> s = new HashSet<String>();
	    if (StringUtils.isNotBlank(listaCodiciComune)) {
		String[] codiciComune = listaCodiciComune.split(",");
		if (codiciComune != null && codiciComune.length > 0) {
		    for (String cod : codiciComune) {
			s.add(cod);
		    }
		}
	    }
	    setORMHelper(idcomunealias, software);
	    StradarioManager service = (StradarioManager) ContextLoader.getCurrentWebApplicationContext().getBean("stradarioManagerImpl");
	    service.allineaStradario(s);
	    log.info("execute end");
	} catch (Exception e) {
	    log.error("execute", e);
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> h = new HashMap<String, String>();
	h.put(CODICI_COMUNE,
		"Se specificato, in installazione multicomune, vengono processati solamente i codicicomune della lista, separata da virgola");
	return h;
    }
}