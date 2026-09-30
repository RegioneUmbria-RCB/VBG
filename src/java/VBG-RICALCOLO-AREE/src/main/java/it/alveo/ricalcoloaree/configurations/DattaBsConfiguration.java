package it.alveo.ricalcoloaree.configurations;

import java.util.Properties;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

public class DattaBsConfiguration {

    private HikariDataSource dataSource;
    private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
    EntityManagerFactory entityManagerFactory;
    JpaTransactionManager transactionManager;

    public HikariDataSource getDataSource() {

	return dataSource;
    }

    public void setDataSource(HikariDataSource dataSource) {

	this.dataSource = dataSource;
    }

    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {

	return entityManagerFactoryBean;
    }

    public void setEntityManagerFactoryBean(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {

	this.entityManagerFactoryBean = entityManagerFactoryBean;
    }

    public EntityManagerFactory getEntityManagerFactory() {

	return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {

	this.entityManagerFactory = entityManagerFactory;
    }

    public JpaTransactionManager getTransactionManager() {

	return transactionManager;
    }

    public void setTransactionManager(JpaTransactionManager transactionManager) {

	this.transactionManager = transactionManager;
    }

    public void createDattaBsConfiguration(String jdbcurll, String utente, String pwd, String drver, String dlect) {

	dataSource = createDataSource(jdbcurll, utente, pwd, drver);
	entityManagerFactoryBean = createEntityManagerFactory(dataSource, dlect);
	entityManagerFactory = entityManagerFactoryBean.getObject();
	transactionManager = new JpaTransactionManager(entityManagerFactory);
    }

    private HikariDataSource createDataSource(String jdbcurll, String utente, String pwd, String drver) {
	//HikariDataSource dataSource = new HikariDataSource();

	// dataSource.setJdbcUrl("jdbc:mysql://localhost:3307/ibcback?serverTimezone=UTC");
	// dataSource.setUsername("ibcback");
	// dataSource.setPassword("ibcback4init");
	HikariConfig hikariConfig = new HikariConfig();
	hikariConfig.setJdbcUrl(jdbcurll);
	hikariConfig.setUsername(utente);
	hikariConfig.setPassword(pwd);
	hikariConfig.setDriverClassName(drver);
	// Impostazioni di Connection Pool (equivalenti a quelle di C3P0)
	hikariConfig.setMinimumIdle(20); // Minimo di connessioni nel pool
	hikariConfig.setMaximumPoolSize(100); // Massimo di connessioni nel pool
	hikariConfig.setIdleTimeout(300000); // Tempo massimo di inattività (5 min)
	hikariConfig.setMaxLifetime(3600000); // Connessioni chiuse dopo 1 ora
	hikariConfig.setConnectionTimeout(10000); // Timeout di attesa per una connessione (10 sec)
	hikariConfig.setValidationTimeout(5000); // Timeout per verificare una connessione (5 sec)
	hikariConfig.setLeakDetectionThreshold(1800000); // Rilevamento connessioni non chiuse (ogni mezzora)
	hikariConfig.setConnectionTestQuery("SELECT 1 FROM DUAL"); // Query di test per verificare connessione
	return new HikariDataSource(hikariConfig);
    }

    private LocalContainerEntityManagerFactoryBean createEntityManagerFactory(HikariDataSource dataSource, String dlect) {

	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	em.setDataSource(dataSource);
	em.setPackagesToScan("it.alveo.ricalcoloaree");
	em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	Properties properties = new Properties();
	properties.setProperty("hibernate.hbm2ddl.auto", "none");
	properties.setProperty("hibernate.dialect", dlect);
	properties.setProperty("hibernate.show_sql", "false");
	properties.setProperty("hibernate.format_sql", "false");
	properties.setProperty("hibernate.use_sql_comments", "false");
	properties.setProperty("hibernate.cache.use_second_level_cache", "false");
	em.setJpaProperties(properties);
	em.afterPropertiesSet();
	return em;
    }
}
