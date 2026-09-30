package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.helper.OperazioniPentaho;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

public interface PentahoService extends BaseService<Esportazioni, PkId> {

    /**
     * Ritorna una stringa che rappresenta una qury string formata dai parametri passati
     * 
     * @param parametriesportazione
     * @return
     */
    public String createRequestParameter(Set<Parametriesportazione> parametriesportazione) throws it.gruppoinit.pal.gp.core.exception.SecurityException;

    /**
     * Ritorna una stringa che rappresneta una chiamata http per invocare un servizio di pentaho
     * 
     * @param job
     * @param trasformazione
     * @param parametriEsportazione
     * @param sessionId
     * @return
     */
    public String createUrlOperazionePentaho(OperazioniPentaho job, String trasformazione, String parametriEsportazione, String sessionId);

    /**
     * Il metodo permettedi chiamare una trasformazione e tornare un risultato
     * 
     * @param esportazioni
     */
    public String callTrasformazione(String urlTrasformazione, Set<Parametriesportazione> parametriesportazione, String sessionId,
	    HttpServletResponse response) throws Exception;
}
