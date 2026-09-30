package it.alveo.ricalcoloaree.configurations;

import org.springframework.core.env.Environment;

import it.alveo.ricalcoloaree.sigeprosecurity.GetDbConnectionInfoResponse;
import jakarta.persistence.EntityManagerFactory;

public class EnteConfig {

    private String idcomune;
    private String jdbcUrl;
    private String utente;
    private String pwd;
    private String driver;
    private String dialect;
    private EntityManagerFactory entityManagerFactory;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getJdbcUrl() {

	return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {

	this.jdbcUrl = jdbcUrl;
    }

    public String getUtente() {

	return utente;
    }

    public void setUtente(String utente) {

	this.utente = utente;
    }

    public String getPwd() {

	return pwd;
    }

    public void setPwd(String pwd) {

	this.pwd = pwd;
    }

    public String getDriver() {

	return driver;
    }

    public void setDriver(String driver) {

	this.driver = driver;
    }

    public String getDialect() {

	return dialect;
    }

    public void setDialect(String dialect) {

	this.dialect = dialect;
    }

    public EntityManagerFactory getEntityManagerFactory() {

	return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {

	this.entityManagerFactory = entityManagerFactory;
    }

    public static EnteConfig fromGetDbConnectionInfoResponse(Environment environment, GetDbConnectionInfoResponse response) {

	var cfg = new EnteConfig();
	cfg.setIdcomune(response.getIdComune());
	cfg.setJdbcUrl(response.getConnectionString());
	cfg.setUtente(response.getDbUser());
	cfg.setPwd(response.getDbPassword());
	cfg.setDriver(environment.getProperty("db.driver." + response.getProvider()));
	cfg.setDialect(environment.getProperty("hibernate.dialect." + response.getProvider()));
	return cfg;
    }
}
