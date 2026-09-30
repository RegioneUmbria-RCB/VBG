package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipigraduatorietEsprArtDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigraduatorietEsprArt;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipigraduatorietEsprArtService extends BaseService<TipigraduatorietEsprArt, PkId> {

    /**
     * @see TipigraduatorietEsprArtDAO#findAll(Integer, Integer)
     */
    public List<TipigraduatorietEsprArt> findAll(Integer firstResult, Integer maxResult);

    /**
     * ritorna una lista di TipigraduatorietEsprArt filtarte per Tipigraduatoriet
     * 
     * @param codice
     * @return
     */
    public List<TipigraduatorietEsprArt> findByTipigraduatoriet(Integer codice);
}
