package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.CatastoDAO;
import it.gruppoinit.pal.gp.core.domain.Catasto;

import java.util.List;

/**
 * 
 * @author
 */
public interface CatastoService extends BaseService<Catasto, String> {

    /**
     * @see CatastoDAO#findAll(Integer, Integer)
     */
    public List<Catasto> findAll(Integer firstResult, Integer maxResult);

    public List<Catasto> findAll();

    @DeletableCacheElements
    public void resetObjectCached();
}
