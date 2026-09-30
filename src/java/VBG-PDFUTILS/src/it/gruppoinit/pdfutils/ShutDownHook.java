package it.gruppoinit.pdfutils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ShutDownHook implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

	/*
	try {
	    // Get a reference to the Scheduler and shut it down             
	    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	    Scheduler scheduler = (Scheduler) context.getBean("quartzSchedulerFactory");
	    scheduler.shutdown(true);
	    // Security.removeProvider("BC");
	    // Sleep for a bit so that we don't get any errors             
	    Thread.sleep(1000);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	*/
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

    }
}
