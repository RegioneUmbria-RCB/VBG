package it.alveo.ricalcoloaree.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.micrometer.common.util.StringUtils;
import it.alveo.ricalcoloaree.bean.RicalcoloAreeInBean;
import it.alveo.ricalcoloaree.clients.SigeproSecurityClient;
import it.alveo.ricalcoloaree.configurations.EnteConfig;
import it.alveo.ricalcoloaree.configurations.RicalcoloAreeAppPropConfig;
import it.alveo.ricalcoloaree.constants.WebConstants;
import it.alveo.ricalcoloaree.reqdata.RicalcoloRequest;
import it.alveo.ricalcoloaree.respdata.ErrResponse;
import it.alveo.ricalcoloaree.respdata.RicalcoloResponse;
import it.alveo.ricalcoloaree.services.IstanzeAreeService;
import it.alveo.ricalcoloaree.sigeprosecurity.CheckTokenResponse;
import it.alveo.ricalcoloaree.sigeprosecurity.GetDbConnectionInfoResponse;
import it.alveo.ricalcoloaree.utils.LogUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@RestController
@RequestMapping("/rest-api")
public class RicalcoloAreeController {

    @Autowired
    private IstanzeAreeService istanzeAreeService;
    @Autowired
    private SigeproSecurityClient sigeproSecurityClient;
    @Autowired
    private RicalcoloAreeAppPropConfig ricalcoloAreeAppPropConfig;
    @Autowired
    private Environment environment;
    private static final ConcurrentHashMap<String, EnteConfig> cacheConfigs = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, EntityManagerFactory> cacheDB = new ConcurrentHashMap<>();

    @GetMapping("/aree/{token}/{ricalcoloAreeID}")
    public ResponseEntity<?> getAree(@PathVariable String token, @PathVariable String ricalcoloAreeID) {

        if (!this.tokenValido(token)) {
            ErrResponse errResp = new ErrResponse();
            errResp.setCodice("401");
            errResp.setMessaggio("Token non valido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResp);
        }
        String methodName = "getAree(...)";
        LogUtil.info(this, methodName, "starting GET for sessionID " + ricalcoloAreeID);
        var enteConfig = getConfig(this.getAliasByToken(token));
        try (EntityManager entityManager = enteConfig.getEntityManagerFactory().createEntityManager()) {
            return ResponseEntity.status(HttpStatus.OK).body(istanzeAreeService
                    .getProcessByRicacloloAreeId(enteConfig.getIdcomune(), ricalcoloAreeID, entityManager));
        } catch (Exception e) {
            LogUtil.error(this, methodName, e.getMessage(), e);
            ErrResponse errResp = new ErrResponse();
            errResp.setCodice("400");
            errResp.setMessaggio("Errore durante la chiamata post, vedere i log per i dettagli");
            errResp.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResp);
        }
    }

    @PostMapping("/aree/{token}/{software}/ricalcolo")
    public ResponseEntity<?> ricalcolaAree(@RequestParam(required = false) String type, @PathVariable String token, @PathVariable String software,
            @RequestBody RicalcoloRequest request) {

        if (!this.tokenValido(token)) {
            ErrResponse errResp = new ErrResponse();
            errResp.setCodice("401");
            errResp.setMessaggio("Token non valido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResp);
        }
        String methodName = "ricalcolaAree(...)";
        LogUtil.info(this, methodName, "method reached");
        logRequestInfo(methodName, software, request);
        try {
            // TODO: Per compatibilità temporanea per non modificare il back che non passa
            // l'alias ma il token dell'utente
            var alias = request.getAlias();
            LogUtil.info(this, methodName, "request alias: " + alias);
            if (StringUtils.isBlank(alias)) {
                alias = this.getAliasByToken(token);
                LogUtil.info(this, methodName, "aliasByToken: " + alias);
            }
            var enteConfig = getConfig(alias);
            Date dateDA = parseData(request.getDataPresentazioneDa());
            Date dateA = parseData(request.getDataPresentazioneA());

            List<String> idRicalcoloAreeLResponse;
            if (request.getRicalcoloAreeIdL() == null) {
                String idRicalcoloAree = UUID.randomUUID().toString();
                inserisciInRicalcoloAree(enteConfig, idRicalcoloAree);
                idRicalcoloAreeLResponse = Arrays.asList(idRicalcoloAree);
            } else {
                idRicalcoloAreeLResponse = request.getRicalcoloAreeIdL();
            }
            RicalcoloAreeInBean ricalcoloAreeInBean = new RicalcoloAreeInBean(dateDA, dateA, software,
                    enteConfig.getIdcomune(), !idRicalcoloAreeLResponse.isEmpty() ? idRicalcoloAreeLResponse.get(0)
                            : UUID.randomUUID().toString(),
                    request.getIdAree());
            
            /* questo parametro servirà per gestire il ricalcolo su singola istanza:
             * se non valorizzato oppure diverso da 'singleistanza' allora tutto è as is
             * altrimenti dovrà esserci una sola testata e per quella singola testata non dovrà esserci
             * più di un record nella tabella ricalcoloareeistanze.
             * Ho deciso di adottare questa soluzione perché voglio evitare di rischiare una chiamata su singola istanza da backoffice
             * coinvolgendo una testata con più istanze, e se non adottassi questa soluzione, in alternativa dovrei fare check lato client,
             * il che comporterebbe maggiore effort + query per i check su singola chiamata
             */
            ricalcoloAreeInBean.setType(type);
            
            istanzeAreeService.ricalcolaAree(ricalcoloAreeInBean, enteConfig.getEntityManagerFactory(),
                    request.getRicalcoloAreeIdL());
            LogUtil.info(this, methodName, "method completed");
            return ResponseEntity.status(HttpStatus.OK).body(new RicalcoloResponse(idRicalcoloAreeLResponse));
        } catch (Exception e) {
            return handleException(e, methodName);
        }
    }

    private boolean tokenValido(String token) {

        CheckTokenResponse resp = sigeproSecurityClient.checkToken(token, ricalcoloAreeAppPropConfig);
        return resp.isValid();
    }

    private void logRequestInfo(String methodName, String software, RicalcoloRequest request) {

        LogUtil.info(this, methodName, "software : " + software);
        LogUtil.info(this, methodName, "ricacloloAreeIdList : " + request.getRicalcoloAreeIdL());
        LogUtil.info(this, methodName, "dateDa : " + request.getDataPresentazioneDa());
        LogUtil.info(this, methodName, "dateA : " + request.getDataPresentazioneA());
        LogUtil.info(this, methodName, "idsArea : " + request.getIdAree());
        LogUtil.info(this, methodName, "alias : " + request.getAlias());
    }

    private ResponseEntity<ErrResponse> handleException(Exception e, String methodName) {

        LogUtil.error(this, methodName, e.getMessage(), e);
        ErrResponse errResp = new ErrResponse();
        errResp.setCodice("400");
        errResp.setMessaggio("Errore durante la chiamata post, vedere i log per i dettagli");
        errResp.setDettaglio(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResp);
    }

    private Date parseData(String data) throws ParseException {

        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        return new SimpleDateFormat(WebConstants.FORMATDATE1).parse(data);
    }

    private void inserisciInRicalcoloAree(EnteConfig enteConfig, String idRicalcoloAree) {

        try (EntityManager entityManager = enteConfig.getEntityManagerFactory().createEntityManager()) {
            istanzeAreeService.insertInRicalcoloAree(enteConfig.getIdcomune(), idRicalcoloAree, entityManager);
        } catch (Exception e) {
            throw e;
        }
    }

    private EnteConfig getConfig(String alias) {

        LogUtil.info(this, "getConfig", "alias: " + alias);
        if (cacheConfigs.get(alias) != null) {
            LogUtil.debug(this, "getConfig", "Configurazione per l'alias " + alias + " presa dalla cache");
            return cacheConfigs.get(alias);
        }
        LogUtil.info(this, "getConfig", "sigeproSecurityClient.getDbByAlias: " + alias);
        GetDbConnectionInfoResponse resp = sigeproSecurityClient.getDbByAlias(alias, ricalcoloAreeAppPropConfig);
        // 1. Parametri iniziali
        var enteConfig = EnteConfig.fromGetDbConnectionInfoResponse(environment, resp);
        LogUtil.info(this, "getConfig", "enteConfig " + enteConfig);
        cacheConfigs.put(alias, enteConfig);
        // 2. Connessioni al db, verifico se presente in cache
        var key = enteConfig.getJdbcUrl();
        if (!cacheDB.containsKey(key)) {
            var dataSource = this.createDataSource(enteConfig.getJdbcUrl(), enteConfig.getUtente(), enteConfig.getPwd(),
                    enteConfig.getDriver());
            var entityManagerFactoryBean = this.createEntityManagerFactory(dataSource, enteConfig.getDialect());
            cacheDB.put(key, entityManagerFactoryBean.getObject());
        }
        enteConfig.setEntityManagerFactory(cacheDB.get(key));
        return enteConfig;
    }

    private String getAliasByToken(String token) {

        CheckTokenResponse resp = sigeproSecurityClient.checkToken(token, ricalcoloAreeAppPropConfig);
        return resp.getTokenInfo().getAlias();
    }

    private HikariDataSource createDataSource(String jdbcurll, String utente, String pwd, String drver) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcurll);
        hikariConfig.setUsername(utente);
        hikariConfig.setPassword(pwd);
        hikariConfig.setDriverClassName(drver);
        hikariConfig.setMinimumIdle(environment.getProperty("db.config.minimumIdle", Integer.class)); // Minimo di
                                                                                                      // connessioni nel
                                                                                                      // pool
        hikariConfig.setMaximumPoolSize(environment.getProperty("db.config.maximumPoolSize", Integer.class)); // Massimo
                                                                                                              // di
                                                                                                              // connessioni
                                                                                                              // nel
                                                                                                              // pool
        hikariConfig.setIdleTimeout(environment.getProperty("db.config.idleTimeout", Long.class)); // Tempo massimo di
                                                                                                   // inattività (2 min)
        hikariConfig.setMaxLifetime(environment.getProperty("db.config.maxLifetime", Long.class)); // Connessioni chiuse
                                                                                                   // dopo 30 minuti
        hikariConfig.setConnectionTimeout(environment.getProperty("db.config.connectionTimeout", Long.class)); // Timeout
                                                                                                               // di
                                                                                                               // attesa
                                                                                                               // per
                                                                                                               // una
                                                                                                               // connessione
                                                                                                               // (10
                                                                                                               // sec)
        hikariConfig.setValidationTimeout(environment.getProperty("db.config.validationTimeout", Long.class)); // Timeout
                                                                                                               // per
                                                                                                               // verificare
                                                                                                               // una
                                                                                                               // connessione
                                                                                                               // (5
                                                                                                               // sec)
        hikariConfig.setLeakDetectionThreshold(environment.getProperty("db.config.leakDetectionThreshold", Long.class)); // Rilevamento
                                                                                                                         // connessioni
                                                                                                                         // non
                                                                                                                         // chiuse
                                                                                                                         // (ogni
                                                                                                                         // mezzora)
        hikariConfig.setConnectionTestQuery(environment.getProperty("db.config.connectionTestQuery")); // Query di test
                                                                                                       // per verificare
                                                                                                       // connessione
        return new HikariDataSource(hikariConfig);
    }

    private LocalContainerEntityManagerFactoryBean createEntityManagerFactory(HikariDataSource dataSource,
            String dialect) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("it.alveo.ricalcoloaree");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        properties.setProperty("hibernate.use_sql_comments", environment.getProperty("hibernate.use_sql_comments"));
        properties.setProperty("hibernate.cache.use_second_level_cache",
                environment.getProperty("hibernate.cache.use_second_level_cache"));
        em.setJpaProperties(properties);
        em.afterPropertiesSet();
        return em;
    }
}
