package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollGestDettRate;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface BollGestDettRateDAO extends BaseDAO<BollGestDettRate, PkId> {

    /**
     * Effettua la cancellazione fisica di tutti i filtri dell'id della bollettazione passato
     * 
     * @param idBollettazione
     */
    void deleteByIdBollettazione(Integer idBollettazione);
}
