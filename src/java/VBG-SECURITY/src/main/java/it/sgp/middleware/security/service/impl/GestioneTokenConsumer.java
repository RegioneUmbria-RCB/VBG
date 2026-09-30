package it.sgp.middleware.security.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.sgp.middleware.security.configuration.SecurityConfiguration;
import it.sgp.middleware.security.service.ComunisecuritySessionService;

@Component
public class GestioneTokenConsumer {

    @Autowired
    private SecurityConfiguration securityConfiguration;
    private static Logger log = LoggerFactory.getLogger(GestioneTokenConsumer.class);
    @Autowired
    private ComunisecuritySessionService comunisecuritySessionService;

    @Scheduled(cron = "${security.delete-token-cron}")
    public void deleteToken() {

	// Cancello tutti i token con data precedente a quella del momento, il metodo dopo la cancellazione ritorna una strin buffer
	// che contiene tutti i record che abbiamo cancellato, nel caso venga generato un errore il file ripoerterà anche l'errore che si è
	// presentato
	deleteToken(securityConfiguration.isDeleteTokenEnabled(), securityConfiguration.isDeleteTokenSaveFile());
    }

    public void deleteToken(boolean isAbilitato, boolean salvaSuFile) {

	// Cancello tutti i token con data precedente a quella del momento, il metodo dopo la cancellazione ritorna una strin buffer
	// che contiene tutti i record che abbiamo cancellato, nel caso venga generato un errore il file ripoerterà anche l'errore che si è
	// presentato
	if (isAbilitato) {
	    try {
		log.info("Inizio cancellazione token con data di ultimo accesso minore di {}", new Date());
		comunisecuritySessionService.deleteBeforeDate(new Date(), salvaSuFile);
		log.info("Terminata cancellazione token con data di ultimo accesso minore di {}", new Date());
	    } catch (Exception e) {
		log.error("Errore in cancellazione del token {}", e.getMessage(), e);
	    }
	}
    }
}
