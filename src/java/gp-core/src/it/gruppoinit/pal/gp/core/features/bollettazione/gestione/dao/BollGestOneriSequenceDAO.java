package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollGestOneriSequence;
import it.gruppoinit.pal.gp.core.domain.BollGestOneriSequenceId;

public interface BollGestOneriSequenceDAO extends BaseDAO<BollGestOneriSequence, BollGestOneriSequenceId> {

    /**
     * Effettua la cancellazione fisica di tutti i filtri dell'id della bollettazione passato
     * 
     * @param idBollettazione
     */
    void deleteByIdBollettazione(Integer idBollettazione);
}
