package it.gruppoinit.pal.gp.core.dao.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * wrapper per l'oggetto sessionFactory
 * 
 * @author fabrizioc
 * 
 */
public class SessionFactoryWrapper {

    private SessionFactory sessionFactory;
    private Configuration hibernateConfig;

    public SessionFactory getSessionFactory() {

	return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {

	this.sessionFactory = sessionFactory;
    }

    public Configuration getHibernateConfig() {

	return hibernateConfig;
    }

    public void setHibernateConfig(Configuration hibernateConfig) {

	this.hibernateConfig = hibernateConfig;
    }
}
