package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigraduatorietEsprArt;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipigraduatorietEsprArtDAO extends BaseDAO<TipigraduatorietEsprArt, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<TipigraduatorietEsprArt> findAll(Integer firstResult, Integer maxResult);
}
