package it.gruppoinit.pdfutils.service.impl;

import it.gruppoinit.pdfutils.Utilities;
import it.gruppoinit.pdfutils.domain.PDFMappature;
import it.gruppoinit.pdfutils.service.ConfigurazioneService;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurazioneServiceImpl implements ConfigurazioneService {

    private static final Logger log = LoggerFactory.getLogger(ConfigurazioneServiceImpl.class);
    private SigeproSecurityWebServiceClient securityWebServiceClient;
    private Map<String, PDFMappature> mappature = new HashMap<String, PDFMappature>();

    @Override
    public Map<String, PDFMappature> loadConfigurazione(String token) throws SQLException {

	if (log.isDebugEnabled()) {
	    log.debug("loadConfigurazione# entro nel metodo");
	}
	if (this.mappature.isEmpty()) {
	    if (log.isDebugEnabled()) {
		log.debug("loadConfigurazione# le mappature non sono mai state lette le ricavo dalla tabella. leggo le token info dal token {}",
			token);
	    }
	    Connection c = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
		// dal token ricava l'alias
		TokenInfoType ti = securityWebServiceClient.getTokenInfo(token);
		// apre la connessione 
		// legge la tabella 
		if (log.isDebugEnabled()) {
		    log.debug("loadConfigurazione# apro la connessione");
		}
		c = securityWebServiceClient.getConnection(ti.getAlias());
		pstmt = c.prepareStatement(PDFMappingServiceImpl.SELECT_FROM_PDF_MAPPATURE);
		rs = pstmt.executeQuery();
		if (log.isDebugEnabled()) {
		    log.debug("loadConfigurazione# leggo la tabella");
		}
		// popola le mappature
		while (rs.next()) {
		    PDFMappature m = new PDFMappature();
		    m.setLabel(rs.getString("label"));
		    m.setAmbito(rs.getString("ambito"));
		    m.setXpath(rs.getString("xpath"));
		    m.setDecodFormatoInput(rs.getString("decod_formato_input"));
		    m.setDecodFormatoOutput(rs.getString("decod_formato_output"));
		    m.setDecodTipo(rs.getString("decod_tipo"));
		    this.mappature.put(rs.getString("label"), m);
		}
	    } finally {
		Utilities.gracefullyReleaseResources(c, pstmt, rs);
	    }
	}
	return this.mappature;
    }

    @Override
    public void reloadConfigurazione() {

	this.mappature = new HashMap<String, PDFMappature>();
	this.wsURL = null;
    }

    public void setSecurityWebServiceClient(SigeproSecurityWebServiceClient securityWebServiceClient) {

	this.securityWebServiceClient = securityWebServiceClient;
    }

    private String wsURL = null;
    
    private String restURL = null;
    
    private Boolean isNewDss = null;

    @Override
    public String getURLWSFirmaDigitale() {

	if (StringUtils.isBlank(this.wsURL)) {
	    Map<String, String> params = securityWebServiceClient.getParams("WSHOSTURL_FIRMADIGITALE");
	    wsURL = params.get("WSHOSTURL_FIRMADIGITALE");
	}
	return wsURL;
    }
    
    @Override
    public String getURLRestFirmaDigitale() {

	if (StringUtils.isBlank(this.restURL)) {
	    Map<String, String> params = securityWebServiceClient.getParams("WSHOSTURL_FIRMADIGITALE_REST");
	    restURL = params.get("WSHOSTURL_FIRMADIGITALE_REST");
	}
	return restURL;
    }
    
    
    /*
     * Aiutandoci con questo metodo, non chiameremo N volte il servizio 
     * per il parametro WSHOSTURL_FIRMADIGITALE_REST se questo non attivo
     */
    @Override
    public boolean checkIsNewDSS() {
    	
    	if( isNewDss != null ) {
    		return isNewDss.booleanValue();
    	}
    	
    	return !StringUtils.isBlank(getURLRestFirmaDigitale());
    	
    }
}
