package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Istanzedyn2modellitStoricoDAO extends BaseDAO<Istanzedyn2modellitStorico, Istanzedyn2modellitStoricoId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzedyn2modellitStorico> findAll(Integer firstResult, Integer maxResult);
}
