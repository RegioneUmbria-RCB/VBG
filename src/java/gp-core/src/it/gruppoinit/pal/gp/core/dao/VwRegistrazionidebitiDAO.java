package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RateNonpagateFilter;
import it.gruppoinit.pal.gp.core.domain.VwRegistrazionidebiti;

import java.util.List;

public interface VwRegistrazionidebitiDAO extends BaseDAO<VwRegistrazionidebiti, PkId> {

    /**
     * Metodo che restituisce una lista di vwregistrazionidebiti con filtri i campi di tale ogetto (opzionali)
     * 
     * @param vwRegistrazionidebiti
     * @return
     */
    public List<VwRegistrazionidebiti> findByFilter(RateNonpagateFilter rateNonpagateFilter);
}
