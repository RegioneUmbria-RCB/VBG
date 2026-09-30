package it.alveo.segnalazioniproxy.scheduler;

import it.alveo.segnalazioniproxy.clients.StcClient;
import it.alveo.segnalazioniproxy.dao.SgConfigurazioniRepository;
import it.alveo.segnalazioniproxy.dao.SgPraticheRepository;
import it.alveo.segnalazioniproxy.entities.SgConfigurazioni;
import it.alveo.segnalazioniproxy.entities.SgPratiche;
import it.alveo.segnalazioniproxy.enums.Stato;
import it.alveo.segnalazioniproxy.servicies.StcService;
import it.alveo.stc.InserimentoPraticaResponse;
import it.alveo.stc.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

@Component
public class SchedulerSegnalazioni {
    private static final Logger log = LoggerFactory.getLogger(SchedulerSegnalazioni.class);

    // private static final String AUTO_STARTUP_KEY = "scheduler.autoStartup";
    // private final Properties PROPERTIES = new Properties();
    private final Map<Integer, String> MAPPA_TOKENS = new HashMap<>();  // I token del login verso STC hanno durata infinita
    private List<SgConfigurazioni> lastConfigurazioni;  // Variabile per salvare il risultato precedente, usata per verificare se ri-generare i token di login

    private final SgConfigurazioniRepository sgConfigurazioniRepository;
    private final SgPraticheRepository sgPraticheRepository;
    private final StcClient stcClient;
    private final StcService stcService;
    @Value("${scheduler.autoStartup}")
    private Boolean schedulerAutoStartup;

    @Autowired
    public SchedulerSegnalazioni(SgConfigurazioniRepository sgConfigurazioniRepository, SgPraticheRepository sgPraticheRepository, StcClient stcClient, StcService stcService) {
        this.sgConfigurazioniRepository = sgConfigurazioniRepository;
        this.sgPraticheRepository = sgPraticheRepository;
        this.stcClient = stcClient;
        this.stcService = stcService;
    }


    // Metodo schedulato per eseguire ogni 5 minuti
    @Scheduled(fixedRateString = "${scheduler.msFrequency}")
    public void inviaSegnalazioni() {

        // Legge/aggiorna le properties
//        try {
//            getProperties();
//        } catch (Exception e) {
//            log.error("SchedulerSegnalazioni.inviaSegnalazioni - NON è possibile ottenere le proprietà!");
//            log.error("Errore: ", e);
//            log.error("Scheduler interrotto!");
//            return;
//        }

        // Abilita o inibisce lo scheduler in base al parametro nelle application.properties
//        if (!Boolean.parseBoolean(getProperty(AUTO_STARTUP_KEY))) {
//            log.warn("L'autoStartup dello scheduler è disattivato!");
//            return;
//        }
	
	if (!schedulerAutoStartup) {
	    log.warn("L'autoStartup dello scheduler è disattivato!");
	    return;
	}
	
	
	// Recupera e imposta le configurazioni, se variate rigenera i token
        getAndCompareConfigurazioni();
        
        generateLoginTokens(); // i Token STC li faccio rigenerare comunque operazione veloce

        log.info("Invio delle segnalazioni in esecuzione...");

        // Recupera tutte le segnalazioni con Stato=INVIATA
        List<SgPratiche> pratiche = sgPraticheRepository.findAllByStato(Stato.INVIATA);
        if (pratiche.isEmpty()) {
            log.warn("Non sono presenti pratiche da inviare");
            return;
        }

        log.info("Inizio invio pratiche INVIATE a STC...");

        // NON in parallelo per evitare che lato Backoffice si blocchi
        for (SgPratiche pratica : pratiche) {
            // Invia la pratica ad STC, una per volta
            log.info("Invio pratica con uuid: `" + pratica.getUuid() + "`");

            // Recupera i dati della configurazione per ogni pratica
            String alias = pratica.getConfigurazione().getAlias();
            String software = pratica.getConfigurazione().getSoftware();

            try {
                //Inserisce la pratica e in caso di OK salva la pratica aggiornandola
                InserimentoPraticaResponse resp = stcClient.inserimentoPratica(MAPPA_TOKENS.get(pratica.getConfigurazione().getId()), alias, software, pratica.getUuid());
                stcService.savePraticaFromSTC(resp, pratica.getUuid());
                log.info("Invio e aggiornamento su DB per la pratica con uuid: `" + pratica.getUuid() + "` avvenuto con successo");
            } catch (Exception e) {
                log.error("Errore nell'inserimento pratica verso STC: ", e);
            }
        }

        log.info("Invio pratiche a STC terminato");
    }

    /**
     * Recupera dinamicamente le proprietà dal file application.properties
     *
     * @throws Exception the exception
     *
    public void getProperties() throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File application.properties not found in classpath");
            }
            PROPERTIES.load(inputStream);
        }
    }

    public String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }


    /**
     * Genera preventivamente i token delle login e li inserisce in una mappa idConfigurazione-codiceToken
     */
    private void generateLoginTokens() {
        log.info("Generazione preventiva tokens login...");

        String alias;
        String software;
        LoginResponse loginResponse;

        // Genera preventivamente i token di login per tutte le configurazioni
        for (SgConfigurazioni configurazione : lastConfigurazioni) {
            alias = configurazione.getAlias();
            software = configurazione.getSoftware();
            loginResponse = stcClient.login(alias, software);

            MAPPA_TOKENS.put(configurazione.getId(), loginResponse.getToken());
        }

        log.info("Fine generazione tokens login. Token creati: `" + MAPPA_TOKENS.size() + "`");
    }

    /**
     * Recupera le configurazioni e confronta con quelle passate
     *
     * @return una lista di <code>SgConfigurazioni</code> se diverse da quelle precedenti, altrimenti <code>null</code>
     */
    private boolean getAndCompareConfigurazioni() {
        List<SgConfigurazioni> currentConfigurazioni = sgConfigurazioniRepository.findAll();
        if (currentConfigurazioni.isEmpty()) {
            throw new RuntimeException("SchedulerSegnalazioni.getAndCompareConfigurazioni - NON sono presenti configurazioni! Scheduler interrotto!");
        }

        if (!Objects.equals(lastConfigurazioni, currentConfigurazioni)) {
            log.info("Trovate `" + currentConfigurazioni.size() + "` configurazioni.");
            log.info("Rigenera i token perché sono variate le Configurazioni");

            // Aggiorna la copia salvata
            lastConfigurazioni = currentConfigurazioni;
            return false;
        } else {
            log.info("Configurazioni invariate, token mantenuti");
            return true;
        }
    }

}
