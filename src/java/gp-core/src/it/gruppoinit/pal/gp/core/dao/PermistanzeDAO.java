package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Permistanze;
import it.gruppoinit.pal.gp.core.domain.PermistanzeId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface PermistanzeDAO extends BaseDAO<Permistanze, PermistanzeId> {

    /**
     * Ritorna una lista di permistanze
     * 
     */
    public List<Permistanze> findAll(Integer firstResult, Integer maxResult);
}
