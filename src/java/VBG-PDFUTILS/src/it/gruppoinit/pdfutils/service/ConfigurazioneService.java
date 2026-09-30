package it.gruppoinit.pdfutils.service;

import it.gruppoinit.pdfutils.domain.PDFMappature;

import java.sql.SQLException;
import java.util.Map;

public interface ConfigurazioneService {

    public Map<String, PDFMappature> loadConfigurazione(String token) throws SQLException;

    public String getURLWSFirmaDigitale();
    
    public String getURLRestFirmaDigitale();
    
    boolean checkIsNewDSS();

    public void reloadConfigurazione();
		
}
