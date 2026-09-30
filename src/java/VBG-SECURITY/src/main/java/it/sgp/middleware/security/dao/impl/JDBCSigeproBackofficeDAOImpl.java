package it.sgp.middleware.security.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.sgp.middleware.security.SecurityConstants;
import it.sgp.middleware.security.dao.ComunisecurityDAO;
import it.sgp.middleware.security.dao.SigeproBackofficeDAO;
import it.sgp.middleware.security.domain.AmbienteEnum;
import it.sgp.middleware.security.domain.Comunisecurity;
import it.sgp.middleware.security.domain.ComunisecurityConnection;
import it.sgp.middleware.security.domain.ContestoEnum;
import it.sgp.middleware.security.domain.InfoUtenteSigepro;
import it.sgp.middleware.security.exceptions.NotImplementedException;
import it.sgp.middleware.security.utils.Utilities;

@Repository
public class JDBCSigeproBackofficeDAOImpl implements SigeproBackofficeDAO {

    private static Logger log = LoggerFactory.getLogger(JDBCSigeproBackofficeDAOImpl.class);
    private ComunisecurityDAO comunisecurityDAO;
    /**
     * select * from responsabili where idcomune=? and (lower(userid)=? or lower(strong_auth_id)=?) and disabilitato=?
     */
    private static String OPERATORI_BY_USERID_QUERY = "select * from responsabili where idcomune=? and (lower(userid)=? or lower(strong_auth_id)=?)  and disabilitato=?";
    /**
     * select * from anagrafe where idcomune=? and (lower(codicefiscale)=? or lower(strong_auth_id)=?) and
     * flag_disabilitato=? and tipoanagrafe='F'
     */
    private static String ANAGRAFE_BY_USERID_QUERY = "select * from anagrafe where idcomune=? and (lower(codicefiscale)=? or lower(strong_auth_id)=?) and flag_disabilitato=? and tipoanagrafe='F'";
    /**
     * select * from anagrafe where idcomune=? and (lower(codicefiscale)=? or lower(strong_auth_id)=?) and
     * flag_disabilitato=? and tipoanagrafe='F'
     */
    private static String ANAGRAFEPG_BY_USERID_QUERY = "select * from anagrafe where idcomune=? and (lower(codicefiscale)=? or lower(strong_auth_id)=?) and flag_disabilitato=? and tipoanagrafe='G'";
    /**
     * select * from amministrazioni where idcomune=? and lower(partitaiva)=?
     */
    private static String AMMINISTRAZIONI_BY_USERID_QUERY = "select * from amministrazioni where idcomune=? and lower(partitaiva)=?";
    private Properties dbDrivers = new Properties();

    @Autowired
    public void setComunisecurityDAO(ComunisecurityDAO comunisecurityDAO) {

	this.comunisecurityDAO = comunisecurityDAO;
    }

    public JDBCSigeproBackofficeDAOImpl() {

	try {
	    dbDrivers.load(JDBCSigeproBackofficeDAOImpl.class.getClassLoader().getResourceAsStream("db.properties"));
	} catch (IOException e) {
	    log.error("Error loading db.properties:", e);
	}
    }

    @Override
    public InfoUtenteSigepro verificaAmministrazione(String alias, String amministrazioneUserId) {

	Comunisecurity comunisecurity = comunisecurityDAO.findById(alias).orElseThrow();
	Connection conn = getSimpleConnection(comunisecurity);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    pstmt = conn.prepareStatement(AMMINISTRAZIONI_BY_USERID_QUERY);
	    pstmt.setString(1, Utilities.resolveIdComune(comunisecurity));
	    pstmt.setString(2, amministrazioneUserId.toLowerCase());
	    if (log.isDebugEnabled()) {
		log.debug("excuting query: {} whith params idcomune: '{}',userid: '{}'",
			new Object[] { AMMINISTRAZIONI_BY_USERID_QUERY, Utilities.resolveIdComune(comunisecurity), amministrazioneUserId });
	    }
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		InfoUtenteSigepro info = new InfoUtenteSigepro();
		info.setCodice(rs.getInt("codiceamministrazione"));
		info.setDescrizione(rs.getString("amministrazione"));
		info.setContesto(ContestoEnum.AMM);
		info.setPassword(rs.getString("password"));
		info.setUserid(amministrazioneUserId);
		info.setIdcomune(Utilities.resolveIdComune(comunisecurity));
		return info;
	    } else {
		return null;
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	} finally {
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
	    if (null != conn) {
		try {
		    conn.close();
		} catch (SQLException e) {
		}
	    }
	}
    }

    @Override
    public InfoUtenteSigepro verificaAnagrafe(String alias, String anagrafeUserId) {

	Comunisecurity comunisecurity = comunisecurityDAO.findById(alias).orElseThrow();
	Connection conn = getSimpleConnection(comunisecurity);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    pstmt = conn.prepareStatement(ANAGRAFE_BY_USERID_QUERY);
	    pstmt.setString(1, Utilities.resolveIdComune(comunisecurity));
	    pstmt.setString(2, anagrafeUserId.toLowerCase());
	    pstmt.setString(3, anagrafeUserId.toLowerCase());
	    pstmt.setInt(4, 0);
	    if (log.isDebugEnabled()) {
		log.debug("excuting query: {} whith params idcomune: '{}',userid: '{}'",
			new Object[] { ANAGRAFE_BY_USERID_QUERY, Utilities.resolveIdComune(comunisecurity), anagrafeUserId });
	    }
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		InfoUtenteSigepro info = new InfoUtenteSigepro();
		info.setCodice(rs.getInt("codiceanagrafe"));
		String descrizioneUtente = rs.getString("nominativo");
		if (StringUtils.isNotBlank(rs.getString("nome"))) {
		    descrizioneUtente = " " + rs.getString("nome");
		}
		info.setDescrizione(descrizioneUtente);
		info.setContesto(ContestoEnum.UTE);
		info.setPassword(rs.getString("password"));
		info.setUserid(anagrafeUserId);
		info.setIdcomune(Utilities.resolveIdComune(comunisecurity));
		if (rs.getBoolean("flag_identificato")) {
		    info.setLivelloIdentificazione(SecurityConstants.LIVELLO_AUTENTICAZIONE_IDENTIFICATO);
		} else {
		    info.setLivelloIdentificazione(SecurityConstants.LIVELLO_AUTENTICAZIONE_NON_IDENTIFICATO);
		}
		return info;
	    } else {
		return null;
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	} finally {
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
	    if (null != conn) {
		try {
		    conn.close();
		} catch (SQLException e) {
		}
	    }
	}
    }

    @Override
    public InfoUtenteSigepro verificaOperatore(String alias, String operatoreUserId) {

	Comunisecurity comunisecurity = comunisecurityDAO.findById(alias).orElseThrow();
	Connection conn = getSimpleConnection(comunisecurity);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    pstmt = conn.prepareStatement(OPERATORI_BY_USERID_QUERY);
	    pstmt.setString(1, Utilities.resolveIdComune(comunisecurity));
	    pstmt.setString(2, operatoreUserId.toLowerCase());
	    pstmt.setString(3, operatoreUserId.toLowerCase());
	    pstmt.setInt(4, 0);
	    if (log.isDebugEnabled()) {
		log.debug("excuting query: {} whith params idcomune:{}, userid:{}, strong_auth_id:{}",
			new Object[] { OPERATORI_BY_USERID_QUERY, Utilities.resolveIdComune(comunisecurity), operatoreUserId, operatoreUserId });
	    }
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		InfoUtenteSigepro info = new InfoUtenteSigepro();
		info.setCodice(rs.getInt("codiceresponsabile"));
		info.setDescrizione(rs.getString("responsabile"));
		info.setContesto(ContestoEnum.OPE);
		info.setPassword(rs.getString("password"));
		info.setUserid(rs.getString("userid"));
		info.setIdcomune(Utilities.resolveIdComune(comunisecurity));
		return info;
	    } else {
		return null;
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	} finally {
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
	    if (null != conn) {
		try {
		    conn.close();
		} catch (SQLException e) {
		}
	    }
	}
    }

    /** Uses DriverManager. */
    private Connection getSimpleConnection(Comunisecurity comunisecurity) {

	String dbConnString = getJavaConnectionString(comunisecurity);
	String userName = comunisecurity.getDbuser();
	String password = comunisecurity.getDbpwd();
	String driverClassName = getDriverForCnString(dbConnString);
	Connection result = null;
	try {
	    Class.forName(driverClassName).newInstance();
	} catch (Exception ex) {
	    log.error("Check classpath. Cannot load db driver: " + driverClassName, ex);
	    throw new RuntimeException("Check classpath. Cannot load db driver: " + driverClassName, ex);
	}
	try {
	    result = DriverManager.getConnection(dbConnString, userName, password);
	} catch (SQLException e) {
	    log.error("Driver loaded, but cannot connect to db: " + dbConnString, e);
	    throw new RuntimeException("Driver loaded, but cannot connect to db: " + dbConnString, e);
	}
	return result;
    }

    private String getDriverForCnString(String dbConnString) {

	if (dbConnString.indexOf("oracle") > 0) {
	    log.debug("load oracle driver: " + dbDrivers.getProperty("oracle"));
	    return dbDrivers.getProperty("oracle");
	} else if (dbConnString.indexOf("postgresql") > 0) {
	    log.debug("load postgresql driver: " + dbDrivers.getProperty("postgresql"));
	    return dbDrivers.getProperty("postgresql");
	} else if (dbConnString.indexOf("mysql") > 0) {
	    log.debug("load mysql driver: " + dbDrivers.getProperty("mysql"));
	    return dbDrivers.getProperty("mysql");
	} else if (dbConnString.indexOf("sqlserver") > 0) {
	    log.debug("load sqlserver driver: " + dbDrivers.getProperty("sqlserver"));
	    return dbDrivers.getProperty("sqlserver");
	}
	return null;
    }

    private String getJavaConnectionString(Comunisecurity comunisecurity) {

	Set<ComunisecurityConnection> conns = comunisecurity.getComunisecurityConnections();
	for (ComunisecurityConnection comunisecurityConnection : conns) {
	    if (comunisecurityConnection.getId().getAmbiente().equalsIgnoreCase(AmbienteEnum.JAVA.toString())) {
		return comunisecurityConnection.getConnectionstring();
	    }
	}
	throw new NotImplementedException("Non è stata trovata la connecrtion string JAVA per l'alias [" + comunisecurity.getId() + "]");
    }

    @Override
    public InfoUtenteSigepro verificaAnagrafePg(String alias, String anagrafeUserId) {

	Comunisecurity comunisecurity = comunisecurityDAO.findById(alias).orElseThrow();
	Connection conn = getSimpleConnection(comunisecurity);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    pstmt = conn.prepareStatement(ANAGRAFEPG_BY_USERID_QUERY);
	    pstmt.setString(1, Utilities.resolveIdComune(comunisecurity));
	    pstmt.setString(2, anagrafeUserId.toLowerCase());
	    pstmt.setString(3, anagrafeUserId.toLowerCase());
	    pstmt.setInt(4, 0);
	    if (log.isDebugEnabled()) {
		log.debug("excuting query: {} whith params idcomune: '{}',userid: '{}'",
			new Object[] { ANAGRAFEPG_BY_USERID_QUERY, Utilities.resolveIdComune(comunisecurity), anagrafeUserId });
	    }
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		InfoUtenteSigepro info = new InfoUtenteSigepro();
		info.setCodice(rs.getInt("codiceanagrafe"));
		String descrizioneUtente = rs.getString("nominativo");
		if (StringUtils.isNotBlank(rs.getString("nome"))) {
		    descrizioneUtente = " " + rs.getString("nome");
		}
		info.setDescrizione(descrizioneUtente);
		info.setContesto(ContestoEnum.UTEG);
		info.setPassword(rs.getString("password"));
		info.setUserid(anagrafeUserId);
		info.setIdcomune(Utilities.resolveIdComune(comunisecurity));
		return info;
	    } else {
		return null;
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	} finally {
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
	    if (null != conn) {
		try {
		    conn.close();
		} catch (SQLException e) {
		}
	    }
	}
    }
}
