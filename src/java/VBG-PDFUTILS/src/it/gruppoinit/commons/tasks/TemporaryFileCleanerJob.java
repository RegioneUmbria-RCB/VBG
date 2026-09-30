package it.gruppoinit.commons.tasks;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TemporaryFileCleanerJob extends QuartzJobBean {

    private static Logger LOG = LoggerFactory.getLogger(TemporaryFileCleanerJob.class);
    private static Set<URI> listaFiles = new HashSet<URI>();

    public static Set<URI> getListaFiles() {

	if (listaFiles == null) {
	    listaFiles = new HashSet<URI>();
	}
	return listaFiles;
    }

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

	LOG.debug("inizio la pulizia dei file temporanei");
	long now = System.currentTimeMillis();
	Set<URI> listaFiles = TemporaryFileCleanerJob.getListaFiles();
	LOG.debug("Lista dei file da eliminare");
	Set<URI> toRemove = new HashSet<URI>();
	for (URI uri : listaFiles) {
	    File f = new File(uri);
	    LOG.debug("processo il file {}", f);
	    if (f.exists()) {
		if ((f.lastModified() + 560000) < now) {
		    try {
			LOG.debug("elimino il file {}", f);
			if (f.delete()) {
			    toRemove.add(uri);
			} else {
			    LOG.error("errore nella cancellazione del file {}: {}", f);
			}
		    } catch (Exception e) {
			LOG.error("errore nella cancellazione del file {}: {}", f, e);
		    }
		}
	    }
	}
	LOG.debug("Elimino dalla lista i file {}", toRemove);
	listaFiles.removeAll(toRemove);
    }
}