package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface Anagrafedyn2datiDAO extends BaseDAO<Anagrafedyn2dati, Anagrafedyn2datiId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Anagrafedyn2dati> findAll(Integer firstResult, Integer maxResult);
}
