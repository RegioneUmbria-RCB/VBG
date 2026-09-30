package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Anagrafedyn2datiStoricoDAO extends BaseDAO<Anagrafedyn2datiStorico, Anagrafedyn2datiStoricoId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Anagrafedyn2datiStorico> findAll(Integer firstResult, Integer maxResult);
}
