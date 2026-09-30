package it.gruppoinit.pal.gp.core.jobs;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.service.ArchiviazioneDocumentiDaFileSystemManager;

public class ArchiviazioneDocumentiDaFileSystemJob extends GenericParam implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioneDocumentiDaFileSystemJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("execute");
	try {
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String tipomov = (String) ctx.getJobDetail().getJobDataMap().get(ArchiviazioneDocumentiDaFileSystemJob.PARAM_TIPO_MOVIMENTO);
	    String pathRepository = (String) ctx.getJobDetail().getJobDataMap().get(ArchiviazioneDocumentiDaFileSystemJob.PARAM_PATH_REPOSITORY_DOC);
	    ArchiviazioneDocumentiDaFileSystemManager service = (ArchiviazioneDocumentiDaFileSystemManager) ContextLoader
		    .getCurrentWebApplicationContext().getBean("archiviazioneDocumentiDaFileSystemManagerImpl");
	    service.eseguiArchiviazione(idcomunealias, software, tipomov, pathRepository);
	    log.info("execute end");
	} catch (Exception e) {
	    log.error("execute", e);
	    throw new JobExecutionException(e.getMessage());
	}
    }

    //    public enum TipoEndo {
    //	TIPO_MOVIMENTO
    //    }
    //
    //    public static String TipoEndo(TipoEndo tipoEndo) {
    //
    //	switch (tipoEndo) {
    //	case TIPO_MOVIMENTO:
    //	    return "Codice del tipo movimento in cui allegare i documenti";
    //	default:
    //	    return "";
    //	}
    //    }
    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put("TIPO_MOVIMENTO", "Codice tipo movimento a cui allegare gli allegati");
	m.put("PATH_REPOSITORY_DOC", "Percorso dove sono salvati i documenti da recuperare");
	return m;
    }

    private final static String PARAM_TIPO_MOVIMENTO = "TIPO_MOVIMENTO";
    private final static String PARAM_PATH_REPOSITORY_DOC = "PATH_REPOSITORY_DOC";
}
