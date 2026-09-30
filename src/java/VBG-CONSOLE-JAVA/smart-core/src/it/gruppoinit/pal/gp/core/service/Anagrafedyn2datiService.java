package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2datiDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Anagrafedyn2datiService extends BaseService<Anagrafedyn2dati, Anagrafedyn2datiId> {

    /**
     * @see Anagrafedyn2datiDAO#findAll(Integer, Integer)
     */
    public List<Anagrafedyn2dati> findAll(Integer firstResult, Integer maxResult);
}
