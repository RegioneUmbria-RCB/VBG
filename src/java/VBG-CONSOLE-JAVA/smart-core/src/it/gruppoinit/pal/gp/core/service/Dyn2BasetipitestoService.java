package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Dyn2BasetipitestoDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basetipitesto;

import java.util.List;

/**
 * 
 * @author
 */
public interface Dyn2BasetipitestoService extends BaseService<Dyn2Basetipitesto, String> {

    /**
     * @see Dyn2BasetipitestoDAO#findAll(Integer, Integer)
     */
    public List<Dyn2Basetipitesto> findAll(Integer firstResult, Integer maxResult);
}
