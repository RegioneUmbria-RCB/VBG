package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Anagrafedyn2modellitStoricoService extends BaseService<Anagrafedyn2modellitStorico, Anagrafedyn2modellitStoricoId> {

    /**
     * @see Anagrafedyn2modellitStoricoDAO#findAll(Integer, Integer)
     */
    public List<Anagrafedyn2modellitStorico> findAll(Integer firstResult, Integer maxResult);
}
