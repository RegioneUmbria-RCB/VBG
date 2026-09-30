package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.TmpEsportazioniDAO;
import it.gruppoinit.pal.gp.core.domain.TmpEsportazioni;

/**
 * 
 * @author
 */
public interface TmpEsportazioniService extends BaseService<TmpEsportazioni, Integer> {

    /**
     * @see TmpEsportazioniDAO#findAll(Integer, Integer)
     */
    public List<TmpEsportazioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cancella dalla tabella tutti i recordo con il sessionId passato
     * 
     * @param sessionId
     */
    public void deleteBysessionId(String sessionId);

    String exportModalitaPentaho(Integer codice, String email, boolean isInvioMail, String codiceComune);

    String exportModalitaPentaho(Integer codice, String email, boolean isInvioMail);
}
