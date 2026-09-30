package it.gruppoinit.pal.firma.servlet;

import it.gruppoinit.pal.firma.DeleteFilesJob;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppServletContextListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(AppServletContextListener.class);
    private int repeatIntervalInHours = 8;
    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

	log.info("FIRMA START");
	try {
	    log.info("starting scheduler for file deletion");
	    // Grab the Scheduler instance from the Factory 
	    scheduler = StdSchedulerFactory.getDefaultScheduler();
	    // and start it off
	    scheduler.start();
	    // define the job and tie it to our HelloJob class
	    JobDetail job = JobBuilder.newJob(DeleteFilesJob.class).withIdentity("job1", "group1").build();
	    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
		    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(repeatIntervalInHours).repeatForever()).build();
	    // Tell quartz to schedule the job using our trigger
	    scheduler.scheduleJob(job, trigger);
	} catch (SchedulerException se) {
	    se.printStackTrace();
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

	log.info("FIRMA STOP");
	try {
	    log.info("stopping scheduler for file deletion");
	    scheduler.shutdown(true);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
    }
}
