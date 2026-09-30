package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface RaggruppamentocausalioneriDAO extends BaseDAO<Raggruppamentocausalioneri, PkId> {

    /**
     * Recupera la lista di raggruppamenti causali oneri per il software corrente ordinati per descrizione
     * 
     */
    public List<Raggruppamentocausalioneri> findAll(Integer firstResult, Integer maxResult);
}
