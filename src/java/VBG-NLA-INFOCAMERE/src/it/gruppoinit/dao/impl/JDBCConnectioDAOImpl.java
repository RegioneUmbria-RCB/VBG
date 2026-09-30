package it.gruppoinit.dao.impl;

import it.gruppoinit.dao.JDBCConnectioDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

// import com.mysql.jdbc.PreparedStatement;
@Repository
public class JDBCConnectioDAOImpl implements JDBCConnectioDAO {

    private static Logger log = LoggerFactory.getLogger(JDBCConnectioDAOImpl.class);
    private Properties propertiesDB = null;

    @Override
    public Connection getSimpleConnectionBackend() {

	log.debug("getSimpleConnection# Recupero le informazioni di connessione");
	loadDBProperties();
	String JDBC_DRIVER = propertiesDB.getProperty("db.backend.jdbc_driver");
	String DB_URL = propertiesDB.getProperty("db.backend.db_url");
	String USER = propertiesDB.getProperty("db.backend.user");
	String PASS = propertiesDB.getProperty("db.backend.user.password");
	Connection result = null;
	try {
	    log.debug("getSimpleConnectionBackend# Istance driver");
	    Class.forName(JDBC_DRIVER).newInstance();
	} catch (Exception ex) {
	    log.error("Check classpath. Cannot load db driver: {}", JDBC_DRIVER);
	    throw new RuntimeException("Check classpath. Cannot load db driver: " + JDBC_DRIVER, ex);
	}
	try {
	    log.debug("getSimpleConnectionBackend# Connection db.....");
	    result = DriverManager.getConnection(DB_URL, USER, PASS);
	} catch (SQLException e) {
	    log.error("Connection KO!");
	    log.error("Driver loaded, but cannot connect to db: {}", DB_URL);
	    throw new RuntimeException("Driver loaded, but cannot connect to db: " + DB_URL, e);
	}
	log.debug("getSimpleConnectionBackend# Connection OK!");
	return result;
    }

    @Override
    public void gracefulReleaseConnections(ResultSet rs, PreparedStatement pstmt, Connection conn) {

	try {
	    if (null != rs) {
		DbUtils.close(rs);
		//rs.close();
	    }
	} catch (Exception e) {
	    log.error("Could not close  ResultSet", e);
	}
	try {
	    if (null != pstmt) {
		DbUtils.close(pstmt);
		//pstmt.close();
	    }
	} catch (Exception e) {
	    log.error("Could not close  PreparedStatement", e);
	}
	if (null != conn) {
	    try {
		DbUtils.close(conn);
		//conn.close();
	    } catch (SQLException e) {
		log.error("Could not close  Connection", e);
	    }
	}
    }

    @Override
    public void gracefulReleaseConnections(Connection conn) {

	if (null != conn) {
	    try {
		DbUtils.close(conn);
	    } catch (SQLException e) {
		log.error("Could not close  Connection", e);
	    }
	}
    }

    private Properties loadDBProperties() {

	if (propertiesDB == null) {
	    try {
		log.debug("loadDBProperties# Recupero le informazioni di connessione dal file deploy.properties");
		propertiesDB = new Properties();
		propertiesDB.load(JDBCConnectioDAOImpl.class.getClassLoader().getResourceAsStream("deploy.properties"));
	    } catch (IOException e) {
		log.error("Error loading db.properties: {}", e.getMessage());
	    }
	} else {
	    log.debug("loadDBProperties# Informazioni di connessione già recuperate");
	}
	return propertiesDB;
    }
}
