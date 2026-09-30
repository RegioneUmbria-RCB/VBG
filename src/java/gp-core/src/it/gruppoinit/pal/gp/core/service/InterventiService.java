package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.InterventiDAO;
import it.gruppoinit.pal.gp.core.domain.Interventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author francescop
 */
public interface InterventiService extends BaseService<Interventi, PkId> {

    /**
     * @see InterventiDAO#findAll(Integer, Integer)
     */
    public List<Interventi> findAll(Integer firstResult, Integer maxResult);
}
