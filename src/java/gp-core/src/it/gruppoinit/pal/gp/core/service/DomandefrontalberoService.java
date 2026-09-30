package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DomandefrontalberoDAO;
import it.gruppoinit.pal.gp.core.domain.Domandefrontalbero;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface DomandefrontalberoService extends BaseService<Domandefrontalbero, PkId> {

    /**
     * @see DomandefrontalberoDAO#findAll(Integer, Integer)
     */
    public List<Domandefrontalbero> findAll(Integer firstResult, Integer maxResult);
}
