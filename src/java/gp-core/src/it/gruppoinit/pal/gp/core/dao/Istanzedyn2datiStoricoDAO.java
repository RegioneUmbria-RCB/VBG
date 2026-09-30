package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Istanzedyn2datiStoricoDAO extends BaseDAO<Istanzedyn2datiStorico, Istanzedyn2datiStoricoId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzedyn2datiStorico> findAll(Integer firstResult, Integer maxResult);
}
