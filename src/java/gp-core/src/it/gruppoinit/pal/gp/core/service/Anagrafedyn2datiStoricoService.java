package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStoricoId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Anagrafedyn2datiStoricoService extends BaseService<Anagrafedyn2datiStorico, Anagrafedyn2datiStoricoId> {

    /**
     * @see Anagrafedyn2datiStoricoDAO#findAll(Integer, Integer)
     */
    public List<Anagrafedyn2datiStorico> findAll(Integer firstResult, Integer maxResult);
}
