package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellitId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface Tipimovimentidyn2modellitDAO extends BaseDAO<Tipimovimentidyn2modellit, Tipimovimentidyn2modellitId> {

    /**
     * Lista dei modelli dyn2dati dei tipi movimento
     * 
     */
    public List<Tipimovimentidyn2modellit> findAll(Integer firstResult, Integer maxResult);
}
