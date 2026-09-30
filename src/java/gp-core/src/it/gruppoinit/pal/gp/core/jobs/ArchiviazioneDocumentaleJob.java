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
import it.gruppoinit.pal.gp.core.service.ArchiviazioniManager;

public class ArchiviazioneDocumentaleJob extends GenericParam implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioneDocumentaleJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("execute");
	try {
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String softwares = (String) ctx.getJobDetail().getJobDataMap().get(ArchiviazioneDocumentaleJob.PARAM_SOFTWARES);
	    ArchiviazioniManager service = (ArchiviazioniManager) ContextLoader.getCurrentWebApplicationContext().getBean("archiviazioniManagerImpl");
	    String[] arraySoftware = StringUtils.split(softwares, ",");
	    if (arraySoftware.length > 0) {
		service.archiviazione(idcomunealias, arraySoftware);
	    } else {
		String[] s = new String[1];
		s[0] = software;
		service.archiviazione(idcomunealias, s);
	    }
	    //  service.eseguiArchiviazionePerIstanza(idcomunealias, software);
	    log.info("execute end");
	} catch (Exception e) {
	    log.error("execute", e);
	    throw new JobExecutionException(e.getMessage());
	}
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put("SOFTWARES", "Lista software per cui effettuare la conservazione dei documento. Indicare i codici separati da ','");
	return m;
    }

    private final static String PARAM_SOFTWARES = "SOFTWARES";
}
