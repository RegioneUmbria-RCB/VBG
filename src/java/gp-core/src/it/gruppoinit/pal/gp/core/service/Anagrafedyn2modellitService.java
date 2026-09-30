package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface Anagrafedyn2modellitService extends BaseService<Anagrafedyn2modellit, Anagrafedyn2modellitId> {

    /**
     * @see Anagrafedyn2modellitDAO#findAll(Integer, Integer)
     */
    public List<Anagrafedyn2modellit> findAll(Integer firstResult, Integer maxResult);
}
