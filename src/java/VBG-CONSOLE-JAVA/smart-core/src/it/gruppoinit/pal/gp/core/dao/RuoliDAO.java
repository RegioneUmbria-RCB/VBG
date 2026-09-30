/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Ruoli;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface RuoliDAO extends BaseDAO<Ruoli, PkId> {

    /**
     * Filtra le la lista dei ruoli in base alla descrizione inserita
     * 
     * @param ruoli
     * @return
     */
    public List<Ruoli> findByFilter(Ruoli ruoli);
}
