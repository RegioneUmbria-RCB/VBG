package it.gruppoinit.pal.gp.core.scheduler.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcConnection {

    private Properties dbProps = null;
    private static final Logger log = LoggerFactory.getLogger(JdbcConnection.class);

    public JdbcConnection(Properties dbProps) {

	this.dbProps = dbProps;
    }

    protected Connection getConnection(String dbConnString, String userName, String password) throws Exception {

	log.debug("getConnection: dbConnString={}, userName={}, password={}", new Object[] { dbConnString, userName, password });
	try {
	    String driverClassName = getDriverClassName(dbConnString);
	    Class.forName(driverClassName).newInstance();
	    return DriverManager.getConnection(dbConnString, userName, password);
	} catch (Exception e) {
	    log.error("getConnection: dbConnString={}, userName={}, password={}\n{}", new Object[] { dbConnString, userName, password, e });
	    throw e;
	}
    }

    private String getDriverClassName(String dbConnString) throws Exception {

	try {
	    log.debug("getDriverClassName: dbConnString={}", dbConnString);
	    String dbDriverClassName = "";
	    if (dbConnString.indexOf("oracle") > 0) {
		dbDriverClassName = dbProps.getProperty("db.driver.Oracle");
	    } else if (dbConnString.indexOf("postgresql") > 0) {
		dbDriverClassName = dbProps.getProperty("db.driver.PostgreSQL");
	    } else if (dbConnString.indexOf("mysql") > 0) {
		dbDriverClassName = dbProps.getProperty("db.driver.MySql");
	    } else if (dbConnString.indexOf("sqlserver") > 0) {
		dbDriverClassName = dbProps.getProperty("db.driver.SQLServer");
	    }
	    if (StringUtils.isBlank(dbDriverClassName)) {
		throw new Exception("getDriverClassName: driver not found in db.properties for dbConnString=" + dbConnString);
	    }
	    log.debug("getDriverClassName: dbConnString={} return {}", dbConnString, dbDriverClassName);
	    return dbDriverClassName;
	} catch (Exception e) {
	    log.error("getDriverClassName: dbConnString={}", dbConnString, e);
	    throw e;
	}
    }

    protected void closeConnection(Connection conn, PreparedStatement pstmt, ResultSet rs) {

	if (null != conn) {
	    try {
		conn.close();
	    } catch (Exception e) {
	    }
	}
	try {
	    if (null != rs) {
		rs.close();
	    }
	} catch (Exception e) {
	}
	try {
	    if (null != pstmt) {
		pstmt.close();
	    }
	} catch (Exception e) {
	}
    }
}
