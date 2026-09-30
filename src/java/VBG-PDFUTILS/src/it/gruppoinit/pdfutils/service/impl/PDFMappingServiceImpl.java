package it.gruppoinit.pdfutils.service.impl;

import it.gruppoinit.pdfutils.Utilities;
import it.gruppoinit.pdfutils.domain.PDFMappature;
import it.gruppoinit.pdfutils.service.ConfigurazioneService;
import it.gruppoinit.pdfutils.service.PDFMappingService;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PDFMappingServiceImpl implements PDFMappingService {

    public static final String SELECT_FROM_PDF_MAPPATURE = "select label,ambito,xpath,decod_formato_input,decod_formato_output,decod_tipo from pdf_mappature order by ambito, label";
    public static final String UPDATE_PDF_MAPPATURE = "update pdf_mappature set ambito=?,xpath=?,decod_formato_input=?,decod_formato_output=?,decod_tipo=? where label=?";
    public static final String DELETE_PDF_MAPPATURE = "delete from pdf_mappature where label=?";
    public static final String INSERT_INTO_PDF_MAPPATURE = "insert into pdf_mappature (label,ambito,xpath,decod_formato_input,decod_formato_output,decod_tipo) values (?,?,?,?,?,?)";
    private SigeproSecurityWebServiceClient securityWebServiceClient;

    public void setSecurityWebServiceClient(SigeproSecurityWebServiceClient securityWebServiceClient) {

	this.securityWebServiceClient = securityWebServiceClient;
    }

    private ConfigurazioneService configurazioneService;

    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Override
    public List<PDFMappature> findAll(String alias, Integer firstResult, Integer maxResult) {

	Connection c = getConnection(alias);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	List<PDFMappature> mappature = new ArrayList<PDFMappature>();
	try {
	    c = getConnection(alias);
	    pstmt = c.prepareStatement(SELECT_FROM_PDF_MAPPATURE);
	    rs = pstmt.executeQuery();
	    // popola le mappature
	    while (rs.next()) {
		PDFMappature m = new PDFMappature();
		m.setLabel(rs.getString("label"));
		m.setAmbito(rs.getString("ambito"));
		m.setXpath(rs.getString("xpath"));
		m.setDecodFormatoInput(rs.getString("decod_formato_input"));
		m.setDecodFormatoOutput(rs.getString("decod_formato_output"));
		m.setDecodTipo(rs.getString("decod_tipo"));
		mappature.add(m);
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    Utilities.gracefullyReleaseResources(c, pstmt, rs);
	}
	return mappature;
    }

    @Override
    public void insert(String alias, PDFMappature newconfigurazione) {

	Connection c = getConnection(alias);
	PreparedStatement pstmt = null;
	try {
	    c = getConnection(alias);
	    pstmt = c.prepareStatement(INSERT_INTO_PDF_MAPPATURE);
	    pstmt.setString(1, newconfigurazione.getLabel());
	    pstmt.setString(2, newconfigurazione.getAmbito());
	    pstmt.setString(3, newconfigurazione.getXpath());
	    pstmt.setString(4, newconfigurazione.getDecodFormatoInput());
	    pstmt.setString(5, newconfigurazione.getDecodFormatoOutput());
	    pstmt.setString(6, newconfigurazione.getDecodTipo());
	    pstmt.executeUpdate();
	    configurazioneService.reloadConfigurazione();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    Utilities.gracefullyReleaseResources(c, pstmt, null);
	}
    }

    @Override
    public void update(String alias, PDFMappature configurazione) {

	Connection c = getConnection(alias);
	PreparedStatement pstmt = null;
	try {
	    c = getConnection(alias);
	    pstmt = c.prepareStatement(UPDATE_PDF_MAPPATURE);	    
	    pstmt.setString(1, configurazione.getAmbito());
	    pstmt.setString(2, configurazione.getXpath());
	    pstmt.setString(3, configurazione.getDecodFormatoInput());
	    pstmt.setString(4, configurazione.getDecodFormatoOutput());
	    pstmt.setString(5, configurazione.getDecodTipo());
	    pstmt.setString(6, configurazione.getLabel());
	    pstmt.executeUpdate();
	    configurazioneService.reloadConfigurazione();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    Utilities.gracefullyReleaseResources(c, pstmt, null);
	}
    }

    @Override
    public void delete(String alias, String label) {

	Connection c = getConnection(alias);
	PreparedStatement pstmt = null;
	try {
	    c = getConnection(alias);
	    pstmt = c.prepareStatement(DELETE_PDF_MAPPATURE);
	    pstmt.setString(1, label);
	    pstmt.executeUpdate();
	    configurazioneService.reloadConfigurazione();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    Utilities.gracefullyReleaseResources(c, pstmt, null);
	}
    }

    private Connection getConnection(String alias) {

	Connection c = null;
	// legge la tabella 
	c = securityWebServiceClient.getConnection(alias);
	return c;
    }
}
