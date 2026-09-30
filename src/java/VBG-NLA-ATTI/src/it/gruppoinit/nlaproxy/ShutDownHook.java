package it.gruppoinit.nlaproxy;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class ShutDownHook implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

	try {
	    // Get a reference to the Scheduler and shut it down             
	    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	    Scheduler scheduler = (Scheduler) context.getBean("quartzSchedulerFactory");
	    if (scheduler != null) {
		scheduler.shutdown(true);
		// Security.removeProvider("BC");
		// Sleep for a bit so that we don't get any errors             
		Thread.sleep(1000);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

    }
}
