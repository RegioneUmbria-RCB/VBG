package it.gruppoinit.pal.gp.backoffice.web.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySessionListener implements HttpSessionListener {

    private static final Logger log = LoggerFactory.getLogger(MySessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {

	if (log.isDebugEnabled())
	    log.debug("event sessionCreated: " + sessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {

	if (log.isDebugEnabled())
	    log.debug("event sessionDestroyed: " + sessionEvent.getSession().getId());
    }
}
