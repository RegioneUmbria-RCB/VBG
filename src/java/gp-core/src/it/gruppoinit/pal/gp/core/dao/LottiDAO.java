package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Lotti;
import it.gruppoinit.pal.gp.core.domain.LottiId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface LottiDAO extends BaseDAO<Lotti, LottiId> {

    /**
     * Non Implementato: la ricerca per tutti i record è inutile
     * 
     */
    public List<Lotti> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista di lotti per l'area selezionata ordinata per codice lotto
     * 
     * @param aree
     * @return
     */
    public List<Lotti> findByAree(Aree aree);
}
