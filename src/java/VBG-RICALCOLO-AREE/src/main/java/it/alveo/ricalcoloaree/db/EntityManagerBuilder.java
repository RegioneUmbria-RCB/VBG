package it.alveo.ricalcoloaree.db;

import java.util.Properties;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.alveo.ricalcoloaree.configurations.EnteConfig;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerBuilder {

    private EntityManagerFactory build(EnteConfig config) {

	var dataSource = createDataSource(config.getJdbcUrl(), config.getUtente(), config.getPwd(), config.getDriver());
	var entityManagerFactoryBean = createEntityManagerFactory(dataSource, config.getDialect());
	return entityManagerFactoryBean.getObject();
    }

    private HikariDataSource createDataSource(String jdbcurll, String utente, String pwd, String drver) {

	HikariConfig hikariConfig = new HikariConfig();
	hikariConfig.setJdbcUrl(jdbcurll);
	hikariConfig.setUsername(utente);
	hikariConfig.setPassword(pwd);
	hikariConfig.setDriverClassName(drver);
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

    private LocalContainerEntityManagerFactoryBean createEntityManagerFactory(HikariDataSource dataSource, String dialect) {

	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	em.setDataSource(dataSource);
	em.setPackagesToScan("it.alveo.ricalcoloaree");
	em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	Properties properties = new Properties();
	properties.setProperty("hibernate.hbm2ddl.auto", "none");
	properties.setProperty("hibernate.dialect", dialect);
	properties.setProperty("hibernate.show_sql", "false");
	properties.setProperty("hibernate.format_sql", "false");
	properties.setProperty("hibernate.use_sql_comments", "false");
	properties.setProperty("hibernate.cache.use_second_level_cache", "false");
	em.setJpaProperties(properties);
	em.afterPropertiesSet();
	return em;
    }
}
