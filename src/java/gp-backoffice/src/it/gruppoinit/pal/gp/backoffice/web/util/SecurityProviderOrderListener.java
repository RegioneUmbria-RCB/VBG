package it.gruppoinit.pal.gp.backoffice.web.util;

import java.security.Provider;
import java.security.Security;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityProviderOrderListener implements ServletContextListener {

    private static final String LOG_STRING = "==>{}";
    private static final Logger log = LoggerFactory.getLogger(SecurityProviderOrderListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

	// Non devo fare niente
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

	log.debug("=== Controllo ordine provider ===");
	Provider[] providers = Security.getProviders();
	for (Provider p : providers) {
	    log.debug(LOG_STRING, p.getName());
	}
	try {
	    log.debug("Aggiungo il provider BouncyCastleProvider");
	    Security.addProvider(new BouncyCastleProvider());
	} catch (Exception e) {
	    log.error("Errore nel riorganizzare i provider: " + e.getMessage(), e);
	}
	// Stampa finale
	log.debug("=== Provider finali ===");
	for (Provider p : Security.getProviders()) {
	    log.debug(LOG_STRING, p.getName());
	}
    }
}
