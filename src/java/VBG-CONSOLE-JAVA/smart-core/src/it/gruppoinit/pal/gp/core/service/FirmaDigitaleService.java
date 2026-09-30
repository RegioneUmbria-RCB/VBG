package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.helper.ReportValidazioneFirmaDigitale;

import javax.activation.DataHandler;


public interface FirmaDigitaleService {
    
    public ReportValidazioneFirmaDigitale validaFile(String fileName, DataHandler fileBinary);
}
