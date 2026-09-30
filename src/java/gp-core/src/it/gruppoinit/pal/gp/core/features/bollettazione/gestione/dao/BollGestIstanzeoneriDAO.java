package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollGestIstanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
public interface BollGestIstanzeoneriDAO extends BaseDAO<BollGestIstanzeoneri, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BollGestIstanzeoneri> findAll(Integer firstResult, Integer maxResult);

    List<BollGestIstanzeoneri> findByBollGestDett(Integer codiceBollGestDett, Integer firstResult, Integer maxResult);

    /**
     * Effettua la cancellazione fisica di tutte le righe legate all'id della bollettazione passata
     * 
     * @param idBollettazione
     */
    void deleteByIdBollettazione(Integer idBollettazione);
}
