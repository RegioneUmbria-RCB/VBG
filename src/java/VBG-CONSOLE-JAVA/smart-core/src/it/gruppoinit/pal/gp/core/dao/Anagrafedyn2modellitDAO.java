package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface Anagrafedyn2modellitDAO extends BaseDAO<Anagrafedyn2modellit, Anagrafedyn2modellitId> {

    /**
     * Lista delle Anagrafedyn2modellit filtrate per idcomune
     * 
     */
    public List<Anagrafedyn2modellit> findAll(Integer firstResult, Integer maxResult);
}
