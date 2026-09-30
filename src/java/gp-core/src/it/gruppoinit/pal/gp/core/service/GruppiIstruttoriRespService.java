package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.GruppiIstruttoriRespDAO;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttoriResp;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface GruppiIstruttoriRespService extends BaseService<GruppiIstruttoriResp, PkId> {

    /**
     * @see GruppiIstruttoriRespDAO#findAll(Integer, Integer)
     */
    public List<GruppiIstruttoriResp> findAll(Integer firstResult, Integer maxResult);

    /**
     * Lista di istruttori appartenete al gruppo
     * 
     * @param codice
     * @param controllaAssenza
     *            : se true controlla se al momento è assente e setta il flag assesento su Responsabili
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<GruppiIstruttoriResp> findByGruppoIstruttori(Integer codice, Boolean controllaAssenza, Boolean controllaSeAttivoPerSoftwareCorrente,
	    Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista di GruppiIstruttoriResp. La lista sarà bonificata andando a togliere i responsabili che alla
     * data odierna sono assenti (RESPONSABILI_ASSENZE) o non sono più responsabili per il software in corso.
     * 
     * @param codice
     * @return
     */
    public List<GruppiIstruttoriResp> findByGruppoIstruttoriSelezionabili(Integer codice);
}
