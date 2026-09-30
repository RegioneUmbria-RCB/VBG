package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Dyn2BasecontestiDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basecontesti;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2BasecontestiService extends BaseService<Dyn2Basecontesti, String> {

    /**
     * @see Dyn2BasecontestiDAO#findAll(Integer, Integer)
     */
    public List<Dyn2Basecontesti> findAll(Integer firstResult, Integer maxResult);
}
