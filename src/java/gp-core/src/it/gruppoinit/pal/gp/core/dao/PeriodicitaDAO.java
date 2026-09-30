package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Periodicita;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface PeriodicitaDAO extends BaseDAO<Periodicita, Integer> {

    /**
     * Ricerca tutte le periodicità (senza filtro per idcomune)
     */
    public List<Periodicita> findAll(Integer firstResult, Integer maxResult);
}
