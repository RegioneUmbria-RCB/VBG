/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;

import java.util.List;

/**
 * @author Riccardo Bocci
 * 
 */
public interface TipiendoDAO extends BaseDAO<Tipiendo, PkId> {

    /**
     * Ricerca tutti i record di una determinata tabella filtrati per idcomune e Software
     * 
     * @param firstResult
     *            il primo record da recuperare, partendo da 0 ( può essere nullo )
     * @param maxResult
     *            il numero massimo di records da recuperare ( può essere nullo )
     * @return una <code>java.util.List</code> di entity
     */
    public List<Tipiendo> findAll(Integer firstResult, Integer maxResult);

    public List<Tipiendo> findByDescSWeTT(String textToSearch, Integer codiceFamiglia);
}
