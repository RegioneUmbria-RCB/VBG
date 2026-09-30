package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidtestiDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface Dyn2ModellidtestiService extends BaseService<Dyn2Modellidtesti, PkId> {

    /**
     * @see Dyn2ModellidtestiDAO#findAll(Integer, Integer)
     */
    public List<Dyn2Modellidtesti> findAll(Integer firstResult, Integer maxResult);
}
