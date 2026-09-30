package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Anagrafedyn2modellitStoricoDAO extends BaseDAO<Anagrafedyn2modellitStorico, Anagrafedyn2modellitStoricoId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Anagrafedyn2modellitStorico> findAll(Integer firstResult, Integer maxResult);
}
