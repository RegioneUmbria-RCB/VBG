package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollGestFiltri;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
public interface BollGestFiltriDAO extends BaseDAO<BollGestFiltri, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BollGestFiltri> findAll(Integer firstResult, Integer maxResult);

    /**
     * Invoca il metodo Insert del BaseDAO per ognuno degli elementi della lista
     * 
     * @param filtri
     */
    public void insert(List<BollGestFiltri> filtri);

    /**
     * Effettua la cancellazione fisica di tutti i filtri dell'id della bollettazione passato
     * 
     * @param idBollettazione
     */
    void deleteByIdBollettazione(Integer idBollettazione);
}
