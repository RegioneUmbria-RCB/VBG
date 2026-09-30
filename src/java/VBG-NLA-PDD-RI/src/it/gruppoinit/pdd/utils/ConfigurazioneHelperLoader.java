package it.gruppoinit.pdd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurazioneHelperLoader {

    private static Logger log = LoggerFactory.getLogger(ConfigurazioneHelperLoader.class);
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private ConfigurazioneHelperLoader() {

	super();
    }

    public ConfigurazioneHelperLoader(Connection conn) {

	this();
	this.conn = conn;
    }

    public ConfigurazioneHelper loadConfigurazione(String idcomunealias, String idcomune, String dbOwner, String software, String codiceComune,
	    boolean effettuaValidazione) {

	ConfigurazioneHelper helper = new ConfigurazioneHelper();
	try {
	    if (log.isDebugEnabled()) {
		log.debug("loadConfigurazione# sql={}", sqlDenominazione.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sqlDenominazione.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setString(2, "TT");
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		if (log.isDebugEnabled()) {
		    log.debug("loadConfigurazione# cerco denominazione in configurazione per idcomune={}, software=TT", idcomune);
		}
		if (StringUtils.isNotBlank(rs.getString("denominazione"))) {
		    helper.setDescrizioneComune(rs.getString("denominazione"));
		}
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	    pstmt = conn.prepareStatement(sqlDenominazione.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setString(2, software);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		if (log.isDebugEnabled()) {
		    log.debug("loadConfigurazione# cerco denominazione in configurazione per idcomune={}, software={}", software);
		}
		if (StringUtils.isNotBlank(rs.getString("denominazione"))) {
		    helper.setDescrizioneComune(rs.getString("denominazione"));
		}
	    }
	    pstmt = conn.prepareStatement(sqlCodicemministrazioneIpa.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setString(2, codiceComune);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		if (log.isDebugEnabled()) {
		    log.debug("loadConfigurazione# cerco codiceamministrazione_ipa in comuniassociati idcomune={}, codiceComune={}", idcomune,
			    codiceComune);
		}
		if (StringUtils.isNotBlank(rs.getString("codiceamministrazione_ipa"))) {
		    helper.setCodiceAmministrazioneIpa(rs.getString("codiceamministrazione_ipa"));
		}
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	    pstmt = conn.prepareStatement(sqlCodiceaccreditamentoAoo.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setString(2, codiceComune);
	    pstmt.setString(3, software);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		if (log.isDebugEnabled()) {
		    log.debug(
			    "loadConfigurazione# cerco codice_accreditamento, codice_aoo in comuniassociatisoftware per idcomune={}, codiceComune={}, software={}",
			    new Object[] { idcomune, codiceComune, software });
		}
		if (StringUtils.isNotBlank(rs.getString("codice_accreditamento"))) {
		    helper.setCodiceAccreditamento(rs.getString("codice_accreditamento"));
		}
		if (StringUtils.isNotBlank(rs.getString("codice_aoo"))) {
		    helper.setCodiceAoo(rs.getString("codice_aoo"));
		}
	    }
	    validateConfigurazione(helper, idcomune, software, codiceComune, effettuaValidazione);
	} catch (SQLException e) {
	    if (e.getCause() == null) {
		Utilities.logAndThrowException("Errore durante la query di recupero dati della configurazione [" + idcomune + "," + software +
					       "] a causa di " + e.getMessage(),
			e, getClass());
	    } else {
		Utilities.logAndThrowException("Errore durante la query di recupero dati della configurazione  [" + idcomune + "," + software +
					       "] a causa di " + e.getCause().getMessage(),
			new RuntimeException(e.getCause()), getClass());
	    }
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return helper;
    }

    public void validateConfigurazione(ConfigurazioneHelper helper, String idcomune, String software, String codiceComune,
	    boolean effettuaValidazione) {

	if (helper == null) {
	    Utilities.logAndThrowException("Configurazione non trovata per l'idcomune [" + idcomune + "] e software [" + software + "]", getClass());
	}
	if (!effettuaValidazione) {
	    return;
	}
	if (StringUtils.isBlank(helper.getCodiceAccreditamento())) {
	    Utilities.logAndThrowException(
		    "Non e' stato configurato il campo CODICE_ACCREDITAMENTO nella tabella COMUNIASSOCIATISOFTWARE rif(idcomune: " + idcomune +
					   ", codiceComune=" + codiceComune + ", software=" + software +
					   ", valore: COMUNIASSOCIATISOFTWARE.CODICE_ACCREDITAMENTO)",
		    getClass());
	}
	if (StringUtils.isBlank(helper.getCodiceAmministrazioneIpa())) {
	    Utilities
		    .logAndThrowException("Non e' stato configurato il campo CODICEAMMINISTRAZIONE_IPA nella tabella COMUNIASSOCIATI rif(idcomune: " +
					  idcomune + ", software='TT', valore: CONFIGURAZIONE.CODICEAMMINISTRAZIONE_IPA)",
			    getClass());
	}
	if (StringUtils.isBlank(helper.getCodiceAoo())) {
	    Utilities.logAndThrowException("Non e' stato configurato il campo CODICE_AOO  nella tabella COMUNIASSOCIATISOFTWARE rif(idcomune: " +
					   idcomune + ", codiceComune=" + codiceComune + ", software=" + software +
					   ", valore: COMUNIASSOCIATISOFTWARE.CODICE_AOO )",
		    getClass());
	}
    }

    String sqlDenominazione = "select denominazione from DB_SCHEMA.configurazione where idcomune=? and software =?";
    String sqlCodicemministrazioneIpa = "select codiceamministrazione_ipa from DB_SCHEMA.comuniassociati where idcomune=? and codicecomune=?";
    String sqlCodiceaccreditamentoAoo = "select codice_accreditamento,codice_aoo from DB_SCHEMA.comuniassociatisoftware where idcomune=? and codicecomune=? and software=?";
}
