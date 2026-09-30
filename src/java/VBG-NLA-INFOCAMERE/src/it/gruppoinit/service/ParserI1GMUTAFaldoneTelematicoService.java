package it.gruppoinit.service;

import java.util.List;

import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;

public interface ParserI1GMUTAFaldoneTelematicoService {

    public DettaglioPraticaType getRipielogoPraticaFaldoneTelematico(List<DocumentiType> documenti, boolean popolaProcedimenti) throws Exception;

    public DettaglioPraticaType getRiepilogoPraticaSUAP(List<DocumentiType> documenti, boolean popolaProcedimenti) throws Exception;

    public DettaglioPraticaType getDettaglioPraticaType(List<DocumentiType> documenti, boolean popolaProcedimenti) throws Exception;
    
    public DettaglioPraticaType getRiepilogoPraticaSUAPEml(List<DocumentiType> documenti, boolean popolaProcedimenti) throws Exception;
    
    public List<DocumentiType> getDocumentiFromEml(DocumentiType eml) throws Exception;
}
