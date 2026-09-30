package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.LinkPreferitiUtenteDAO;
import it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface LinkPreferitiUtenteService extends BaseService<LinkPreferitiUtente, PkId> {

    /**
     * @see LinkPreferitiUtenteDAO#findAll(Integer, Integer)
     */
    public List<LinkPreferitiUtente> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista dei link preferiti salvati dall'utente
     * 
     * @param codice
     * @return
     */
    public List<LinkPreferitiUtente> findLinkPreferitiUtente(Integer codiceResponsabile, Integer firstResult, Integer maxResult);

    /**
     * @see LinkPreferitiUtenteDAO#findMaxOrdine()
     */
    public Integer findMaxOrdine();
}
