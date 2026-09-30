package it.gruppoinit.pal.firma;

import java.io.File;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteFilesJob implements Job {

    private static Logger log = LoggerFactory.getLogger(DeleteFilesJob.class);
    private long ageFromNow = 1 * 60 * 60 * 1000;//1h

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

	log.info("deleting files from upload dir: {}", FileManager.uploadDir);
	File uploadDir = new File(FileManager.uploadDir);
	if (uploadDir.exists()) {
	    File[] sessionDirs = uploadDir.listFiles();
	    if (sessionDirs != null) {
		Date today = new Date();
		for (File sessionDir : sessionDirs) {
		    long ttl = (sessionDir.lastModified() + ageFromNow - today.getTime()) / 60000;
		    log.debug("session dir {} ttl={} min.", sessionDir.getName(), ttl);
		    if (ttl < 0) {
			File[] sessionFiles = sessionDir.listFiles();
			if (sessionFiles != null) {
			    for (File file : sessionFiles) {
				if (file.delete()) {
				    log.info("file {} on session dir {} deleted", file.getName(), sessionDir.getName());
				} else {
				    log.warn("file {} on session dir {} NOT deleted!", file.getName(), sessionDir.getName());
				}
			    }
			}
			if (sessionDir.delete()) {
			    log.info("session dir {} deleted", sessionDir.getName());
			    SessionManager.removeSession(sessionDir.getName());
			} else {
			    log.warn("session dir {} NOT deleted!", sessionDir.getName());
			}
		    }
		}
	    }
	} else {
	    log.info("upload dir {} does NOT exist.", FileManager.uploadDir);
	}
    }
}
