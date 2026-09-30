package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.hibernate3.support.IdTransferringMergeEventListener;

/**
 * classe per la gestione della sessionFactory
 * 
 * @author fabrizioc
 * 
 */
public class DataSourceContainer {

    private static final Logger log = LoggerFactory.getLogger(DataSourceContainer.class);
    private ExternalDBResolver externalDBResolver;
    private Map<String, SessionFactoryWrapper> factoryWrappers;
    private String[] packagesToScan;
    private LobHandler lobHandler;

    public void setExternalDBResolver(ExternalDBResolver externalDBResolver) {

	this.externalDBResolver = externalDBResolver;
    }

    public void setPackageToScan(String[] packagesToScan) {

	this.packagesToScan = packagesToScan;
    }

    public void setLobHandler(LobHandler lobHandler) {

	this.lobHandler = lobHandler;
    }

    public void setFactoryWrappers(Map<String, SessionFactoryWrapper> factoryWrappers) {

	this.factoryWrappers = factoryWrappers;
    }

    /**
     * questo metodo recupera il wrapper alla SessionFactory associata alla chiave passata come argomento.
     * 
     * @param idcomunealias
     * @return
     */
    public SessionFactoryWrapper getSessionFactoryWrapper(String hibernateSFKey, String idcomunealias) {

	if (!factoryWrappers.containsKey(hibernateSFKey)) {
	    return getSessionFactoryWrapperInternal(hibernateSFKey, idcomunealias);
	}
	if (log.isDebugEnabled()) {
	    log.debug("getSessionFactoryWrapper: key={}, idcomunealias={}", hibernateSFKey, idcomunealias);
	}
	return factoryWrappers.get(hibernateSFKey);
    }

    synchronized SessionFactoryWrapper getSessionFactoryWrapperInternal(String hibernateSFKey, String idcomunealias) {

	if (!factoryWrappers.containsKey(hibernateSFKey)) {
	    if (log.isDebugEnabled()) {
		log.debug("newSessionFactoryWrapper: key={}, idcomunealias={}", hibernateSFKey, idcomunealias);
	    }
	    SessionFactoryWrapper sfw = newSessionFactoryWrapper(idcomunealias);
	    factoryWrappers.put(hibernateSFKey, sfw);
	}
	return factoryWrappers.get(hibernateSFKey);
    }

    /**
     * crea una nuova sessionFactory per l'idcomunealias passato come parametro
     * 
     * @param idcomunealias
     * @return
     */
    private SessionFactoryWrapper newSessionFactoryWrapper(String idcomunealias) {

	// recupero le properties per la configurazione della sessionFactory
	// per questo devo utilizzare l'alias dell'idcomune
	Properties props = externalDBResolver.getConnectionProperties(idcomunealias);
	AnnotationSessionFactoryBean asfb = new AnnotationSessionFactoryBean();
	asfb.setPackagesToScan(packagesToScan);
	asfb.setConfigurationClass(AnnotationConfiguration.class);
	asfb.setHibernateProperties(props);
	String driver = props.getProperty("hibernate.connection.driver_class");
	if (driver.toLowerCase().contains("oracle")) {
	    asfb.setLobHandler(lobHandler);
	} else {
	    asfb.setLobHandler(new DefaultLobHandler());
	}
	Map<String, Object> eventListeners = new HashMap<String, Object>();
	eventListeners.put("merge", new IdTransferringMergeEventListener());
	asfb.setEventListeners(eventListeners);
	SessionFactoryWrapper sfw = null;
	try {
	    asfb.afterPropertiesSet();
	    sfw = new SessionFactoryWrapper();
	    sfw.setSessionFactory((SessionFactory) asfb.getObject());
	    sfw.setHibernateConfig(asfb.getConfiguration());
	} catch (Exception e) {
	    log.error("ERRORE DURANTE LA CREAZIONE DELLA SESSION FACTORY.", e);
	    throw new RuntimeException("ERRORE DURANTE LA CREAZIONE DELLA SESSION FACTORY. " + e.getMessage(), e);
	}
	return sfw;
    }
}
