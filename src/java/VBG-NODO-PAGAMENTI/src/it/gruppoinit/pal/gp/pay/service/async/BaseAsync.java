package it.gruppoinit.pal.gp.pay.service.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public abstract class BaseAsync implements Runnable {

    private long connectionTimeOut = 12000;
    private long receivetimeout = 600000;
    private static final Logger log = LoggerFactory.getLogger(BaseAsync.class);
    protected ApplicationContext applicationContext;
    protected String idOperazione;

    public abstract String getIdOperazione();

    @SuppressWarnings("unchecked")
    public <T> T getBeanOfType(Class<T> type) {

	return applicationContext.getBean(type);
    }

    @Override
    public void run() {

	this.idOperazione = getIdOperazione();
	try {
	    log.debug("idOperazione {}", idOperazione);
	    process();
	} catch (Exception e) {
	    log.error("idOperazione " + idOperazione + ", Errore " + e.getMessage(), e);
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }

    public abstract void process() throws AsyncProcessException;
}
