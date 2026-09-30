package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface LinkPreferitiUtenteDAO extends BaseDAO<LinkPreferitiUtente, PkId> {

    public List<LinkPreferitiUtente> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna il valore massimo del campo ordine, nel caso non ci sia nessun record riporta il valore 0
     * 
     * @return
     */
    public Integer findMaxOrdine();
}
