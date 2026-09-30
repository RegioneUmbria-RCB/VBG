package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface Dyn2ModellidtestiDAO extends BaseDAO<Dyn2Modellidtesti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Dyn2Modellidtesti> findAll(Integer firstResult, Integer maxResult);
}
